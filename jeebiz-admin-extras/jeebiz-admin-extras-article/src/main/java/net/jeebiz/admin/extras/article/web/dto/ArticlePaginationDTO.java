package net.jeebiz.admin.extras.article.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.jeebiz.boot.api.dto.AbstractPaginationDTO;

@ApiModel(value = "ArticlePaginationDTO", description = "文章分页查询参数DTO")
@Data
@EqualsAndHashCode(callSuper=false)
public class ArticlePaginationDTO extends AbstractPaginationDTO {
	
	/**
	 * 文章模糊查询关键字
	 */
	@ApiModelProperty(name = "keywords", dataType = "String", value = "文章模糊查询关键字")
	private String keywords;
	/**
	 * 文章栏目ID
	 */
	@ApiModelProperty(name = "tid", dataType = "String", value = "文章栏目ID")
	private String tid;
	/**
	 * 文章分类ID
	 */
	@ApiModelProperty(name = "cid", dataType = "String", value = "文章分类ID")
	private String cid;
	/**
	 * 文章所属单位ID/编号
	 */
	@ApiModelProperty(name = "orgId", dataType = "String", value = "文章所属单位ID/编号")
	private String orgId;
	/**
	 * 文章审核状态（0:未提交|1:待审核|2:审核通过|2:审核不通过）
	 */
	@ApiModelProperty(name = "review", dataType = "Integer", value = "文章审核状态（0:未提交|1:待审核|2:审核通过|2:审核不通过）")
	private String review;
	/**
	 * 文章状态（0:禁用|1:可用）
	 */
	@ApiModelProperty(name = "status", dataType = "Integer", value = "文章状态（0:禁用|1:可用）")
	private Integer status;
	/**
	 * 文章是否推荐（0:未推荐|1:推荐）
	 */
	@ApiModelProperty(name = "recommend", dataType = "Integer", value = "文章是否推荐（0:未推荐|1:推荐）")
	private Integer recommend;
	
	
}
