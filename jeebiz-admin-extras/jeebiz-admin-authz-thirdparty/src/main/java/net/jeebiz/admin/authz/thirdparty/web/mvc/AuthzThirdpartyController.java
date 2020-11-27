/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.authz.thirdparty.web.mvc;

import java.util.List;

import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.biz.authz.principal.ShiroPrincipal;
import org.apache.shiro.biz.utils.SubjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import net.jeebiz.admin.authz.thirdparty.dao.entities.AuthzThirdpartyModel;
import net.jeebiz.admin.authz.thirdparty.service.IAuthzThirdpartyService;
import net.jeebiz.admin.authz.thirdparty.setup.Constants;
import net.jeebiz.admin.authz.thirdparty.setup.ThirdpartyType;
import net.jeebiz.admin.authz.thirdparty.web.dto.AuthzThirdpartyPaginationDTO;
import net.jeebiz.admin.authz.thirdparty.web.dto.AuthzThirdpartyDTO;
import net.jeebiz.boot.api.ApiRestResponse;
import net.jeebiz.boot.api.annotation.BusinessLog;
import net.jeebiz.boot.api.annotation.BusinessType;
import net.jeebiz.boot.api.web.BaseMapperController;
import net.jeebiz.boot.api.web.Result;

/**
 * 第三方账号登录：授权维护
 */
@Api(tags = "第三方账号登录：授权维护（Ok）")
@RestController
@RequestMapping("/authz/thirdpt/")
@Validated
public class AuthzThirdpartyController extends BaseMapperController {

	@Autowired
	protected IAuthzThirdpartyService authzThirdpartyService;
	
	@ApiOperation(value = "分页查询第三方授权登录", notes = "分页查询第三方授权登录")
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "body", name = "paginationDTO", value = "分页筛选条件", dataType = "AuthzThirdpartyPaginationDTO")
	})
	@PostMapping("list")
	@RequiresPermissions("thirdpt:list")
	@ResponseBody
	public Result<AuthzThirdpartyDTO> list(@Valid @RequestBody AuthzThirdpartyPaginationDTO paginationDTO){
		
		AuthzThirdpartyModel model =  getBeanMapper().map(paginationDTO, AuthzThirdpartyModel.class);
		Page<AuthzThirdpartyModel> pageResult = getAuthzThirdpartyService().getPagedList(model);
		List<AuthzThirdpartyDTO> retList = Lists.newArrayList();
		for (AuthzThirdpartyModel thirdpartyModel : pageResult.getRecords()) {
			retList.add(getBeanMapper().map(thirdpartyModel, AuthzThirdpartyDTO.class));
		}
		
		return new Result<AuthzThirdpartyDTO>(pageResult, retList);
		
	}

	@ApiOperation(value = "解绑定第三方账号登录", notes = "删除登录账号绑定的第三方登录账号")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "type", required = true, value = "第三方账号类型：(wxma:微信小程序,wxmp:微信公众号,qq:腾讯QQ,weibo:新浪微博,yiban:易班,)", dataType = "String", 
				allowableValues = "wxma,wxmp,qq,weibo,yiban"),
		@ApiImplicitParam(name = "openid", required = true, value = "第三方平台OpenID（通常指第三方账号体系下某应用中用户的唯一ID）", dataType = "String")
	})
	@BusinessLog(module = Constants.AUTHZ_THIRDPT, business = "删除登录账号绑定的第三方登录账号", opt = BusinessType.DELETE)
	@GetMapping("unbind")
	@RequiresAuthentication
	@ResponseBody
	public ApiRestResponse<String> unbind(@Valid @RequestParam("type") String type, @RequestParam("openid") String openid) throws Exception {
		ShiroPrincipal principal = SubjectUtils.getPrincipal(ShiroPrincipal.class);
		// 判断用户是否已经有绑定
		int count = getAuthzThirdpartyService().getCountByUid(ThirdpartyType.valueOfIgnoreCase(type), principal.getUserid());
		if(count == 0) {
			return fail("authz.thirdparty.unbind.not-found"); 
		}
		int total = getAuthzThirdpartyService().unbind(ThirdpartyType.valueOfIgnoreCase(type), openid);
		if(total > 0) {
			return success("authz.thirdparty.unbind.success", total); 
		}
		return fail("authz.thirdparty.unbind.fail"); 
	}
	
	public IAuthzThirdpartyService getAuthzThirdpartyService() {
		return authzThirdpartyService;
	}

}
