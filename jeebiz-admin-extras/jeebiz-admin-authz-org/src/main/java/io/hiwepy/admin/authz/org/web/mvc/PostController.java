package io.hiwepy.admin.authz.org.web.mvc;

import java.util.List;

import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.biz.authz.principal.ShiroPrincipal;
import org.apache.shiro.biz.utils.SubjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;

import hitool.core.collections.CollectionUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.hiwepy.admin.authz.org.dao.entities.PostModel;
import io.hiwepy.admin.authz.org.service.IPostService;
import io.hiwepy.admin.authz.org.setup.Constants;
import io.hiwepy.admin.authz.org.web.dto.PostDTO;
import io.hiwepy.admin.authz.org.web.dto.PostNewDTO;
import io.hiwepy.admin.authz.org.web.dto.PostPaginationDTO;
import io.hiwepy.admin.authz.org.web.dto.PostRenewDTO;
import io.hiwepy.boot.api.ApiRestResponse;
import io.hiwepy.boot.api.annotation.BusinessLog;
import io.hiwepy.boot.api.annotation.BusinessType;
import io.hiwepy.boot.api.dao.entities.PairModel;
import io.hiwepy.boot.api.web.BaseApiController;
import io.hiwepy.boot.api.web.Result;

@Api(tags = "组织机构：岗位信息维护")
@RestController
@RequestMapping(value = "/authz/org/post/")
public class PostController extends BaseApiController {
	
	@Autowired
	private IPostService authzPostService;

