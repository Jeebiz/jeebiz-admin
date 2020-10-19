package net.jeebiz.admin.extras.article.web.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.jeebiz.boot.api.vo.AbstractPaginationVo;

@ApiModel(value = "ArticleTopicPaginationVo", description = "文章栏目分页查询参数Vo")
@Data
@EqualsAndHashCode(callSuper=false)
public class ArticleTopicPaginationVo extends AbstractPaginationVo {
    
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
