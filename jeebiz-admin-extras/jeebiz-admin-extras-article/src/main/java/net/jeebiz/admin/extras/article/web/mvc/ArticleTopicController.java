package net.jeebiz.admin.extras.article.web.mvc;//


import java.util.Arrays;
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
import net.jeebiz.boot.api.ApiRestResponse;
import net.jeebiz.boot.api.annotation.AllowableValues;
import net.jeebiz.boot.api.annotation.BusinessLog;
import net.jeebiz.boot.api.annotation.BusinessType;
import net.jeebiz.boot.api.dao.entities.PairModel;
import net.jeebiz.boot.api.utils.StringUtils;
import net.jeebiz.boot.api.web.BaseApiController;
import net.jeebiz.boot.api.web.Result;
import net.jeebiz.admin.extras.article.dao.entities.ArticleTopicModel;
import net.jeebiz.admin.extras.article.service.IArticleTopicService;
import net.jeebiz.admin.extras.article.setup.Constants;
import net.jeebiz.admin.extras.article.web.vo.ArticleTopicNewVo;
import net.jeebiz.admin.extras.article.web.vo.ArticleTopicPaginationVo;
import net.jeebiz.admin.extras.article.web.vo.ArticleTopicRenewVo;
import net.jeebiz.admin.extras.article.web.vo.ArticleTopicTreeVo;
import net.jeebiz.admin.extras.article.web.vo.ArticleTopicVo;

@Api(tags = "文章栏目查询")
@RestController
@RequestMapping("/article/topic/")
@Validated
public class ArticleTopicController extends BaseApiController {

    @Autowired
    private IArticleTopicService articleTopicService;
    
    @ApiOperation(value = "分页查询文章栏目", notes = "分页查询文章栏目")
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "body", name = "paginationVo", value = "用户信息筛选条件", dataType = "ArticleTopicPaginationVo")
	})
	@PostMapping("list")
    @RequiresPermissions("article-topic:list")
	public Result<ArticleTopicVo> list(@Valid @RequestBody ArticleTopicPaginationVo paginationVo){
		
    	ArticleTopicModel model =  getBeanMapper().map(paginationVo, ArticleTopicModel.class);
		Page<ArticleTopicModel> pageResult = getArticleTopicService().getPagedList(model);
		List<ArticleTopicVo> retList = Lists.newArrayList();
		for (ArticleTopicModel keyvalueModel : pageResult.getRecords()) {
			retList.add(getBeanMapper().map(keyvalueModel, ArticleTopicVo.class));
		}
		return new Result<ArticleTopicVo>(pageResult, retList);
		
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
		@ApiImplicitParam(paramType = "body", name = "vo", value = "文章栏目传输对象", dataType = "ArticleTopicNewVo") 
	})
	@BusinessLog(module = Constants.ARTICLE_TOPIC, business = "创建文章栏目", opt = BusinessType.INSERT)
	@PostMapping("new")
	@RequiresPermissions("article-topic:new")
	@ResponseBody
	public ApiRestResponse<String> topic(@Valid @RequestBody ArticleTopicNewVo vo) throws Exception {
		
		// 检查名称是否存在
		int ct = getArticleTopicService().getCountByName(vo.getName(), null);
		if(ct > 0) {
			return fail("article.topic.conflict");
		}
		// 登录账号信息
		ShiroPrincipal principal = SubjectUtils.getPrincipal(ShiroPrincipal.class);

		// 新增一条数据库配置记录
		ArticleTopicModel model = getBeanMapper().map(vo, ArticleTopicModel.class);
		model.setUid(principal.getUserid());
		
		int result = getArticleTopicService().insert(model);
		if(result == 1) {
			return success("article.topic.new.success", result);
		}
		// 逻辑代码，如果发生异常将不会被执行
		return fail("article.topic.new.fail", result);
	}
	
	@ApiOperation(value = "删除文章栏目", notes = "删除文章栏目")
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "query", name = "ids", value = "文章栏目ID,多个用,拼接", required = true, dataType = "String")
	})
	@BusinessLog(module = Constants.ARTICLE_TOPIC, business = "删除文章栏目", opt = BusinessType.UPDATE)
	@GetMapping("delete")
	@RequiresPermissions("article-topic:renew")
	public ApiRestResponse<String> delete(@RequestParam String ids) throws Exception {
		// 执行文章栏目删除操作
		List<String> idList = Lists.newArrayList(StringUtils.tokenizeToStringArray(ids));
		int result = getArticleTopicService().batchDelete(idList);
		if(result > 0) {
			return success("article.topic.delete.success", result);
		}
		// 逻辑代码，如果发生异常将不会被执行
		return fail("article.topic.delete.fail", result);
	}
	 
	@ApiOperation(value = "更新文章栏目", notes = "更新文章栏目")
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "body", name = "vo", value = "文章栏目", required = true, dataType = "ArticleTopicRenewVo"),
	})
	@BusinessLog(module = Constants.ARTICLE_TOPIC, business = "更新文章栏目", opt = BusinessType.UPDATE)
	@PostMapping("renew")
	@RequiresPermissions("article-topic:renew")
	@ResponseBody
	public ApiRestResponse<String> renew(@Valid @RequestBody ArticleTopicRenewVo vo) throws Exception {
		
		// 检查名称是否存在
		int ct = getArticleTopicService().getCountByName(vo.getName(), vo.getId());
		if(ct > 0) {
			return fail("article.topic.conflict");
		}
		
		ArticleTopicModel model = getBeanMapper().map(vo, ArticleTopicModel.class);
		int result = getArticleTopicService().update(model);
		if(result == 1) {
			return success("article.topic.renew.success", result);
		}
		// 逻辑代码，如果发生异常将不会被执行
		return fail("article.topic.renew.fail", result);
	}
	
	@ApiOperation(value = "更新文章栏目状态", notes = "更新文章栏目状态")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "id", required = true, value = "文章栏目ID", dataType = "String"),
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
	
	@ApiOperation(value = "查询文章栏目信息", notes = "根据ID查询文章栏目信息")
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "query", name = "id", required = true, value = "文章栏目ID", dataType = "String")
	})
	@BusinessLog(module = Constants.ARTICLE_TOPIC, business = "查询文章栏目信息", opt = BusinessType.SELECT)
	@GetMapping("detail")
	@RequiresAuthentication
	@ResponseBody
	public ApiRestResponse<ArticleTopicVo> detail(@RequestParam("id") String id) throws Exception { 
		ArticleTopicModel model = getArticleTopicService().getModel(id);
		if(model == null) {
			return ApiRestResponse.empty(getMessage("article.topic.get.empty"));
		}
		return ApiRestResponse.success(getBeanMapper().map(model, ArticleTopicVo.class));
	}

    @ApiOperation(value = "文章栏目树")
    @GetMapping("tree")
    @RequiresAuthentication
    public ApiRestResponse<List<ArticleTopicTreeVo>> tree() {
    	ArticleTopicTreeVo topTopic = ArticleTopicTreeVo.createRoot();
        List<ArticleTopicTreeVo> children = getArticleTopicService().tree();
        topTopic.setChildren(children);
        return ApiRestResponse.success(Arrays.asList(topTopic));
    }
    
    public IArticleTopicService getArticleTopicService() {
		return articleTopicService;
	}

}
