package net.jeebiz.admin.extras.article.web.mvc;//


import java.util.List;

import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import net.jeebiz.boot.api.web.BaseApiController;
import net.jeebiz.boot.api.web.Result;
import net.jeebiz.admin.extras.article.dao.entities.ArticleVisitModel;
import net.jeebiz.admin.extras.article.service.IArticleVisitService;
import net.jeebiz.admin.extras.article.web.vo.ArticleVisitPaginationVo;
import net.jeebiz.admin.extras.article.web.vo.ArticleVisitVo;

@Api(tags = "文章访问：")
@RestController
@RequestMapping("/article/visit/")
@Validated
public class ArticleVisitController extends BaseApiController {

    @Autowired
    private IArticleVisitService articleVisitService;
    
    @ApiOperation(value = "分页查询访问记录", notes = "分页查询访问记录")
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "body", name = "paginationVo", value = "用户信息筛选条件", dataType = "ArticleVisitPaginationVo")
	})
	@PostMapping("list")
    @RequiresPermissions("article-topic:list")
	public Result<ArticleVisitVo> list(@Valid @RequestBody ArticleVisitPaginationVo paginationVo){
		
    	ArticleVisitModel model =  getBeanMapper().map(paginationVo, ArticleVisitModel.class);
		Page<ArticleVisitModel> pageResult = getArticleVisitService().getPagedList(model);
		List<ArticleVisitVo> retList = Lists.newArrayList();
		for (ArticleVisitModel keyvalueModel : pageResult.getRecords()) {
			retList.add(getBeanMapper().map(keyvalueModel, ArticleVisitVo.class));
		}
		return new Result<ArticleVisitVo>(pageResult, retList);
		
	}
    
    public IArticleVisitService getArticleVisitService() {
		return articleVisitService;
	}

}
