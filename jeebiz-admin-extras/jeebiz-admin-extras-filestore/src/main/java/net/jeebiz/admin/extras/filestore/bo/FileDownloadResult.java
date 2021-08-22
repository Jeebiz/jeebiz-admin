package net.jeebiz.admin.extras.filestore.bo;

import java.io.InputStream;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import net.jeebiz.admin.extras.filestore.enums.FilestoreChannel;

@ApiModel(value = "FileDownloadResult", description = "文件下载结果")
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class FileDownloadResult {
	
	/**
	 * 发起文件存储的用户uid
	 */
	@ApiModelProperty(name = "userId", value = "发起文件存储的用户uid")
	private String userId;
	
	/**
	 * 文件存储渠道
	 */
	@ApiModelProperty(name = "channel", value = "文件存储渠道")
	private FilestoreChannel channel;
	
	/**
	 * 文件对象
	 */
	@ApiModelProperty(name = "file", value = "文件对象")
	private FileData file;
	
    /**
	 * 文件下载状态（ 0：下载失败、1：下载成功）
	 */
	@ApiModelProperty(name = "status", value = "文件下载送状态（ 0：下载失败、1：下载成功）")
	private Integer status;
	
	/*
	 * 文件字节码
	 */
	@ApiModelProperty(name = "bytes", value = "文件字节码")
	private byte[] bytes;
	
	/**
	 * 文件流对象
	 */
	@ApiModelProperty(name = "stream", value = "文件流对象")
	private InputStream stream;

}