	@ApiOperation(value = "分页查询岗位信息", notes = "分页查询岗位信息")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "body", name = "paginationDTO", value = "分页查询参数", dataType = "PostPaginationDTO") 
	})
	@BusinessLog(module = Constants.AUTHZ_POST, business = "分页查询岗位信息", opt = BusinessType.SELECT)
	@PostMapping("list")
	@RequiresPermissions("authz-post:list")
	public Result<PostDTO> list(@Valid @RequestBody PostPaginationDTO paginationDTO) throws Exception {
		
		PostModel model = getBeanMapper().map(paginationDTO, PostModel.class);
		Page<PostModel> pageResult = getPostService().getPagedList(model);
		List<PostDTO> retList = Lists.newArrayList();
		for (PostModel postModel : pageResult.getRecords()) {
			retList.add(getBeanMapper().map(postModel, PostDTO.class));
		}
		
		return new Result<PostDTO>(pageResult, retList);
		
	}
	
	@ApiOperation(value = "岗位信息：键值对集合", notes = "根据部门id编码查询岗位信息")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "deptId", value = "部门id编码", dataType = "String")
	})
	@BusinessLog(module = Constants.AUTHZ_POST, business = "查询岗位信息", opt = BusinessType.SELECT)
	@GetMapping("pairs")
	@RequiresPermissions("authz-post:list")
	public ApiRestResponse<List<PairModel>> pairs(@RequestParam(required = false) String deptId) throws Exception {
		return ApiRestResponse.success(getPostService().getPairValues(deptId));
	}
	
	@ApiOperation(value = "岗位信息：列表集合", notes = "根据部门id编码查询岗位信息")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "deptId", value = "部门id编码", dataType = "String")
	})
	@BusinessLog(module = Constants.AUTHZ_POST, business = "查询岗位信息", opt = BusinessType.SELECT)
	@GetMapping("list")
	@RequiresAuthentication
	public ApiRestResponse<List<PostDTO>> list(@RequestParam(required = false) String deptId) throws Exception {
		
		List<PostModel> resultList = getPostService().getModelList(deptId);
		if( CollectionUtils.isEmpty(resultList)) {
			return ApiRestResponse.fail(getMessage("authz.post.not-found"));
		}
		List<PostDTO> retList = Lists.newArrayList();
		for (PostModel model : resultList) {
			retList.add(getBeanMapper().map(model, PostDTO.class));
		}
		return ApiRestResponse.success(retList);
		
	}
	
	@ApiOperation(value = "创建岗位信息", notes = "增加一个新的岗位信息")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "body", name = "postDTO", value = "岗位信息", required = true, dataType = "PostNewDTO") 
	})
	@BusinessLog(module = Constants.AUTHZ_POST, business = "创建岗位信息", opt = BusinessType.INSERT)
	@PostMapping("new")
	@RequiresPermissions("authz-post:new")
	public ApiRestResponse<String> post(@Valid @RequestBody PostNewDTO postDTO) throws Exception {
		PostModel model = getBeanMapper().map(postDTO, PostModel.class);
		ShiroPrincipal principal = SubjectUtils.getPrincipal(ShiroPrincipal.class);
		model.setCreator(principal.getUserid());
		// 新增一条数据库配置记录
		boolean result = getPostService().save(model);
		if(result) {
			return success("authz.post.new.success", result);
		}
		return fail("authz.post.new.fail", result);
	}
	
	@ApiOperation(value = "更新岗位信息", notes = "更新岗位信息")
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "body", name = "postDTO", value = "岗位信息", required = true, dataType = "PostRenewDTO"),
	})
	@BusinessLog(module = Constants.AUTHZ_POST, business = "更新岗位信息", opt = BusinessType.UPDATE)
	@PostMapping("renew")
	@RequiresPermissions("authz-post:renew")
	public ApiRestResponse<String> renew(@Valid @RequestBody PostRenewDTO postDTO) throws Exception {
		PostModel model = getBeanMapper().map(postDTO, PostModel.class);
		boolean result = getPostService().updateById(model);
		if(result) {
			return success("authz.post.renew.success", result);
		}
		// 逻辑代码，如果发生异常将不会被执行
		return fail("authz.post.renew.fail", result);
	}
	
	@ApiOperation(value = "更新岗位信息状态", notes = "更新岗位信息状态")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id", required = true, value = "岗位信息id", dataType = "String"),
		@ApiImplicitParam(name = "status", required = true, value = "岗位信息状态", dataType = "String", allowableValues = "1,0")
	})
	@BusinessLog(module = Constants.AUTHZ_POST, business = "更新岗位信息状态", opt = BusinessType.UPDATE)
	@PostMapping("status")
	@RequiresPermissions("authz-post:status")
	public ApiRestResponse<String> status(@RequestParam String id, @RequestParam String status) throws Exception {
		int result = getPostService().setStatus(id, status);
		if(result == 1) {
			return success("authz.post.status.success", result);
		}
		// 逻辑代码，如果发生异常将不会被执行
		return fail("authz.post.status.fail", result);
	}
	
	@ApiOperation(value = "删除岗位信息", notes = "删除岗位信息")
	@ApiImplicitParams({ 
		@ApiImplicitParam( name = "id", value = "岗位信息id", required = true, dataType = "String")
	})
	@BusinessLog(module = Constants.AUTHZ_POST, business = "删除岗位信息", opt = BusinessType.UPDATE)
	@GetMapping("delete")
	@RequiresPermissions("authz-post:delete")
	public ApiRestResponse<String> delete(@RequestParam("id") String id) throws Exception {
		// 执行岗位信息删除操作
		boolean result = getPostService().removeById(id);
		if(result) {
			return success("authz.post.delete.success", result);
		}
		// 逻辑代码，如果发生异常将不会被执行
		return fail("authz.post.delete.fail", result);
	}
	
	@ApiOperation(value = "查询岗位信息", notes = "根据id查询岗位信息")
	@ApiImplicitParams({ 
		@ApiImplicitParam( name = "id", required = true, value = "岗位信息id", dataType = "String")
	})
	@BusinessLog(module = Constants.AUTHZ_POST, business = "查询岗位信息", opt = BusinessType.SELECT)
	@GetMapping("detail")
	@RequiresPermissions("authz-post:detail")
	public ApiRestResponse<PostDTO> detail(@RequestParam("id") String id) throws Exception { 
		PostModel model = getPostService().getModel(id);
		if( model == null) {
			return ApiRestResponse.fail(getMessage("authz.post.not-found"));
		}
		return ApiRestResponse.success(getBeanMapper().map(model, PostDTO.class));
	}

	public IPostService getPostService() {
		return authzPostService;
	}

	public void setPostService(IPostService authzPostService) {
		this.authzPostService = authzPostService;
	}

}
