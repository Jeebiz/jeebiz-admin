package net.jeebiz.admin.extras.article.web.mvc;

import java.util.List;

import javax.validation.Valid;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.biz.utils.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import net.jeebiz.admin.extras.article.dao.entities.ArticleAttachmentEntity;
import net.jeebiz.admin.extras.article.service.IArticleAttachmentService;
import net.jeebiz.admin.extras.article.setup.Constants;
import net.jeebiz.admin.extras.article.web.dto.ArticleAttachmentDTO;
import net.jeebiz.admin.extras.article.web.dto.ArticleAttachmentNewDTO;
import net.jeebiz.boot.api.ApiRestResponse;
import net.jeebiz.boot.api.annotation.BusinessLog;
import net.jeebiz.boot.api.annotation.BusinessType;
import net.jeebiz.boot.api.web.BaseApiController;


@Api(tags = "文章附件管理")
@Validated
@RestController
@RequestMapping("/article/att/")
public class ArticleAttachmentController extends BaseApiController {

    @Autowired
    private IArticleAttachmentService articleAttachmentService;
    
    @ApiOperation(value = "查询文章附件", notes = "查询文章附件")
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "query", name = "id", required = true, value = "文章id", dataType = "String")
	})
	@PostMapping("list")
    @RequiresPermissions(value = {"article:list","article:new","article:renew"}, logical = Logical.OR)
	public ApiRestResponse<List<ArticleAttachmentDTO>> list(@RequestParam("id") String id){
		List<ArticleAttachmentEntity> modelList = getArticleAttachmentService().getModelList(id);
		List<ArticleAttachmentDTO> retList = Lists.newArrayList();
		for (ArticleAttachmentEntity model : modelList) {
			retList.add(getBeanMapper().map(model, ArticleAttachmentDTO.class));
		}
		return ApiRestResponse.success(retList);
	}
    
	@ApiOperation(value = "创建文章附件", notes = "增加一个新的文章附件")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "body", name = "DTO", value = "文章附件传输对象", dataType = "ArticleAttachmentNewDTO") 
	})
	@BusinessLog(module = Constants.ARTICLE_ATT, business = "创建文章附件", opt = BusinessType.INSERT)
	@PostMapping("upload")
	@RequiresPermissions(value = {"article:new","article:renew"}, logical = Logical.OR)
	public ApiRestResponse<String> upload(@Valid @RequestBody ArticleAttachmentNewDTO DTO) throws Exception {
		
		// 新增一条数据库配置记录
		ArticleAttachmentEntity model = getBeanMapper().map(DTO, ArticleAttachmentEntity.class);
		boolean result = getArticleAttachmentService().save(model);
		if(result) {
			return success("article.att.new.success", result);
		}
		// 逻辑代码，如果发生异常将不会被执行
		return fail("article.att.new.fail", result);
	}
	
	@ApiOperation(value = "删除文章附件", notes = "删除文章附件")
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "query", name = "ids", value = "文章附件id,多个用,拼接", required = true, dataType = "String")
	})
	@BusinessLog(module = Constants.ARTICLE_ATT, business = "删除文章附件", opt = BusinessType.UPDATE)
	@GetMapping("delete")
	@RequiresPermissions("article:delete")
	public ApiRestResponse<String> delete(@RequestParam String ids) throws Exception {
		// 执行文章附件删除操作
		List<String> idList = Lists.newArrayList(StringUtils.tokenizeToStringArray(ids));
		boolean result = getArticleAttachmentService().removeBatchByIds(idList);
		if(result) {
			return success("article.att.delete.success", result);
		}
		// 逻辑代码，如果发生异常将不会被执行
		return fail("article.att.delete.fail", result);
	}
    
	public IArticleAttachmentService getArticleAttachmentService() {
		return articleAttachmentService;
	}

}
