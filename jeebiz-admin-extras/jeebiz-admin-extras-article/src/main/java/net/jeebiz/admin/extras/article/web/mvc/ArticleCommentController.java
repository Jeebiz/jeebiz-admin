package net.jeebiz.admin.extras.article.web.mvc;//


import java.util.List;

import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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
import net.jeebiz.admin.extras.article.dao.entities.ArticleCommentModel;
import net.jeebiz.admin.extras.article.service.IArticleCommentService;
import net.jeebiz.admin.extras.article.setup.Constants;
import net.jeebiz.admin.extras.article.web.dto.ArticleCommentDTO;
import net.jeebiz.admin.extras.article.web.dto.ArticleCommentNewDTO;
import net.jeebiz.admin.extras.article.web.dto.ArticleCommentPaginationDTO;
import net.jeebiz.admin.extras.article.web.dto.ArticleCommentRenewDTO;
import net.jeebiz.boot.api.ApiRestResponse;
import net.jeebiz.boot.api.annotation.AllowableValues;
import net.jeebiz.boot.api.annotation.BusinessLog;
import net.jeebiz.boot.api.annotation.BusinessType;
import net.jeebiz.boot.api.dao.entities.PairModel;
import net.jeebiz.boot.api.utils.StringUtils;
import net.jeebiz.boot.api.web.BaseApiController;
import net.jeebiz.boot.api.web.Result;

@Api(tags = "文章评论")
@RestController
@RequestMapping("/article/comment/")
@Validated
public class ArticleCommentController extends BaseApiController {

    @Autowired
    private IArticleCommentService articleCommentService;
    
    @ApiOperation(value = "分页查询文章评论", notes = "分页查询文章评论")
   	@ApiImplicitParams({ 
   		@ApiImplicitParam(paramType = "body", name = "paginationDTO", value = "用户信息筛选条件", dataType = "ArticleCommentPaginationDTO")
   	})
   	@PostMapping("list")
    @RequiresPermissions("article-comment:list")
   	public Result<ArticleCommentDTO> list(@Valid @RequestBody ArticleCommentPaginationDTO paginationDTO){
   		
       	ArticleCommentModel model =  getBeanMapper().map(paginationDTO, ArticleCommentModel.class);
   		Page<ArticleCommentModel> pageResult = getArticleCommentService().getPagedList(model);
   		List<ArticleCommentDTO> retList = Lists.newArrayList();
   		for (ArticleCommentModel keyvalueModel : pageResult.getRecords()) {
   			retList.add(getBeanMapper().map(keyvalueModel, ArticleCommentDTO.class));
   		}
   		return new Result<ArticleCommentDTO>(pageResult, retList);
   		
   	}

   	@ApiOperation(value = "根据分组查询文章评论（键值对）", notes = "根据分组查询文章评论（键值对）")
   	@GetMapping("pairs")
   	@RequiresAuthentication
   	@ResponseBody
   	public ApiRestResponse<List<PairModel>> pairs() throws Exception {
   		return ApiRestResponse.success(getArticleCommentService().getPairList());
   	}
    
   	@ApiOperation(value = "创建文章评论", notes = "增加一个新的文章评论")
   	@ApiImplicitParams({
   		@ApiImplicitParam(paramType = "body", name = "DTO", value = "文章评论传输对象", dataType = "ArticleCommentNewDTO") 
   	})
   	@BusinessLog(module = Constants.ARTICLE_COMMENT, business = "创建文章评论", opt = BusinessType.INSERT)
   	@PostMapping("new")
   	@RequiresPermissions("article-comment:new")
   	@ResponseBody
   	public ApiRestResponse<String> comment(@Valid @RequestBody ArticleCommentNewDTO DTO) throws Exception {
   		
   		// 新增一条数据库配置记录
   		ArticleCommentModel model = getBeanMapper().map(DTO, ArticleCommentModel.class);
   		int result = getArticleCommentService().insert(model);
   		if(result == 1) {
   			return success("article.comment.new.success", result);
   		}
   		// 逻辑代码，如果发生异常将不会被执行
   		return fail("article.comment.new.fail", result);
   	}
   	
   	@ApiOperation(value = "删除文章评论", notes = "删除文章评论")
   	@ApiImplicitParams({ 
   		@ApiImplicitParam(paramType = "query", name = "ids", value = "文章评论id,多个用,拼接", required = true, dataType = "String")
   	})
   	@BusinessLog(module = Constants.ARTICLE_COMMENT, business = "删除文章评论", opt = BusinessType.UPDATE)
   	@GetMapping("delete")
   	@RequiresPermissions("article-comment:delete")
   	public ApiRestResponse<String> delete(@RequestParam String ids) throws Exception {
   		// 执行文章评论删除操作
   		List<String> idList = Lists.newArrayList(StringUtils.tokenizeToStringArray(ids));
   		int result = getArticleCommentService().batchDelete(idList);
   		if(result > 0) {
   			return success("article.comment.delete.success", result);
   		}
   		// 逻辑代码，如果发生异常将不会被执行
   		return fail("article.comment.delete.fail", result);
   	}
   	 
