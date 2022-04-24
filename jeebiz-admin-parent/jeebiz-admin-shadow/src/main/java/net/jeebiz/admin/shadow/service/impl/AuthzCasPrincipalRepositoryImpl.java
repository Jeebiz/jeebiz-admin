package net.jeebiz.admin.shadow.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.hiwepy.jwt.JwtPayload.RolePair;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.biz.authc.exception.InvalidAccountException;
import org.apache.shiro.biz.authc.exception.NoneRoleException;
import org.apache.shiro.spring.boot.cas.CasPrincipalRepository;
import org.apache.shiro.spring.boot.cas.token.CasToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service("casRepository")
public class AuthzCasPrincipalRepositoryImpl extends CasPrincipalRepository {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private IAuthzLoginDao authzLoginDao;
	@Autowired
	private IAuthzUserDao authzUserDao;
	@Autowired
	private IAuthzRoleDao authzRoleDao;
	@Autowired
	private IAuthzRolePermsDao authzRolePermsDao;
	
	@Override
	public AuthenticationInfo getAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

		CasToken upToken = (CasToken) token;
		
		if( !StringUtils.hasText(upToken.getUsername()) ){
			throw new UnknownAccountException("Username is required.");
		}
		// 账号状态
		Map<String, String> statusMap = getAuthzLoginDao().getAccountStatusWithoutPwd(upToken.getUsername());
   		// 账号不存在 或 用户名或密码不正确
   		if("0".equals(statusMap.get("num_1")) || "0".equals(statusMap.get("num_2"))){
   			throw new InvalidAccountException("Username or password is incorrect, please re-enter.");
   		}
   		// 账号被禁用
		else if ("0".equals(statusMap.get("num_4"))) {
			throw new DisabledAccountException("Account is disabled.");
		}
   		//用户无所属角色
   		else if("0".equals(statusMap.get("num_3"))){
            throw new NoneRoleException();
   		}
   		
   		// 用户主体对象
   		AuthzLoginModel model = getAuthzLoginDao().getAccountWithoutPwd(upToken.getUsername());
   		if(model == null){
   			throw new InvalidAccountException("Username or password is incorrect, please re-enter.");
   		}
   		
   		// 用户角色信息集合
   		List<AuthzRoleModel> roleModels = getAuthzRoleDao().getUserRoles(model.getUserid());
   		
   		Optional<AuthzRoleModel> roleFirst = CollectionUtils.isEmpty(roleModels) ? Optional.empty()
				: roleModels.stream().findFirst();
					
   		// 用户权限标记集合
   		Set<String> perms =  Sets.newHashSet();
   		
   		try {
			
			logger.error(JSONObject.toJSONString(model));
			
			// 有设置角色：构造用户权限
			if(roleFirst.isPresent()) {
				AuthzRoleModel role = roleFirst.get();
		   		// 	用户权限标记集合
				perms.addAll(getAuthzRolePermsDao().getPermissions(role.getId()));
			}
			 
			model.setUserid(model.getUserid());
			model.setAlias(model.getAlias());
			model.setPerms(perms);
			
			// 有设置角色：构造角色信息
			if(roleFirst.isPresent()) {
				
				List<RolePair> roles = CollectionUtils.isEmpty(roleModels) ? Lists.newArrayList() : roleModels.stream().map(role -> {
					return new RolePair(role.getId(), role.getKey(), role.getName()); 
		   		}).collect(Collectors.toList());
				
				model.setRoles(roles);
				model.setRole(roleFirst.get().getKey());
				model.setRoleid(roleFirst.get().getId());
				
			}
			model.setInitial(model.isInitial());
		} catch (Exception e) {
			e.printStackTrace();
		}
   		
   		// 认证信息
		return new SimpleAuthenticationInfo(model, null, "login");
	}
	 
	
	@Override
	public Set<String> getRoles(Set<Object> principals) {
		Set<String> sets =  Sets.newHashSet();
		for (Object principal : principals) {
			if(principal instanceof AuthzLoginModel) {
				AuthzLoginModel model = (AuthzLoginModel) principal;
				// 用户角色信息集合
		   		List<AuthzRoleModel> roleModels = getAuthzRoleDao().getUserRoles(model.getUserid());
				if(CollectionUtils.isNotEmpty(roleModels)) {
					for (AuthzRoleModel authzRoleModel : roleModels) {
						sets.add(authzRoleModel.getKey());
					}	
				}
			}
		}
		return sets;
	}
	
	@Override
	public Set<String> getPermissions(Set<Object> principals) {
		Set<String> set =  Sets.newHashSet();
		for (Object principal : principals) {
			if(principal instanceof AuthzLoginModel) {
				AuthzLoginModel model = (AuthzLoginModel) principal;
				set.addAll(getAuthzRolePermsDao().getPermissions(model.getRoleid()));
				for (AuthzRoleModel roleModel : model.getRoleList()) {
					set.addAll(getAuthzRolePermsDao().getPermissions(roleModel.getId()));
				}
			}
		}
		return set;
	}
	
	@Override
	public void doLock(Object principal) {
		// TODO Auto-generated method stub
		
	}

	public IAuthzLoginDao getAuthzLoginDao() {
		return authzLoginDao;
	}

	public void setAuthzLoginDao(IAuthzLoginDao authzLoginDao) {
		this.authzLoginDao = authzLoginDao;
	}

	public IAuthzUserDao getAuthzUserDao() {
		return authzUserDao;
	}

	public void setAuthzUserDao(IAuthzUserDao authzUserDao) {
		this.authzUserDao = authzUserDao;
	}

	public IAuthzRoleDao getAuthzRoleDao() {
		return authzRoleDao;
	}

	public void setAuthzRoleDao(IAuthzRoleDao authzRoleDao) {
		this.authzRoleDao = authzRoleDao;
	}

	public IAuthzRolePermsDao getAuthzRolePermsDao() {
		return authzRolePermsDao;
	}

	public void setAuthzRolePermsDao(IAuthzRolePermsDao authzRolePermsDao) {
		this.authzRolePermsDao = authzRolePermsDao;
	}
	
}