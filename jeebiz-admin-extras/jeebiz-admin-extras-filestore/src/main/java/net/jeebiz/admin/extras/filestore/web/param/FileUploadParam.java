package net.jeebiz.admin.extras.filestore.web.param;

import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.jeebiz.admin.extras.filestore.enums.FilestoreChannel;

@ApiModel(value = "FileUploadParam", description = "文件上传参数")
@Data
public class FileUploadParam {

	@ApiModelProperty(name = "channel", value = "存储目标")
	@NotNull(message = "存储目标不能为空") 
	private FilestoreChannel channel;
	/**
	 * 文件对象
	 */
	@ApiModelProperty(name = "file", required = true, value = "文件对象")
	@NotNull(message = "文件不能为空")
	private MultipartFile file;
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