   	@ApiOperation(value = "更新文章评论", notes = "更新文章评论")
   	@ApiImplicitParams({ 
   		@ApiImplicitParam(paramType = "body", name = "DTO", value = "文章评论", required = true, dataType = "ArticleCommentRenewDTO"),
   	})
   	@BusinessLog(module = Constants.ARTICLE_COMMENT, business = "更新文章评论", opt = BusinessType.UPDATE)
   	@PostMapping("renew")
   	@RequiresPermissions("article-comment:renew")
   	@ResponseBody
   	public ApiRestResponse<String> renew(@Valid @RequestBody ArticleCommentRenewDTO DTO) throws Exception {
   		
   		ArticleCommentModel model = getBeanMapper().map(DTO, ArticleCommentModel.class);
   		int result = getArticleCommentService().update(model);
   		if(result == 1) {
   			return success("article.comment.renew.success", result);
   		}
   		// 逻辑代码，如果发生异常将不会被执行
   		return fail("article.comment.renew.fail", result);
   	}
   	
   	@ApiOperation(value = "更新文章评论状态", notes = "更新文章评论状态")
   	@ApiImplicitParams({
   		@ApiImplicitParam(paramType = "query", name = "id", required = true, value = "文章评论id", dataType = "String"),
   		@ApiImplicitParam(paramType = "query", name = "status", required = true, value = "文章评论状态（0:删除|1:正常）", dataType = "String", allowableValues = "1,0")
   	})
   	@BusinessLog(module = Constants.ARTICLE_COMMENT, business = "更新文章评论状态", opt = BusinessType.UPDATE)
   	@GetMapping("status")
   	@RequiresPermissions("article-comment:status")
   	@ResponseBody
   	public ApiRestResponse<String> status(@RequestParam String id, @AllowableValues(allows = "0,1",message = "文章评论状态错误") @RequestParam String status) throws Exception {
   		int result = getArticleCommentService().setStatus(id, status);
   		if(result == 1) {
   			return success("article.comment.status.success", result);
   		}
   		// 逻辑代码，如果发生异常将不会被执行
   		return fail("article.comment.status.fail", result);
   	}
   	
	@ApiOperation(value = "更新文章评论审核状态", notes = "更新文章评论审核状态")
   	@ApiImplicitParams({
   		@ApiImplicitParam(paramType = "query", name = "id", required = true, value = "文章评论id", dataType = "String"),
   		@ApiImplicitParam(paramType = "query", name = "status", required = true, value = "文章评论审核状态（0:未通过|1:通过）", dataType = "String", allowableValues = "1,0")
   	})
   	@BusinessLog(module = Constants.ARTICLE_COMMENT, business = "更新文章评论审核状态", opt = BusinessType.UPDATE)
   	@GetMapping("review")
   	@RequiresPermissions("article-comment:review")
   	@ResponseBody
   	public ApiRestResponse<String> review(@RequestParam String id, @AllowableValues(allows = "0,1",message = "文章评论审核状态错误") @RequestParam String status) throws Exception {
   		int result = getArticleCommentService().setRecommend(id, status);
   		if(result == 1) {
   			return success("article.comment.review.success", result);
   		}
   		// 逻辑代码，如果发生异常将不会被执行
   		return fail("article.comment.review.fail", result);
   	}
	
	@ApiOperation(value = "更新文章评论推荐状态", notes = "更新文章评论推荐状态")
   	@ApiImplicitParams({
   		@ApiImplicitParam(paramType = "query", name = "id", required = true, value = "文章评论id", dataType = "String"),
   		@ApiImplicitParam(paramType = "query", name = "status", required = true, value = "文章评论推荐状态（0:未推荐|1:推荐）", dataType = "String", allowableValues = "1,0")
   	})
   	@BusinessLog(module = Constants.ARTICLE_COMMENT, business = "更新文章评论推荐状态", opt = BusinessType.UPDATE)
   	@GetMapping("recommend")
   	@RequiresPermissions("article-comment:recommend")
   	@ResponseBody
   	public ApiRestResponse<String> recommend(@RequestParam String id, @AllowableValues(allows = "0,1",message = "文章评论推荐状态错误") @RequestParam String status) throws Exception {
   		int result = getArticleCommentService().setRecommend(id, status);
   		if(result == 1) {
   			return success("article.comment.recommend.success", result);
   		}
   		// 逻辑代码，如果发生异常将不会被执行
   		return fail("article.comment.recommend.fail", result);
   	}
   	
   	@ApiOperation(value = "查询文章评论", notes = "根据id查询文章评论")
   	@ApiImplicitParams({ 
   		@ApiImplicitParam(paramType = "query", name = "id", required = true, value = "文章评论id", dataType = "String")
   	})
   	@BusinessLog(module = Constants.ARTICLE_COMMENT, business = "查询文章评论", opt = BusinessType.SELECT)
   	@GetMapping("detail")
   	@RequiresAuthentication
   	@ResponseBody
   	public ApiRestResponse<ArticleCommentDTO> detail(@RequestParam("id") String id) throws Exception { 
   		ArticleCommentModel model = getArticleCommentService().getModel(id);
   		if(model == null) {
   			return ApiRestResponse.fail(getMessage("article.comment.get.empty"));
   		}
   		return ApiRestResponse.success(getBeanMapper().map(model, ArticleCommentDTO.class));
   	}

    public IArticleCommentService getArticleCommentService() {
		return articleCommentService;
	}
}
