package net.jeebiz.admin.extras.filestore.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@ApiModel(value = "LocalFileUploadBO", description = "文件上传BO")
@Data
@EqualsAndHashCode(callSuper=false)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class LocalFileUploadBO extends FileUploadBO {
	
	/**
	 * 缩放长度
	 */
	@ApiModelProperty(name = "width", value = "缩放长度")
	private Integer width;
	/**
	 * 缩放高度
	 */
	@ApiModelProperty(name = "height", value = "缩放高度")
	private Integer height;

}
