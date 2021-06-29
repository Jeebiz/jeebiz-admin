package net.jeebiz.admin.extras.article.web.dto;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "ArticleCategoryStatusRenewDTO", description = "文章分类状态编辑DTO")
@Data
public class ArticleCategoryStatusRenewDTO {

    /**
	 * 文章分类id
	 */
	@ApiModelProperty(name = "id", required = true, dataType = "String", value = "文章分类id")
	@NotBlank(message = "文章分类id不能为空")
	private String id;
	/**
	 * 文章分类状态（0:禁用|1:可用）
	 */
	@ApiModelProperty(name = "status", dataType = "String", value = "文章分类状态（0:禁用|1:可用）")
	private String status;
    
}
