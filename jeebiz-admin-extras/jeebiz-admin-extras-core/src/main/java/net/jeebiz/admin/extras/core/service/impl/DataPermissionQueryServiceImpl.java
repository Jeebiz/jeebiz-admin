package net.jeebiz.admin.extras.core.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.mybatis.dbperms.annotation.Condition;
import org.apache.mybatis.dbperms.annotation.Relational;
import org.apache.mybatis.dbperms.dto.DataPermission;
import org.apache.mybatis.dbperms.dto.DataPermissionColumn;
import org.apache.mybatis.dbperms.dto.DataSpecialPermission;
import org.apache.shiro.biz.authz.principal.ShiroPrincipal;
import org.apache.shiro.biz.utils.SubjectUtils;
import org.springframework.stereotype.Service;

import net.jeebiz.admin.extras.core.dao.IDataPermissionQueryDao;
import net.jeebiz.admin.extras.core.service.IDataPermissionQueryService;
import net.jeebiz.boot.api.service.BaseServiceImpl;
import net.jeebiz.boot.api.utils.CollectionUtils;
import net.jeebiz.boot.api.utils.Constants;

@Service
public class DataPermissionQueryServiceImpl extends BaseServiceImpl<DataPermission, IDataPermissionQueryDao> implements IDataPermissionQueryService {
	 
	@Override
	public List<DataPermission> getPermissions(String rid, String uid) {
		
		// 获取当前登录用户主体对象
    	ShiroPrincipal principal = SubjectUtils.getPrincipal(ShiroPrincipal.class);
    	
    	// admin 不做数据权限处理
    	if(null == principal || principal.isAdmin()) {
    		return null;
    	}
    	List<DataPermission> retList = new ArrayList<>();
    	// 特殊权限控制
		List<DataSpecialPermission> specialPerms = getDao().getSpecialPermissions(rid, uid);
		if(CollectionUtils.isNotEmpty(specialPerms)) {
			
			// 筛选出非SQL转换的配置
			specialPerms = specialPerms.stream().filter(specialPermission -> {
				// 根据SQL解析限制数据
				String permsSQL = specialPermission.getSql();
				// 用户ID、用户业务ID、用户业务code、角色ID、角色编码、
				return StringUtils.equalsIgnoreCase(permsSQL, Constants.UID) 
						|| StringUtils.equalsIgnoreCase(permsSQL, Constants.UKEY)
						|| StringUtils.equalsIgnoreCase(permsSQL, Constants.UCODE) 
						|| StringUtils.equalsIgnoreCase(permsSQL, Constants.RID)
						|| StringUtils.equalsIgnoreCase(permsSQL, Constants.RKEY);
			}).collect(Collectors.toList());
			
        	// 循环特殊权限
			for (DataSpecialPermission specialPermission : specialPerms) {

				DataPermission permission = new DataPermission();
				permission.setOrder(specialPermission.getOrder());
				permission.setRelation(Relational.OR);
				permission.setStatus(specialPermission.getStatus());
				permission.setTable(specialPermission.getTable());
				
				/**
				 * 数据权限项
				 */
				DataPermissionColumn column = new DataPermissionColumn();
				
				column.setColumn(specialPermission.getColumn());
				column.setCondition(Condition.EQ);
				column.setOrder(specialPermission.getOrder());
				column.setStatus(specialPermission.getStatus());
				
				// 根据SQL解析限制数据
				String permsSQL = specialPermission.getSql();
				// 用户ID
				if(StringUtils.equalsIgnoreCase(permsSQL, Constants.UID)) {
					column.setPerms(StringUtils.defaultString(principal.getUserid()));
				}
				// 用户业务ID
				else if(StringUtils.equalsIgnoreCase(permsSQL, Constants.UKEY)) {
					column.setPerms(StringUtils.defaultString(principal.getUserkey()));
				} 
				// 用户业务code
				else if(StringUtils.equalsIgnoreCase(permsSQL, Constants.UCODE)) {
					column.setPerms(StringUtils.defaultString(principal.getUsercode()));
				} 
				// 角色ID
				else if(StringUtils.equalsIgnoreCase(permsSQL, Constants.RID)) {
					column.setPerms(StringUtils.defaultString(principal.getRoleid()));
				} 
				// 角色编码
				else if(StringUtils.equalsIgnoreCase(permsSQL, Constants.RKEY)) {
					column.setPerms(StringUtils.defaultString(principal.getRole()));
				} 
				
				permission.setColumns(Arrays.asList(column));
				
				retList.add(permission);
					 
			}
		}
		// 数据项控制
		retList.addAll(getDao().getPermissions(rid, uid));
		return retList;
	}

