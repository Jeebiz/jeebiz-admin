package io.hiwepy.admin.extras.article.web.mvc;

import java.util.List;

import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.biz.authz.principal.ShiroPrincipal;
import org.apache.shiro.biz.utils.SubjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.biz.utils.StringUtils;
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
import io.hiwepy.admin.extras.article.dao.entities.ArticleEntity;
import io.hiwepy.admin.extras.article.service.IArticleService;
import io.hiwepy.admin.extras.article.setup.Constants;
import io.hiwepy.admin.extras.article.web.dto.ArticleDTO;
import io.hiwepy.admin.extras.article.web.dto.ArticleDetailDTO;
import io.hiwepy.admin.extras.article.web.dto.ArticleNewDTO;
import io.hiwepy.admin.extras.article.web.dto.ArticlePaginationDTO;
import io.hiwepy.admin.extras.article.web.dto.ArticleRenewDTO;
import io.hiwepy.boot.api.ApiRestResponse;
import io.hiwepy.boot.api.annotation.AllowableValues;
import io.hiwepy.boot.api.annotation.BusinessLog;
import io.hiwepy.boot.api.annotation.BusinessType;
import io.hiwepy.boot.api.web.BaseApiController;
import io.hiwepy.boot.api.web.Result;


@Api(tags = "文章管理")
@Validated
@RestController
@RequestMapping("/article/")
public class ArticleController extends BaseApiController {

    @Autowired
    private IArticleService articleService;
    
