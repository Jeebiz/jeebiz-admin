package io.hiwepy.admin.extras.inform.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value = "InformTemplatePairDTO", description = "消息通知模板对象DTO")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InformTemplatePairDTO {

	/**
	 * 消息通知模板ID
	 */
	@ApiModelProperty(value = "消息通知模板ID")
	private String id;
	/**
	 * 消息通知模板名称
	 */
	@ApiModelProperty(value = "消息模板名称")
	private String name;

}
