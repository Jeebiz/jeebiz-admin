package net.jeebiz.admin.extras.article.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.jeebiz.boot.api.dto.AbstractPaginationDTO;

@ApiModel(value = "ArticleCategoryPaginationDTO", description = "文章分类分页查询参数DTO")
@Data
@EqualsAndHashCode(callSuper=false)
public class ArticleCategoryPaginationDTO extends AbstractPaginationDTO {
    
    /**
	 * 文章分类名称
	 */
	@ApiModelProperty(name = "name", dataType = "String", value = "文章分类名称")
	private String name;
	
    /**
	 * 文章分类关键字
	 */
	@ApiModelProperty(name = "keywords", dataType = "String", value = "文章分类关键字")
	private String keywords;
	
}
