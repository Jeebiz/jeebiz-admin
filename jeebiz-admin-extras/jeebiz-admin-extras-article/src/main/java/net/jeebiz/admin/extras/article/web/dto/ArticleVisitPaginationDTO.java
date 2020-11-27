package net.jeebiz.admin.extras.article.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.jeebiz.boot.api.dto.AbstractPaginationDTO;

@ApiModel(value = "ArticleVisitPaginationDTO", description = "文章访问记录分页查询参数DTO")
@Data
@EqualsAndHashCode(callSuper=false)
public class ArticleVisitPaginationDTO extends AbstractPaginationDTO {

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
