package net.jeebiz.admin.extras.filestore.web.param;

import java.util.List;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.jeebiz.admin.extras.filestore.enums.FilestoreChannel;

@ApiModel(value = "FileDeleteByPathParam", description = "文件删除DTO")
@Data
public class FileDeleteByPathParam {

	@ApiModelProperty(name = "channel", value = "存储目标")
	@NotNull(message = "存储目标不能为空") 
	private FilestoreChannel channel;
	/**
	 * 要删除文件的路径
	 */
	@ApiModelProperty(name = "paths", value = "要删除文件的路径")
	@NotNull(message = "文件路径不能为空") 
	private List<String> paths;

}
