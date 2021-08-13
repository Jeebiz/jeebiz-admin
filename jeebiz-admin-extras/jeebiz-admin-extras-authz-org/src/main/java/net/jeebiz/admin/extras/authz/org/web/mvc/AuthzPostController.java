package net.jeebiz.admin.extras.authz.org.web.mvc;

import java.util.List;

import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.biz.authz.principal.ShiroPrincipal;
import org.apache.shiro.biz.utils.SubjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import net.jeebiz.admin.extras.authz.org.dao.entities.AuthzPostModel;
import net.jeebiz.admin.extras.authz.org.service.IAuthzPostService;
import net.jeebiz.admin.extras.authz.org.setup.Constants;
import net.jeebiz.admin.extras.authz.org.web.vo.AuthzPostNewVo;
import net.jeebiz.admin.extras.authz.org.web.vo.AuthzPostPaginationVo;
import net.jeebiz.admin.extras.authz.org.web.vo.AuthzPostRenewVo;
import net.jeebiz.admin.extras.authz.org.web.vo.AuthzPostVo;
import net.jeebiz.boot.api.annotation.BusinessLog;
import net.jeebiz.boot.api.annotation.BusinessType;
import net.jeebiz.boot.api.dao.entities.PairModel;
import net.jeebiz.boot.api.utils.HttpStatus;
import net.jeebiz.boot.api.web.BaseApiController;
import net.jeebiz.boot.api.web.Result;

@Api(tags = "组织机构：岗位信息维护")
@RestController
@RequestMapping(value = "/authz/org/post/")
public class AuthzPostController extends BaseApiController {
	
	@Autowired
	private IAuthzPostService authzPostService;

