package net.jeebiz.admin.extras.filestore.web.dto;

import java.util.Set;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "FileDTO", description = "文件存储信息DTO")
@Data
public class FileDTO {
	/**
	 * 文件名称
	 */
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
    private Set<FileMetaDataDTO> metadata;

}