	@Override
	public List<DataPermissionColumn> getPermissionColumns(String gid) {
		return getDao().getPermissionColumns(gid);
	}
	
	@Override
	public List<DataSpecialPermission> getSpecialPermissions(String rid, String uid) {
		
		// 获取当前登录用户主体对象
    	ShiroPrincipal principal = SubjectUtils.getPrincipal(ShiroPrincipal.class);
    	// admin 不做数据权限处理
    	if(null == principal || principal.isAdmin()) {
    		return null;
    	}
    	// 特殊权限控制
		List<DataSpecialPermission> specialPerms = getDao().getSpecialPermissions(rid, uid);
		if(CollectionUtils.isNotEmpty(specialPerms)) {
			
			// 过滤掉非SQL转换的配置
			specialPerms = specialPerms.stream().filter(specialPermission -> {
				// 根据SQL解析限制数据
				String permsSQL = specialPermission.getSql();
				// 用户ID、用户业务ID、用户业务code、角色ID、角色编码、
				return !StringUtils.equalsIgnoreCase(permsSQL, Constants.UID) 
				&& !StringUtils.equalsIgnoreCase(permsSQL, Constants.UKEY)
				&& !StringUtils.equalsIgnoreCase(permsSQL, Constants.UCODE) 
				&& !StringUtils.equalsIgnoreCase(permsSQL, Constants.RID)
				&& !StringUtils.equalsIgnoreCase(permsSQL, Constants.RKEY)
				&& StringUtils.isNotBlank(permsSQL);
			}).collect(Collectors.toList());
			
			// 循环特殊权限
			for (DataSpecialPermission specialPermission : specialPerms) {
				 
				// 根据SQL解析限制数据
				String permsSQL = StringUtils.trim(specialPermission.getSql());
				
				// uid、ukey、rid、rkey
				Matcher matcher = Constants.pattern_find.matcher(permsSQL);
				// 查找匹配的#{}片段
				while (matcher.find()) {
					//在#{}区域内符合当前正则表达式的整段字符
					String segment = matcher.group(0);
					// 取得#{}内容开始结束位置
					int begain = permsSQL.indexOf(segment);
					int end = begain + segment.length();
					// 获取#{}中间的内容
					String cname = matcher.group(1);
					
					// 用户ID
					if(StringUtils.equalsIgnoreCase(cname, Constants.UID)) {
						permsSQL = permsSQL.substring(0, begain) + StringUtils.wrap(principal.getUserid(), "'")  + permsSQL.substring(end);
					} 
					// 用户业务ID
					else if(StringUtils.equalsIgnoreCase(cname, Constants.UKEY)) {
						permsSQL = permsSQL.substring(0, begain) +  StringUtils.wrap(principal.getUserkey(), "'") + permsSQL.substring(end);
					} 
					// 角色ID
					else if(StringUtils.equalsIgnoreCase(cname, Constants.RID)) {
						permsSQL = permsSQL.substring(0, begain) +  StringUtils.wrap(principal.getRoleid(), "'") + permsSQL.substring(end);
					}
					// 用户教工号
					else if(StringUtils.equalsIgnoreCase(cname, Constants.UCODE)) {
						permsSQL = permsSQL.substring(0, begain) +  StringUtils.wrap(principal.getUsercode(), "'") + permsSQL.substring(end);
					}
					// 角色编码
					else if(StringUtils.equalsIgnoreCase(cname, Constants.RKEY)) {
						permsSQL = permsSQL.substring(0, begain) + StringUtils.wrap(principal.getRole(), "'") + permsSQL.substring(end);
					} else {
						permsSQL = permsSQL.substring(0, begain) + "" + permsSQL.substring(end);
					}
				}
				
				if(!(StringUtils.startsWith(permsSQL, "(") && StringUtils.endsWith(permsSQL, ")"))) {
					specialPermission.setSql("(" + permsSQL +  ")");
				} else {
					specialPermission.setSql(permsSQL);
				}
			}
			return specialPerms;
		}
		
		return null;
	}
	
}
