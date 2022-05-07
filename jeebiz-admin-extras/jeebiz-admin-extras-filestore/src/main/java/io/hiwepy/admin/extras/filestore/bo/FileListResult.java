package io.hiwepy.admin.extras.filestore.bo;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import io.hiwepy.admin.extras.filestore.enums.FilestoreChannel;

@ApiModel(value = "FilesInfoResult", description = "多文件信息")
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class FileListResult {
	
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
	 * 文件对象数组
	 */
	@ApiModelProperty(name = "files", required = true, value = "文件对象数组")
	private List<FileData> files;
	
    /**
	 * 文件上传状态（ 0：上传失败、1：上传成功）
	 */
	@ApiModelProperty(name = "status", value = "文件上传送状态（ 0：上传失败、1：上传成功）")
	private Integer status;
	 
}

