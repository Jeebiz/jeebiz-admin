package net.jeebiz.admin.extras.filestore.bo;

import java.util.Set;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import net.jeebiz.admin.extras.filestore.enums.FilestoreChannel;
import net.jeebiz.admin.extras.filestore.web.dto.FileMetaDataDTO;

@ApiModel(value = "SendSmsResult", description = "支付结果")
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class FileUploadResult {
	
	/**
	 * 发起文件存储的用户uid
	 */
	@ApiModelProperty(name = "userId", required = true, value = "发起文件存储的用户uid")
	private String userId;
	/**
	 * 文件存储渠道
	 */
	@ApiModelProperty(name = "channel", required = true, value = "文件存储渠道")
	private FilestoreChannel channel;
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
    /**
	 * 文件上传状态（ 0：上传失败、1：上传成功）
	 */
	@ApiModelProperty(name = "status", value = "文件上传送状态（ 0：上传失败、1：上传成功）")
	private Integer status;
	 
}

