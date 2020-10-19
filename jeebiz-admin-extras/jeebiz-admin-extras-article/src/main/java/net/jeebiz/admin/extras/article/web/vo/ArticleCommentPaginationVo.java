package net.jeebiz.admin.extras.article.web.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.jeebiz.boot.api.vo.AbstractPaginationVo;

@ApiModel(value = "ArticleCommentPaginationVo", description = "文章评论分页查询参数Vo")
@Data
@EqualsAndHashCode(callSuper=false)
public class ArticleCommentPaginationVo extends AbstractPaginationVo {
	
}
