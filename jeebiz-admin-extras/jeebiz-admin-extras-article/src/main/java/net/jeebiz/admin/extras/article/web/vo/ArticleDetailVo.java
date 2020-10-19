package net.jeebiz.admin.extras.article.web.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@ApiModel(value = "ArticleDetailVo", description = "文章详情参数Vo")
@Data
@EqualsAndHashCode(callSuper=false)
public class ArticleDetailVo extends ArticleVo {

	@ApiModelProperty(name = "author", dataType = "ArticleAuthorVo", value = "文章作者信息")
    private ArticleAuthorVo author;

}
