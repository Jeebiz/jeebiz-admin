package net.jeebiz.admin.extras.filestore.bo;

import java.util.Set;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "FileData", description = "文件存储信息")
@Data
public class FileData {

	@ApiModelProperty(name = "uuid", value = "文件uuid")
	private String uuid;

	@ApiModelProperty(name = "name", value = "文件名称")
	private String name;

	@ApiModelProperty(name = "path", value = "文件存储路径")
	private String path;

	@ApiModelProperty(name = "thumb", value = "缩略图存储路径（图片类型文件）")
	private String thumb;

	@ApiModelProperty(name = "url", value = "文件访问地址")
	private String url;

	@ApiModelProperty(name = "thumbUrl", value = "缩略图访问地址（图片类型文件）")
	private String thumbUrl;

	@ApiModelProperty(name = "ext", value = "文件类型")
	private String ext;

	@ApiModelProperty(name = "metadata", value = "文件元信息")
	private Set<FileMetaData> metadata;

}
