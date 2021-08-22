package net.jeebiz.admin.extras.filestore.web.param;

import java.util.List;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.jeebiz.admin.extras.filestore.enums.FilestoreChannel;

@ApiModel(value = "FileListByUuidParam", description = "文件查询DTO")
@Data
public class FileListByUuidParam {

	@ApiModelProperty(name = "channel", value = "存储目标")
	@NotNull(message = "存储目标不能为空") 
	private FilestoreChannel channel;
	/**
	 * 要查询文件的uuid
	 */
	@ApiModelProperty(name = "uuids", value = "要查询文件的uuid")
	@NotNull(message = "文件UUid不能为空")
	private List<String> uuids;

}
