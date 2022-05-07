package io.hiwepy.admin.extras.article.web.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.hiwepy.boot.api.dto.AbstractPaginationDTO;

@ApiModel(value = "ArticleCommentPaginationDTO", description = "文章评论分页查询参数DTO")
@Data
@EqualsAndHashCode(callSuper=false)
public class ArticleCommentPaginationDTO extends AbstractPaginationDTO {
	
}
