package net.jeebiz.admin.extras.filestore.web.param;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.jeebiz.admin.extras.filestore.enums.FilestoreChannel;

@ApiModel(value = "FileDownloadByUuidParam", description = "文件下载DTO")
@Data
public class FileDownloadByUuidParam {

	@ApiModelProperty(name = "channel", value = "存储目标")
	@NotNull(message = "存储目标不能为空") 
	private FilestoreChannel channel;
	/**
	 * 要下载文件的uuid
	 */
	@ApiModelProperty(name = "uuid", value = "要下载文件的uuid")
	@NotNull(message = "文件UUid不能为空")
	private String uuid;

}