    @ApiOperation(value = "分页查询文章信息", notes = "分页查询文章信息")
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "body", name = "paginationDTO", value = "筛选条件", dataType = "ArticlePaginationDTO")
	})
	@PostMapping("list")
    @RequiresPermissions("article:list")
	public Result<ArticleDTO> list(@Valid @RequestBody ArticlePaginationDTO paginationDTO){
		
    	ArticleEntity model =  getBeanMapper().map(paginationDTO, ArticleEntity.class);
		Page<ArticleEntity> pageResult = getArticleService().getPagedList(model);
		List<ArticleDTO> retList = Lists.newArrayList();
		for (ArticleEntity keyvalueModel : pageResult.getRecords()) {
			retList.add(getBeanMapper().map(keyvalueModel, ArticleDTO.class));
		}
		return new Result<ArticleDTO>(pageResult, retList);
		
	}
    
    @ApiOperation(value = "分页查询当前登录人可见文章信息", notes = "分页查询当前登录人可见文章信息")
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "body", name = "paginationDTO", value = "筛选条件", dataType = "ArticlePaginationDTO")
	})
	@PostMapping("forme")
    @RequiresAuthentication 
	public Result<ArticleDTO> forme(@Valid @RequestBody ArticlePaginationDTO paginationDTO){
		
    	ArticleEntity model =  getBeanMapper().map(paginationDTO, ArticleEntity.class);
		Page<ArticleEntity> pageResult = getArticleService().getPagedList(model);
		List<ArticleDTO> retList = Lists.newArrayList();
		for (ArticleEntity keyvalueModel : pageResult.getRecords()) {
			retList.add(getBeanMapper().map(keyvalueModel, ArticleDTO.class));
		}
		return new Result<ArticleDTO>(pageResult, retList);
	}
 
	@ApiOperation(value = "创建文章信息", notes = "增加一个新的文章信息")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "body", name = "DTO", value = "文章信息传输对象", dataType = "ArticleNewDTO") 
	})
	@BusinessLog(module = Constants.ARTICLE, business = "创建文章信息", opt = BusinessType.INSERT)
	@PostMapping("new")
	@RequiresPermissions("article:new")
	@ResponseBody
	public ApiRestResponse<String> article(@Valid @RequestBody ArticleNewDTO DTO) throws Exception {
		
		// 新增一条数据库配置记录
		ArticleEntity model = getBeanMapper().map(DTO, ArticleEntity.class);
		// 登录账号信息
		ShiroPrincipal principal = SubjectUtils.getPrincipal(ShiroPrincipal.class);
		model.setUid(principal.getUserid());
		boolean result = getArticleService().save(model);
		if(result) {
			return success("article.new.success", result);
		}
		// 逻辑代码，如果发生异常将不会被执行
		return fail("article.new.fail", result);
	}
	
	@ApiOperation(value = "删除文章信息", notes = "删除文章信息")
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "query", name = "ids", value = "文章信息id,多个用,拼接", required = true, dataType = "String")
	})
	@BusinessLog(module = Constants.ARTICLE, business = "删除文章信息", opt = BusinessType.UPDATE)
	@GetMapping("delete")
	@RequiresPermissions("article:renew")
	public ApiRestResponse<String> delete(@RequestParam String ids) throws Exception {
		// 执行文章信息删除操作
		List<String> idList = Lists.newArrayList(StringUtils.tokenizeToStringArray(ids));
		boolean result = getArticleService().removeBatchByIds(idList);
		if(result) {
			return success("article.delete.success", result);
		}
		// 逻辑代码，如果发生异常将不会被执行
		return fail("article.delete.fail", result);
	}
	 
	@ApiOperation(value = "更新文章信息", notes = "更新文章信息")
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "body", name = "DTO", value = "文章信息", required = true, dataType = "ArticleRenewDTO"),
	})
	@BusinessLog(module = Constants.ARTICLE, business = "更新文章信息", opt = BusinessType.UPDATE)
	@PostMapping("renew")
	@RequiresPermissions("article:renew")
	@ResponseBody
	public ApiRestResponse<String> renew(@Valid @RequestBody ArticleRenewDTO DTO) throws Exception {
		ArticleEntity model = getBeanMapper().map(DTO, ArticleEntity.class);
		// 登录账号信息
		ShiroPrincipal principal = SubjectUtils.getPrincipal(ShiroPrincipal.class);
		model.setUid(principal.getUserid());
		boolean result = getArticleService().updateById(model);
		if(result) {
			return success("article.renew.success", result);
		}
		// 逻辑代码，如果发生异常将不会被执行
		return fail("article.renew.fail", result);
	}
	
	@ApiOperation(value = "更新文章状态", notes = "更新文章状态")
   	@ApiImplicitParams({
   		@ApiImplicitParam(paramType = "query", name = "id", required = true, value = "文章id", dataType = "String"),
   		@ApiImplicitParam(paramType = "query", name = "status", required = true, value = "文章状态（0:删除|1:正常）", dataType = "String", allowableValues = "1,0")
   	})
   	@BusinessLog(module = Constants.ARTICLE, business = "更新文章状态", opt = BusinessType.UPDATE)
   	@GetMapping("status")
   	@RequiresPermissions("article:status")
   	@ResponseBody
   	public ApiRestResponse<String> status(@RequestParam String id, @AllowableValues(allows = "0,1",message = "文章状态错误") @RequestParam String status) throws Exception {
   		int result = getArticleService().setStatus(id, status);
   		if(result == 1) {
   			return success("article.status.success", result);
   		}
   		// 逻辑代码，如果发生异常将不会被执行
   		return fail("article.status.fail", result);
   	}
   	
	@ApiOperation(value = "更新文章审核状态", notes = "更新文章审核状态")
   	@ApiImplicitParams({
   		@ApiImplicitParam(paramType = "query", name = "id", required = true, value = "文章id", dataType = "String"),
   		@ApiImplicitParam(paramType = "query", name = "status", required = true, value = "文章审核状态（0:未通过|1:通过）", dataType = "String", allowableValues = "1,0")
   	})
   	@BusinessLog(module = Constants.ARTICLE, business = "更新文章审核状态", opt = BusinessType.UPDATE)
   	@GetMapping("review")
   	@RequiresPermissions("article:review")
   	@ResponseBody
   	public ApiRestResponse<String> review(@RequestParam String id, @AllowableValues(allows = "0,1",message = "文章审核状态错误") @RequestParam String status) throws Exception {
   		int result = getArticleService().setRecommend(id, status);
   		if(result == 1) {
   			return success("article.review.success", result);
   		}
   		// 逻辑代码，如果发生异常将不会被执行
   		return fail("article.review.fail", result);
   	}
	
	@ApiOperation(value = "更新文章推荐状态", notes = "更新文章推荐状态")
   	@ApiImplicitParams({
   		@ApiImplicitParam(paramType = "query", name = "id", required = true, value = "文章id", dataType = "String"),
   		@ApiImplicitParam(paramType = "query", name = "status", required = true, value = "文章推荐状态（0:未推荐|1:推荐）", dataType = "String", allowableValues = "1,0")
   	})
   	@BusinessLog(module = Constants.ARTICLE, business = "更新文章推荐状态", opt = BusinessType.UPDATE)
   	@GetMapping("recommend")
   	@RequiresPermissions("article:recommend")
   	@ResponseBody
   	public ApiRestResponse<String> recommend(@RequestParam String id, @AllowableValues(allows = "0,1",message = "文章推荐状态错误") @RequestParam String status) throws Exception {
   		int result = getArticleService().setRecommend(id, status);
   		if(result == 1) {
   			return success("article.recommend.success", result);
   		}
   		// 逻辑代码，如果发生异常将不会被执行
   		return fail("article.recommend.fail", result);
   	}
	
	@ApiOperation(value = "查询文章详情（编辑时调用）", notes = "根据id查询文章详情")
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "query", name = "id", required = true, value = "文章信息id", dataType = "String")
	})
	@GetMapping("detail")
	@RequiresAuthentication
	@ResponseBody
	public ApiRestResponse<ArticleDTO> detail(@RequestParam("id") String id) throws Exception { 
		ArticleEntity model = getArticleService().getModel(id);
		if(model == null) {
			return ApiRestResponse.fail(getMessage("article.get.empty"));
		}
		return ApiRestResponse.success(getBeanMapper().map(model, ArticleDTO.class));
	}
	
	@ApiOperation(value = "查询文章详情（访问时调用）", notes = "根据id查询文章详情")
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "query", name = "id", required = true, value = "文章信息id", dataType = "String")
	})
	@GetMapping("access")
	@RequiresAuthentication
	@ResponseBody
	public ApiRestResponse<ArticleDetailDTO> detail2(@RequestParam("id") String id) throws Exception { 
		ArticleDetailDTO model = getArticleService().getDetail(id);
		if(model == null) {
			return ApiRestResponse.fail(getMessage("article.get.empty"));
		}
		return ApiRestResponse.success(model);
	}
    
	public IArticleService getArticleService() {
		return articleService;
	}

}
