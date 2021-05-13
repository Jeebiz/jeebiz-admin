package net.jeebiz.admin.extras.filestore.web.dto;

import java.io.InputStream;
import java.util.Set;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel(value = "FileDownloadDTO", description = "文件存储信息DTO")
@Data
public class FileDownloadDTO {

	/**
	 * 文件UUid
	 */
	private String uuid;
	/**
	 * 文件名称
	 */
	private String name;
	/**
	 * 文件存储路径
	 */
	private String path;
	/**
	 * 文件访问地址
	 */
	private String url;
	/**
	 * 文件类型
	 */
	private String ext;
	/**
	 * 文件元信息
	 */
	private Set<FileMetaDataDTO> metadata;
	/*
	 * 文件字节码
	 */
	private byte[] bytes;
	/**
	 * 文件流对象
	 */
	private InputStream stream;

}
