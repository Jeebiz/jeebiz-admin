package net.jeebiz.admin.authz.rbac0.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@ApiModel(value = "FilesUploadBO", description = "多文件上传BO")
@Data
@EqualsAndHashCode(callSuper=false)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class UsernamePasswordAuthBO extends AuthBO {

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
