package net.jeebiz.admin.extras.article.web.mvc;//


import java.util.List;

import javax.validation.Valid;

import net.jeebiz.admin.extras.article.web.dto.ArticleCategoryStatusRenewDTO;
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
import net.jeebiz.admin.extras.article.dao.entities.ArticleCategoryModel;
import net.jeebiz.admin.extras.article.service.IArticleCategoryService;
import net.jeebiz.admin.extras.article.setup.Constants;
import net.jeebiz.admin.extras.article.web.vo.ArticleCategoryVo;
import net.jeebiz.admin.extras.article.web.dto.ArticleCategoryNewDTO;
import net.jeebiz.admin.extras.article.web.dto.ArticleCategoryPaginationDTO;
import net.jeebiz.admin.extras.article.web.dto.ArticleCategoryRenewDTO;
import net.jeebiz.boot.api.ApiRestResponse;
import net.jeebiz.boot.api.annotation.AllowableValues;
import net.jeebiz.boot.api.annotation.BusinessLog;
import net.jeebiz.boot.api.annotation.BusinessType;
import net.jeebiz.boot.api.dao.entities.PairModel;
import org.springframework.biz.utils.StringUtils;
import net.jeebiz.boot.api.web.BaseApiController;
import net.jeebiz.boot.api.web.Result;


@Api(tags = "文章分类查询")
@RestController
@RequestMapping("/article/category/")
@Validated
public class ArticleCategoryController extends BaseApiController {

    @Autowired
    private IArticleCategoryService articleCategoryService;

