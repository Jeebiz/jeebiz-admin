package io.hiwepy.admin.extras.filestore.web.param;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import io.hiwepy.admin.extras.filestore.enums.FilestoreChannel;

@ApiModel(value = "FileDeleteParam", description = "文件删除DTO")
@Data
public class FileDeleteParam {

	@ApiModelProperty(name = "channel", value = "存储目标")
	@NotNull(message = "存储目标不能为空") 
	private FilestoreChannel channel;
	/**
	 * 要删除文件的uuid
	 */
	@ApiModelProperty(name = "uuid", value = "要删除文件的uuid")
	@NotNull(message = "文件UUid不能为空")
	private String uuid;

}
