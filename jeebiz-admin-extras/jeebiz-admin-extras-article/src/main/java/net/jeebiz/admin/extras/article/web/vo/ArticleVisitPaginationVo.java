package net.jeebiz.admin.extras.article.web.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.jeebiz.boot.api.vo.AbstractPaginationVo;

@ApiModel(value = "ArticleVisitPaginationVo", description = "文章访问记录分页查询参数Vo")
@Data
@EqualsAndHashCode(callSuper=false)
public class ArticleVisitPaginationVo extends AbstractPaginationVo {

	/**
	 * 文章ID
	 */
	@ApiModelProperty(name = "cid", dataType = "String", value = "文章ID")
	private String cid;
	/**
	 * 文章访问者ID
	 */
	@ApiModelProperty(name = "uid", dataType = "String", value = "文章访问者ID")
	private String uid;
    
}