    @ApiOperation(value = "分页查询文章分类", notes = "分页查询文章分类")
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "body", name = "paginationDTO", value = "用户信息筛选条件", dataType = "ArticleCategoryPaginationDTO")
	})
	@PostMapping("list")
    @RequiresPermissions("article-category:list")
	public Result<ArticleCategoryVo> list(@Valid @RequestBody ArticleCategoryPaginationDTO paginationDTO){
		
    	ArticleCategoryModel model =  getBeanMapper().map(paginationDTO, ArticleCategoryModel.class);
		Page<ArticleCategoryModel> pageResult = getArticleCategoryService().getPagedList(model);
		List<ArticleCategoryVo> retList = Lists.newArrayList();
		for (ArticleCategoryModel categoryModel : pageResult.getRecords()) {

			ArticleCategoryVo categoryVo = getBeanMapper().map(categoryModel, ArticleCategoryVo.class);

			categoryVo.setUid(categoryModel.getCreator());
			categoryVo.setTime24(categoryModel.getCreateTime());

			retList.add(categoryVo);
		}
		return new Result<ArticleCategoryVo>(pageResult, retList);
		
	}

	@ApiOperation(value = "根据分组查询文章分类（键值对）", notes = "根据分组查询文章分类（键值对）")
	@GetMapping("pairs")
	@RequiresAuthentication
	@ResponseBody
	public ApiRestResponse<List<PairModel>> pairs() throws Exception {
		return ApiRestResponse.success(getArticleCategoryService().getPairList());
	}
 
	@ApiOperation(value = "创建文章分类", notes = "增加一个新的文章分类")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "body", name = "DTO", value = "文章分类传输对象", dataType = "ArticleCategoryNewDTO") 
	})
	@BusinessLog(module = Constants.ARTICLE_CATEGORY, business = "创建文章分类", opt = BusinessType.INSERT)
	@PostMapping("new")
	@RequiresPermissions("article-category:new")
	@ResponseBody
	public ApiRestResponse<String> keygroup(@Valid @RequestBody ArticleCategoryNewDTO DTO) throws Exception {
		
		// 检查名称是否存在
		int ct = getArticleCategoryService().getCountByName(DTO.getName(), null);
		if(ct > 0) {
			return fail("article.category.new.name.conflict");
		}
		// 登录账号信息
		ShiroPrincipal principal = SubjectUtils.getPrincipal(ShiroPrincipal.class);

		// 新增一条数据库配置记录
		ArticleCategoryModel model = getBeanMapper().map(DTO, ArticleCategoryModel.class);
		model.setCreator(principal.getUserid());
		boolean result = getArticleCategoryService().save(model);
		if(result) {
			return success("article.category.new.success", result);
		}
		// 逻辑代码，如果发生异常将不会被执行
		return fail("article.category.new.fail", result);
	}
	
	@ApiOperation(value = "删除文章分类", notes = "删除文章分类")
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "query", name = "ids", value = "文章分类id,多个用,拼接", required = true, dataType = "String")
	})
	@BusinessLog(module = Constants.ARTICLE_CATEGORY, business = "删除文章分类", opt = BusinessType.UPDATE)
	@GetMapping("delete")
	@RequiresPermissions("article-category:renew")
	public ApiRestResponse<String> delete(@RequestParam String ids) throws Exception {
		// 执行文章分类删除操作
		List<String> idList = Lists.newArrayList(StringUtils.tokenizeToStringArray(ids));
		boolean result = getArticleCategoryService().removeByIds(idList);
		if(result) {
			return success("article.category.delete.success", result);
		}
		// 逻辑代码，如果发生异常将不会被执行
		return fail("article.category.delete.fail", result);
	}
	 
	@ApiOperation(value = "更新文章分类", notes = "更新文章分类")
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "body", name = "DTO", value = "文章分类", required = true, dataType = "ArticleCategoryRenewDTO"),
	})
	@BusinessLog(module = Constants.ARTICLE_CATEGORY, business = "更新文章分类", opt = BusinessType.UPDATE)
	@PostMapping("renew")
	@RequiresPermissions("article-category:renew")
	@ResponseBody
	public ApiRestResponse<String> renew(@Valid @RequestBody ArticleCategoryRenewDTO DTO) throws Exception {
		
		// 检查名称是否存在
		int ct = getArticleCategoryService().getCountByName(DTO.getName(), DTO.getId());
		if(ct > 0) {
			return fail("article.category.renew.value.conflict");
		}
		
		ArticleCategoryModel model = getBeanMapper().map(DTO, ArticleCategoryModel.class);
		boolean result = getArticleCategoryService().updateById(model);
		if(result) {
			return success("article.category.renew.success", result);
		}
		// 逻辑代码，如果发生异常将不会被执行
		return fail("article.category.renew.fail", result);
	}
	
	@ApiOperation(value = "更新文章分类状态", notes = "更新文章分类状态")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "body", name = "DTO", value = "文章分类", required = true, dataType = "ArticleCategoryRenewDTO"),
	})
	@BusinessLog(module = Constants.ARTICLE_CATEGORY, business = "更新文章分类状态", opt = BusinessType.UPDATE)
	@PostMapping("status")
	@RequiresPermissions("article-category:status")
	@ResponseBody
	public ApiRestResponse<String> status(@Valid @RequestBody ArticleCategoryStatusRenewDTO renewDTO ) throws Exception {
		int result = getArticleCategoryService().setStatus(renewDTO.getId(), renewDTO.getStatus());
		if(result == 1) {
			return success("article.category.status.success", result);
		}
		// 逻辑代码，如果发生异常将不会被执行
		return fail("article.category.status.fail", result);
	}
	
	@ApiOperation(value = "查询文章分类信息", notes = "根据id查询文章分类信息")
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "query", name = "id", required = true, value = "文章分类id", dataType = "String")
	})
	@BusinessLog(module = Constants.ARTICLE_CATEGORY, business = "查询文章分类信息", opt = BusinessType.SELECT)
	@GetMapping("detail")
	@RequiresAuthentication
	@ResponseBody
	public ApiRestResponse<ArticleCategoryVo> detail(@RequestParam("id") String id) throws Exception {
		ArticleCategoryModel model = getArticleCategoryService().getModel(id);
		if(model == null) {
			return ApiRestResponse.fail(getMessage("article.category.get.empty"));
		}
		ArticleCategoryVo categoryVo = getBeanMapper().map(model, ArticleCategoryVo.class);

		categoryVo.setUid(model.getCreator());
		categoryVo.setTime24(model.getCreateTime());

		return ApiRestResponse.success(categoryVo);
	}

    public IArticleCategoryService getArticleCategoryService() {
		return articleCategoryService;
	}

}
