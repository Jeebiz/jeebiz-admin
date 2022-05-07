package io.hiwepy.admin.extras.article.web.mvc;//


import java.util.Arrays;
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
import io.hiwepy.admin.extras.article.dao.entities.ArticleTopicEntity;
import io.hiwepy.admin.extras.article.service.IArticleTopicService;
import io.hiwepy.admin.extras.article.setup.Constants;
import io.hiwepy.admin.extras.article.web.dto.ArticleTopicDTO;
import io.hiwepy.admin.extras.article.web.dto.ArticleTopicNewDTO;
import io.hiwepy.admin.extras.article.web.dto.ArticleTopicPaginationDTO;
import io.hiwepy.admin.extras.article.web.dto.ArticleTopicRenewDTO;
import io.hiwepy.admin.extras.article.web.dto.ArticleTopicTreeDTO;
import io.hiwepy.boot.api.ApiRestResponse;
import io.hiwepy.boot.api.annotation.AllowableValues;
import io.hiwepy.boot.api.annotation.BusinessLog;
import io.hiwepy.boot.api.annotation.BusinessType;
import io.hiwepy.boot.api.dao.entities.PairModel;
import io.hiwepy.boot.api.web.BaseApiController;
import io.hiwepy.boot.api.web.Result;

@Api(tags = "文章栏目查询")
@RestController
@RequestMapping("/article/topic/")
@Validated
public class ArticleTopicController extends BaseApiController {

    @Autowired
    private IArticleTopicService articleTopicService;
    
