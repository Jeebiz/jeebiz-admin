package net.jeebiz.admin.shadow.setup.strategy;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.hiwepy.jwt.JwtPayload;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import hitool.core.lang3.RandomString;
import lombok.extern.slf4j.Slf4j;
import net.jeebiz.admin.authz.rbac0.dao.entities.*;
import net.jeebiz.admin.authz.rbac0.service.*;
import net.jeebiz.admin.extras.redis.setup.BizRedisKey;
import net.jeebiz.admin.extras.redis.setup.BizRedisKeyConstant;
import net.jeebiz.admin.shadow.bo.AuthBO;
import net.jeebiz.admin.shadow.service.IAuthService;
import net.jeebiz.admin.shadow.web.param.RegisterParam;
import net.jeebiz.boot.api.sequence.Sequence;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.biz.authc.exception.InvalidAccountException;
import org.apache.shiro.biz.authz.principal.ShiroPrincipal;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.pac4j.core.ext.exception.UsernameNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisOperationTemplate;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public abstract class AbstractAuthStrategy<T> implements AuthStrategy<T> {

	protected RandomString randomString = new RandomString(8);
	// 加密方式
	private String algorithmName = "MD5";
	// 加密的次数,可以进行多次的加密操作
	private int hashIterations = 10;
	// 默认密码
	private String defaultPassword = "123456";

	@Autowired
	private IAuthService authService;
	@Autowired
	private IUserAccountService accountService;
	@Autowired
	private IUserProfileService profileService;
	@Autowired
	private IUserRoleService userRoleService;
	@Autowired
	private IRoleService roleService;
	@Autowired
	private IRolePermsService rolePermsService;
	@Autowired
	private Sequence sequence;
	@Autowired
	private RedisOperationTemplate redisOperation;

	@Override
	public AuthBO<T> initInfo(AuthenticationToken token) throws AuthenticationException {
		// TODO Auto-generated method stub
		return null;
	}

	protected String initAvatar(String avatarUrl) {
		if (StringUtils.isBlank(avatarUrl)) {
			return "default/user-avatar2.png";
		}
		return avatarUrl;
	}

	protected boolean useUserCode() {
		return false;
	}

	/**
	 * 注册
	 *
	 * @param authBO
	 * @param registerParam
	 * @return Long
	 * @throws AuthenticationException
	 */
	protected String register(AuthBO<T> authBO, RegisterParam registerParam) throws AuthenticationException {
		String userId = String.valueOf(getSequence().nextId());
		String userCode = randomString.nextNumberString();
		String avatar = StringUtils.isNotBlank(registerParam.getAvatar()) ? registerParam.getAvatar() : "default/user-avatar.png";
		String nickname = registerParam.getNickname() != null ? registerParam.getNickname() : "Edu_".concat(String.valueOf(userCode));
		int gender = registerParam.getGender() != null ? registerParam.getGender() : 1;

		LocalDate birthday = LocalDate.parse("2000-01-01 00:00:00"); // DateUtil.getNowDate();

		// 查询user_code是否已使用 直到生成未使用的为止
		while (getAccountService().count(new QueryWrapper<UserAccountEntity>().eq("user_code", userCode)) > 0) {
			userCode = randomString.nextNumberString();
		}

		UserProfileEntity profileEntity = new UserProfileEntity();
		profileEntity.setAvatar(avatar);
		profileEntity.setBirthday(birthday);
		profileEntity.setNickname(nickname);
		profileEntity.setUserId(userId);
		profileEntity.setUserCode(userCode);
		profileEntity.setGender(gender);
		profileEntity.setSignature("");
		getProfileService().save(profileEntity);

		UserAccountEntity accountEntity = new UserAccountEntity();
		accountEntity.setAccount(useUserCode() ? userCode : registerParam.getAccount());
		String salt = randomString.nextString();
		SimpleHash hash = new SimpleHash(algorithmName, new String(randomString.nextString()), salt, hashIterations);
		accountEntity.setSalt(salt);
		accountEntity.setPassword(hash.toBase64());
		accountEntity.setUserId(userId);
		accountEntity.setType(registerParam.getChannel().getKey());
		accountEntity.setStatus(1);
		accountEntity.setPassword(registerParam.getPassword());

		getAccountService().save(accountEntity);

		/**
		 * 封装传递参数
		 */
		authBO.setUserId(userId);

		afterReg(authBO, userId);
		return userId;
	}



	/**
	 * 根据账号获取uid
	 *
	 * @param authBO
	 * @return
	 */
    protected String getUidByAccount(AuthBO<T> authBO) throws AuthenticationException {
    	// 1、账号查询
		UserAccountEntity accountEntity = getAccountService().getAccountByType(this.getChannel().getKey() , authBO.getAccount());
        // 未找到响应账号
        if (Objects.nonNull(accountEntity)) {
			throw new InvalidAccountException("Username or password is incorrect, please re-enter.");
        }
		authBO.setEncodePassword(accountEntity.getPassword());
		//System.out.println(getPasswordEncoder().encode(authBO.getPassword()));
        if (!getPasswordEncoder().matches(accountEntity.getPassword(), authBO.getPassword())) {
        	 //throw new BadCredentialsException("Authentication Failed. Username or Password not valid.");
        }
        return accountEntity.getUserId();
    }

	/**
	 * 是否可以绑定账号
	 *
	 * @param account
	 * @return
	 */
	protected Boolean checkBindByAccount(AuthBO<T> account) throws AuthenticationException {
		return false;
	}

	/**
	 * 把获取到的账号信息转化为注册所要用到的参数
	 *
	 * @param authBO<T>
	 * @return
	 */
	protected RegisterParam getRegisterParam(AuthBO<T> authBO) {
		RegisterParam registerParam = new RegisterParam();
		registerParam.setChannel(getChannel());
		registerParam.setLang(authBO.getLang());
		return registerParam;
	}

	/**
	 * 是否需要注册
	 *
	 * @return
	 */
	protected Boolean needReg() {
		return false;
	}

	@Override
	public final SimpleAuthenticationInfo login(AuthenticationToken token) throws AuthenticationException {
		// 1、获取登录用的账号
		AuthBO<T> authBO = initInfo(token);
		// 2、执行登录
		return this.login(authBO);
	}

	/**
	 * 登陆
	 *
	 * @param authBO
	 * @return
	 * @throws AuthenticationException
	 */
	@Override
	public final SimpleAuthenticationInfo login(AuthBO<T> authBO) throws AuthenticationException {

		// 1、查询是否是已存在的账号
		String userId = getUidByAccount(authBO);
		boolean isFirst = false;
		// 2、首次登录，如果需要注册账号则注册则自动注册账号
		if (Objects.isNull(userId) && needReg()) {
			// 2.1、获取注册所需参数
			RegisterParam registerParam = getRegisterParam(authBO);
			// 2.2、加锁
			String key = BizRedisKey.USER_LOCK_REG.getKey(registerParam.getAccount());
			String value = String.valueOf(System.currentTimeMillis());
			boolean lock = redisOperation.setNx(key, value, BizRedisKeyConstant.FIVE_SECONDS);
			if (!lock) {
				throw new DisabledAccountException("Account is disabled.");
			}
			// 2.3、注册
			userId = register(authBO, registerParam);
			redisOperation.unlock(key, value);
			isFirst = true;
		}
		if(Objects.nonNull(userId) ){
			authBO.setUserId(userId);
		}
		// 登陆
		ShiroPrincipal principal = getPrincipal(authBO);
		principal.setInitial(isFirst); // 首次注册登陆

		authBO.setLoginTime(System.currentTimeMillis());
		afterLogin(authBO, userId);

		// 认证信息
		ByteSource credentialsSalt = ByteSource.Util.bytes(authBO.getSalt());
		return new SimpleAuthenticationInfo(principal, authBO.getPassword(), credentialsSalt, "login");

	}


	/**
	 * 登陆
	 *
	 * @param authBO
	 * @return
	 * @throws AuthenticationException
	 */
	@Override
	public final ShiroPrincipal getPrincipal(AuthBO<T> authBO) throws AuthenticationException {

		log.info("{} >> Login AuthBo : {}", authBO.getUserId(), JSONObject.toJSONString(authBO));

		// 账号状态
		AccountStatusModel statusModel = getAccountService().getAccountStatusByUid(authBO.getUserId());


		// 用户角色信息集合
		List<UserRoleEntity> userRoles = getUserRoleService().list(new QueryWrapper<UserRoleEntity>().eq("user_id", authBO.getUserId()));
		List<RoleEntity> roles = CollectionUtils.isEmpty(userRoles) ? Lists.newArrayList() : getRoleService().list(new QueryWrapper<RoleEntity>().in("id", userRoles.stream().map(UserRoleEntity::getRoleId).toArray()));
		Optional<RoleEntity> roleFirst = null;
		if(!CollectionUtils.isEmpty(roles)){
			if(StringUtils.isNotBlank(authBO.getRoleId())){
				roleFirst = roles.stream().filter(role -> StringUtils.equalsIgnoreCase(role.getId(), authBO.getRoleId())).findFirst();
			} else {
				roleFirst = Optional.of(roles.get(0));
			}
		}

		Set<String> perms = Sets.newHashSet();

		ShiroPrincipal principal = null;
		try {


			// 有设置角色：构造用户权限
			if (roleFirst.isPresent()) {

				RoleEntity role = roleFirst.get();

				// 用户权限标记集合
				perms.addAll(getRolePermsService().getPermissions(role.getId()));
			}

			// 用户状态（0:禁用|1:可用|2:锁定|3:密码过期）
			principal = new ShiroPrincipal(authBO.getAccount(), "");


			// principal.setUserid(model.getUserid());
			// principal.setAlias(model.getAlias());
			principal.setPerms(perms);

			// 有设置角色：构造角色信息
			if (roleFirst.isPresent()) {

				if(!CollectionUtils.isEmpty(roles)){
					List<JwtPayload.RolePair> rolePairs = roles.stream().map(role -> {
						return new JwtPayload.RolePair(role.getId(), role.getKey(), role.getName());
					}).collect(Collectors.toList());
					principal.setRoles(rolePairs);
				}

				RoleEntity role = roleFirst.get();
				principal.setRole(role.getKey());
				principal.setRoleid(role.getId());

			}
			// principal.setInitial(model.isInitial());

			// 查询用户个人信息
			Map<String, Object> profile = getAuthService().getAuthProfile(authBO.getUserId());
			principal.setProfile(profile);

			log.info("{} >> Login Principal : {}", authBO.getUserId(), JSONObject.toJSONString(principal));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void afterLogin(AuthBO<T> object, String userId) {
		object.setUserId(userId);
		authService.afterLogin(object);
	}

	@Override
	public void afterReg(AuthBO<T> authBO, String userId) throws AuthenticationException {
		authBO.setUserId(userId);
	}

	public IUserAccountService getAccountService() {
		return accountService;
	}

	public IAuthService getAuthService() {
		return authService;
	}

	public IUserProfileService getProfileService() {
		return profileService;
	}

	public IUserRoleService getUserRoleService() {
		return userRoleService;
	}

	public RedisOperationTemplate getRedisOperation() {
		return redisOperation;
	}

	public IRoleService getRoleService() {
		return roleService;
	}

	public IRolePermsService getRolePermsService() {
		return rolePermsService;
	}

	public Sequence getSequence() {
		return sequence;
	}

}