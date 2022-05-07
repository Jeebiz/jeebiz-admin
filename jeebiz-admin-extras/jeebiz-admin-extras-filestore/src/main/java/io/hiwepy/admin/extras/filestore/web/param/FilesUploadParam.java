package io.hiwepy.admin.extras.filestore.web.param;

import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import io.hiwepy.admin.extras.filestore.enums.FilestoreChannel;

@ApiModel(value = "FileUploadParam", description = "文件上传参数")
@Data
public class FilesUploadParam {

	@ApiModelProperty(name = "channel", value = "存储目标")
	@NotNull(message = "存储目标不能为空") 
	private FilestoreChannel channel;
	/**
	 * 文件对象数组
	 */
	@ApiModelProperty(name = "files", required = true, value = "文件对象数组")
	@NotNull(message = "文件不能为空")
	private MultipartFile[] files;
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
