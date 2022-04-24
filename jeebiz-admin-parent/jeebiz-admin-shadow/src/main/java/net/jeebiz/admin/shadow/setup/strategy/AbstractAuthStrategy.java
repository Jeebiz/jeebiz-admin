package net.jeebiz.admin.shadow.setup.strategy;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.hiwepy.jwt.JwtPayload;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import hitool.core.lang3.RandomString;
import lombok.extern.slf4j.Slf4j;
import net.jeebiz.admin.authz.rbac0.service.*;
import net.jeebiz.admin.shadow.bo.AuthBO;
import net.jeebiz.admin.shadow.service.IAuthService;
import net.jeebiz.boot.api.sequence.Sequence;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisOperationTemplate;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.boot.biz.userdetails.SecurityPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public abstract class AbstractAuthStrategy<T> implements AuthStrategy<T> {

	protected RandomString randomString = new RandomString();

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
	protected Long register(AuthBO<T> authBO, RegisterParam registerParam) throws AuthenticationException {
		long userId = getSequence().nextId();
		String userCode = RandomUtil.getRandomNum8();
		String avatar = StringUtils.isNotBlank(registerParam.getAvatar()) ? registerParam.getAvatar() : "default/user-avatar.png";
		String nickname = registerParam.getNickname() != null ? registerParam.getNickname() : "Edu_".concat(String.valueOf(userCode));
		int gender = registerParam.getGender() != null ? registerParam.getGender() : 1;
		
		LocalDate birthday = LocalDate.parse("2000-01-01 00:00:00"); // DateUtil.getNowDate();
		
		// 查询user_code是否已使用 直到生成未使用的为止
		while (getAccountService().count(new QueryWrapper<UserAccountEntity>().eq("user_code", userCode)) > 0) {
			userCode = RandomUtil.getRandomNum8();
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
		accountEntity.setPassword(getPasswordEncoder().encode(randomString.nextString()));
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
    protected Long getUidByAccount(AuthBO<T> authBO) throws AuthenticationException {
    	// 1、账号查询
        UserAccountEntity accountEntity = getAccountService().getOne(new QueryWrapper<UserAccountEntity>()
                .eq("type", this.getChannel().getKey())
                .eq("account", authBO.getAccount()));
        // 未找到响应账号
        if (accountEntity == null) {
        	throw new UsernameNotFoundException("Username or password is incorrect, please re-enter.");
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
	public final SecurityPrincipal login(Authentication token) throws AuthenticationException {
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
	public final SecurityPrincipal login(AuthBO<T> authBO) throws AuthenticationException {
		
		// 1、查询是否是已存在的账号
		Long userId = getUidByAccount(authBO);
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
				throw new LockedException("");
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
		SecurityPrincipal login = getPrincipal(authBO);
		login.setInitial(isFirst); // 首次注册登陆
			
		authBO.setLoginTime(System.currentTimeMillis());
		afterLogin(authBO, userId);
		return login;
		
	}	 
	

	/**
	 * 登陆
	 * 
	 * @param authBO
	 * @return
	 * @throws AuthenticationException
	 */
	@Override
	public final SecurityPrincipal getPrincipal(AuthBO<T> authBO) throws AuthenticationException {
		
		log.info("{} >> Login AuthBo : {}", authBO.getUserId(), JSONObject.toJSONString(authBO));
		
		// 账号状态
		AccountStatusModel statusModel = getAccountService().getAccountStatusByUid(authBO.getUserId());
		//	账号不存在 或 用户名或密码不正确
   		if(!statusModel.isHasAcc()){
   			throw new UsernameNotFoundException("Username or password is incorrect, please re-enter.");
   		}
   		
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

		Set<GrantedAuthority> grantedAuthorities = Sets.newHashSet();
		Set<String> perms = Sets.newHashSet();

		SecurityPrincipal principal = null;
		try {

			
			// 有设置角色：构造用户权限
			if (roleFirst.isPresent()) {

				RoleEntity role = roleFirst.get();

				// 角色必须是ROLE_开头，可以在数据库中设置
				GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(
						StringUtils.startsWithIgnoreCase(role.getKey(), "ROLE_") ? role.getKey()
								: "ROLE_" + role.getKey());

				grantedAuthorities.add(grantedAuthority);

				// 用户权限标记集合
				perms.addAll(getRolePermsService().getPermissions(role.getId()));
				for (String perm : perms) {
					GrantedAuthority authority = new SimpleGrantedAuthority(perm);
					grantedAuthorities.add(authority);
				}

			}
			
			// 用户状态（0:禁用|1:可用|2:锁定|3:密码过期）
			principal = new SecurityPrincipal(authBO.getAccount(), authBO.getEncodePassword(), statusModel.isEnabled(),
					statusModel.isAccountNonExpired(), statusModel.isCredentialsNonExpired(),
					statusModel.isAccountNonLocked(), grantedAuthorities);

			
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
				principal.setRcode(role.getKey());
				principal.setRid(role.getId());

			}
			// principal.setInitial(model.isInitial());

			// 查询用户个人信息
			Map<String, Object> profile = getProfileService().getAccountProfile(authBO.getUserId());
			principal.setProfile(profile);

			log.info("{} >> Login Principal : {}", authBO.getUserId(), JSONObject.toJSONString(principal));
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return principal;
	}

	@Override
	public void afterLogin(AuthBO<T> object, Long userId) {
		object.setUserId(userId);
		authService.afterLogin(object);
	}

	@Override
	public void afterReg(AuthBO<T> authBO, Long userId) throws AuthenticationException {
		authBO.setUserId(userId);
	}

	public IUserAccountService getAccountService() {
		return accountService;
	}

	public IAuthService getAuthService() {
		return authService;
	}

	public PasswordEncoder getPasswordEncoder() {
		return passwordEncoder;
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

	public ThirdClientApi getThirdClientApi() {
		return thirdClientApi;
	}

}