    @ApiOperation(value = "分页查询文章栏目", notes = "分页查询文章栏目")
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "body", name = "paginationDTO", value = "用户信息筛选条件", dataType = "ArticleTopicPaginationDTO")
	})
	@PostMapping("list")
    @RequiresPermissions("article-topic:list")
	public Result<ArticleTopicDTO> list(@Valid @RequestBody ArticleTopicPaginationDTO paginationDTO){
		
    	ArticleTopicEntity model =  getBeanMapper().map(paginationDTO, ArticleTopicEntity.class);
		Page<ArticleTopicEntity> pageResult = getArticleTopicService().getPagedList(model);
		List<ArticleTopicDTO> retList = Lists.newArrayList();
		for (ArticleTopicEntity keyvalueModel : pageResult.getRecords()) {
			retList.add(getBeanMapper().map(keyvalueModel, ArticleTopicDTO.class));
		}
		return new Result<ArticleTopicDTO>(pageResult, retList);
		
	}

	@ApiOperation(value = "根据分组查询文章栏目（键值对）", notes = "根据分组查询文章栏目（键值对）")
	@GetMapping("pairs")
	@RequiresAuthentication
	@ResponseBody
	public ApiRestResponse<List<PairModel>> pairs() throws Exception {
		return ApiRestResponse.success(getArticleTopicService().getPairList());
	}
 
	@ApiOperation(value = "创建文章栏目", notes = "增加一个新的文章栏目")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "body", name = "DTO", value = "文章栏目传输对象", dataType = "ArticleTopicNewDTO") 
	})
	@BusinessLog(module = Constants.ARTICLE_TOPIC, business = "创建文章栏目", opt = BusinessType.INSERT)
	@PostMapping("new")
	@RequiresPermissions("article-topic:new")
	@ResponseBody
	public ApiRestResponse<String> topic(@Valid @RequestBody ArticleTopicNewDTO DTO) throws Exception {
		
		// 检查名称是否存在
		Long ct = getArticleTopicService().getCountByName(DTO.getName(), null);
		if(ct > 0) {
			return fail("article.topic.conflict");
		}
		// 登录账号信息
		ShiroPrincipal principal = SubjectUtils.getPrincipal(ShiroPrincipal.class);

		// 新增一条数据库配置记录
		ArticleTopicEntity model = getBeanMapper().map(DTO, ArticleTopicEntity.class);
		model.setUid(principal.getUserid());
		
		boolean result = getArticleTopicService().save(model);
		if(result) {
			return success("article.topic.new.success", result);
		}
		// 逻辑代码，如果发生异常将不会被执行
		return fail("article.topic.new.fail", result);
	}
	
	@ApiOperation(value = "删除文章栏目", notes = "删除文章栏目")
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "query", name = "ids", value = "文章栏目id,多个用,拼接", required = true, dataType = "String")
	})
	@BusinessLog(module = Constants.ARTICLE_TOPIC, business = "删除文章栏目", opt = BusinessType.UPDATE)
	@GetMapping("delete")
	@RequiresPermissions("article-topic:renew")
	public ApiRestResponse<String> delete(@RequestParam String ids) throws Exception {
		// 执行文章栏目删除操作
		List<String> idList = Lists.newArrayList(StringUtils.tokenizeToStringArray(ids));
		boolean result = getArticleTopicService().removeBatchByIds(idList);
		if(result) {
			return success("article.topic.delete.success", result);
		}
		// 逻辑代码，如果发生异常将不会被执行
		return fail("article.topic.delete.fail", result);
	}
	 
	@ApiOperation(value = "更新文章栏目", notes = "更新文章栏目")
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "body", name = "DTO", value = "文章栏目", required = true, dataType = "ArticleTopicRenewDTO"),
	})
	@BusinessLog(module = Constants.ARTICLE_TOPIC, business = "更新文章栏目", opt = BusinessType.UPDATE)
	@PostMapping("renew")
	@RequiresPermissions("article-topic:renew")
	@ResponseBody
	public ApiRestResponse<String> renew(@Valid @RequestBody ArticleTopicRenewDTO DTO) throws Exception {
		
		// 检查名称是否存在
		Long ct = getArticleTopicService().getCountByName(DTO.getName(), DTO.getId());
		if(ct > 0) {
			return fail("article.topic.conflict");
		}
		
		ArticleTopicEntity model = getBeanMapper().map(DTO, ArticleTopicEntity.class);
		boolean result = getArticleTopicService().updateById(model);
		if(result) {
			return success("article.topic.renew.success", result);
		}
		// 逻辑代码，如果发生异常将不会被执行
		return fail("article.topic.renew.fail", result);
	}
	
	@ApiOperation(value = "更新文章栏目状态", notes = "更新文章栏目状态")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "id", required = true, value = "文章栏目id", dataType = "String"),
		@ApiImplicitParam(paramType = "query", name = "status", required = true, value = "文章栏目状态", dataType = "String", allowableValues = "1,0")
	})
	@BusinessLog(module = Constants.ARTICLE_TOPIC, business = "更新文章栏目状态", opt = BusinessType.UPDATE)
	@GetMapping("status")
	@RequiresPermissions("article-topic:status")
	@ResponseBody
	public ApiRestResponse<String> status(@RequestParam String id, @AllowableValues(allows = "0,1",message = "数据状态错误") @RequestParam String status) throws Exception {
		int result = getArticleTopicService().setStatus(id, status);
		if(result == 1) {
			return success("article.topic.status.success", result);
		}
		// 逻辑代码，如果发生异常将不会被执行
		return fail("article.topic.status.fail", result);
	}
	
	@ApiOperation(value = "查询文章栏目信息", notes = "根据id查询文章栏目信息")
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "query", name = "id", required = true, value = "文章栏目id", dataType = "String")
	})
	@BusinessLog(module = Constants.ARTICLE_TOPIC, business = "查询文章栏目信息", opt = BusinessType.SELECT)
	@GetMapping("detail")
	@RequiresAuthentication
	@ResponseBody
	public ApiRestResponse<ArticleTopicDTO> detail(@RequestParam("id") String id) throws Exception { 
		ArticleTopicEntity model = getArticleTopicService().getModel(id);
		if(model == null) {
			return ApiRestResponse.fail(getMessage("article.topic.get.empty"));
		}
		return ApiRestResponse.success(getBeanMapper().map(model, ArticleTopicDTO.class));
	}

    @ApiOperation(value = "文章栏目树")
    @GetMapping("tree")
    @RequiresAuthentication
    public ApiRestResponse<List<ArticleTopicTreeDTO>> tree() {
    	ArticleTopicTreeDTO topTopic = ArticleTopicTreeDTO.createRoot();
        List<ArticleTopicTreeDTO> children = getArticleTopicService().tree();
        topTopic.setChildren(children);
        return ApiRestResponse.success(Arrays.asList(topTopic));
    }
    
    public IArticleTopicService getArticleTopicService() {
		return articleTopicService;
	}

}
