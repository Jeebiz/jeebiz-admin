package io.hiwepy.admin.extras.article.web.mvc;//


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
import io.hiwepy.admin.extras.article.dao.entities.ArticleVisitEntity;
import io.hiwepy.admin.extras.article.service.IArticleVisitService;
import io.hiwepy.admin.extras.article.web.dto.ArticleVisitDTO;
import io.hiwepy.admin.extras.article.web.dto.ArticleVisitPaginationDTO;
import io.hiwepy.boot.api.web.BaseApiController;
import io.hiwepy.boot.api.web.Result;

@Api(tags = "文章访问：")
@RestController
@RequestMapping("/article/visit/")
@Validated
public class ArticleVisitController extends BaseApiController {

    @Autowired
    private IArticleVisitService articleVisitService;
    
    @ApiOperation(value = "分页查询访问记录", notes = "分页查询访问记录")
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "body", name = "paginationDTO", value = "用户信息筛选条件", dataType = "ArticleVisitPaginationDTO")
	})
	@PostMapping("list")
    @RequiresPermissions("article-topic:list")
	public Result<ArticleVisitDTO> list(@Valid @RequestBody ArticleVisitPaginationDTO paginationDTO){
		
    	ArticleVisitEntity model =  getBeanMapper().map(paginationDTO, ArticleVisitEntity.class);
		Page<ArticleVisitEntity> pageResult = getArticleVisitService().getPagedList(model);
		List<ArticleVisitDTO> retList = Lists.newArrayList();
		for (ArticleVisitEntity keyvalueModel : pageResult.getRecords()) {
			retList.add(getBeanMapper().map(keyvalueModel, ArticleVisitDTO.class));
		}
		return new Result<ArticleVisitDTO>(pageResult, retList);
		
	}
    
    public IArticleVisitService getArticleVisitService() {
		return articleVisitService;
	}

}
