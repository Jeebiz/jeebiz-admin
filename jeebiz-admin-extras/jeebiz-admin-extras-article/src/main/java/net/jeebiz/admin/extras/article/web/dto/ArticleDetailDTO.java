package net.jeebiz.admin.extras.article.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@ApiModel(value = "ArticleDetailDTO", description = "文章详情参数DTO")
@Data
@EqualsAndHashCode(callSuper=false)
public class ArticleDetailDTO extends ArticleDTO {

	@ApiModelProperty(name = "author", dataType = "ArticleAuthorDTO", value = "文章作者信息")
    private ArticleAuthorDTO author;

}