	@ApiOperation(value = "分页查询岗位信息", notes = "分页查询岗位信息")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "body", name = "paginationVo", value = "分页查询参数", dataType = "AuthzPostPaginationVo") 
	})
	@ApiResponses({ 
		@ApiResponse(code = HttpStatus.SC_OK, message = "操作成功", response = Result.class)
	})
	@BusinessLog(module = Constants.AUTHZ_POST, business = "分页查询岗位信息", opt = BusinessType.SELECT)
	@PostMapping("list")
	@RequiresPermissions("authz-post:list")
	@ResponseBody
	public Object list(@Valid @RequestBody AuthzPostPaginationVo paginationVo) throws Exception {
		
		AuthzPostModel model = getBeanMapper().map(paginationVo, AuthzPostModel.class);
		Page<AuthzPostModel> pageResult = getAuthzPostService().getPagedList(model);
		List<AuthzPostVo> retList = Lists.newArrayList();
		for (AuthzPostModel postModel : pageResult.getRecords()) {
			retList.add(getBeanMapper().map(postModel, AuthzPostVo.class));
		}
		
		return new Result<AuthzPostVo>(pageResult, retList);
		
	}
	
	@ApiOperation(value = "根据部门ID编码查询岗位信息", notes = "根据部门ID编码查询岗位信息")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "deptId", value = "部门ID编码", required = true, dataType = "String")
	})
	@ApiResponses({ 
		@ApiResponse(code = HttpStatus.SC_OK, message = "操作成功", response = PairModel.class, responseContainer = "List")
	})
	@BusinessLog(module = Constants.AUTHZ_POST, business = "查询岗位信息", opt = BusinessType.SELECT)
	@GetMapping("pairs")
	@RequiresPermissions("authz-post:list")
	@ResponseBody
	public Object pairs(@RequestParam String deptId) throws Exception {
		return getAuthzPostService().getPairValues(deptId);
	}
	
	@ApiOperation(value = "创建岗位信息", notes = "增加一个新的岗位信息")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "body", name = "postVo", value = "岗位信息", required = true, dataType = "AuthzPostNewVo") 
	})
	@BusinessLog(module = Constants.AUTHZ_POST, business = "创建岗位信息", opt = BusinessType.INSERT)
	@PostMapping("new")
	@RequiresPermissions("authz-post:new")
	@ResponseBody
	public Object post(@Valid @RequestBody AuthzPostNewVo postVo) throws Exception {
		AuthzPostModel model = getBeanMapper().map(postVo, AuthzPostModel.class);
		ShiroPrincipal principal = SubjectUtils.getPrincipal(ShiroPrincipal.class);
		model.setUserId(principal.getUserid());
		// 新增一条数据库配置记录
		int result = getAuthzPostService().insert(model);
		if(result > 0) {
			return success("authz.post.new.success", result);
		}
		return fail("authz.post.new.fail", result);
	}
	
	@ApiOperation(value = "更新岗位信息", notes = "更新岗位信息")
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "body", name = "postVo", value = "岗位信息", required = true, dataType = "AuthzPostRenewVo"),
	})
	@BusinessLog(module = Constants.AUTHZ_POST, business = "更新岗位信息", opt = BusinessType.UPDATE)
	@PostMapping("renew")
	@RequiresPermissions("authz-post:renew")
	@ResponseBody
	public Object renew(@Valid @RequestBody AuthzPostRenewVo postVo) throws Exception {
		AuthzPostModel model = getBeanMapper().map(postVo, AuthzPostModel.class);
		int result = getAuthzPostService().update(model);
		if(result == 1) {
			return success("authz.post.renew.success", result);
		}
		// 逻辑代码，如果发生异常将不会被执行
		return fail("authz.post.renew.fail", result);
	}
	
	@ApiOperation(value = "更新岗位信息状态", notes = "更新岗位信息状态")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id", required = true, value = "岗位信息ID", dataType = "String"),
		@ApiImplicitParam(name = "status", required = true, value = "岗位信息状态", dataType = "String", allowableValues = "1,0")
	})
	@BusinessLog(module = Constants.AUTHZ_POST, business = "更新岗位信息状态", opt = BusinessType.UPDATE)
	@PostMapping("status")
	@RequiresPermissions("authz-post:status")
	@ResponseBody
	public Object status(@RequestParam String id, @RequestParam String status) throws Exception {
		int result = getAuthzPostService().setStatus(id, status);
		if(result == 1) {
			return success("authz.post.status.success", result);
		}
		// 逻辑代码，如果发生异常将不会被执行
		return fail("authz.post.status.fail", result);
	}
	
	@ApiOperation(value = "删除岗位信息", notes = "删除岗位信息")
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "path", name = "id", value = "岗位信息ID", required = true, dataType = "String")
	})
	@BusinessLog(module = Constants.AUTHZ_POST, business = "删除岗位信息", opt = BusinessType.UPDATE)
	@GetMapping("delete/{id}")
	@RequiresPermissions("authz-post:delete")
	@ResponseBody
	public Object delete(@PathVariable("id") String id) throws Exception {
		// 执行岗位信息删除操作
		int result = getAuthzPostService().delete(id);
		if(result > 0) {
			return success("authz.post.delete.success", result);
		}
		// 逻辑代码，如果发生异常将不会被执行
		return fail("authz.post.delete.fail", result);
	}
	
	@ApiOperation(value = "查询岗位信息", notes = "根据ID查询岗位信息")
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "path",  name = "id", required = true, value = "岗位信息ID", dataType = "String")
	})
	@ApiResponses({ 
		@ApiResponse(code = HttpStatus.SC_OK, message = "操作成功", response = AuthzPostVo.class)
	})
	@BusinessLog(module = Constants.AUTHZ_POST, business = "查询岗位信息", opt = BusinessType.SELECT)
	@GetMapping("detail/{id}")
	@RequiresPermissions("authz-post:detail")
	@ResponseBody
	public Object detail(@PathVariable("id") String id) throws Exception { 
		AuthzPostModel model = getAuthzPostService().getModel(id);
		if( model == null) {
			return fail("authz.post.get.empty");
		}
		return getBeanMapper().map(model, AuthzPostVo.class);
	}

	public IAuthzPostService getAuthzPostService() {
		return authzPostService;
	}

	public void setAuthzPostService(IAuthzPostService authzPostService) {
		this.authzPostService = authzPostService;
	}

}
