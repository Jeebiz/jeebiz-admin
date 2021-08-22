package net.jeebiz.admin.extras.filestore.web.param;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.jeebiz.admin.extras.filestore.enums.FilestoreChannel;

@ApiModel(value = "FileDownloadByPathParam", description = "文件下载DTO")
@Data
public class FileDownloadByPathParam {

	@ApiModelProperty(name = "channel", value = "存储目标")
	@NotNull(message = "存储目标不能为空") 
	private FilestoreChannel channel;
	/**
	 * 要下载文件的路径
	 */
	@ApiModelProperty(name = "path", value = "要下载文件的路径")
	@NotNull(message = "文件路径不能为空") 
	private String path;

}
