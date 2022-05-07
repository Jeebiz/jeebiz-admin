package io.hiwepy.admin.extras.article.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.hiwepy.boot.api.dto.AbstractPaginationDTO;

@ApiModel(value = "ArticleTopicPaginationDTO", description = "文章栏目分页查询参数DTO")
@Data
@EqualsAndHashCode(callSuper=false)
public class ArticleTopicPaginationDTO extends AbstractPaginationDTO {
    
    /**
	 * 文章栏目名称
	 */
	@ApiModelProperty(name = "name", dataType = "String", value = "文章栏目名称")
	private String name;
	
    /**
	 * 文章栏目关键字
	 */
	@ApiModelProperty(name = "keywords", dataType = "String", value = "文章栏目关键字")
	private String keywords;
	
}
