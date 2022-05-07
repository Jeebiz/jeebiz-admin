package io.hiwepy.admin.extras.filestore.web.dto;

import java.io.InputStream;
import java.util.Set;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import io.hiwepy.admin.extras.filestore.bo.FileMetaData;

@ApiModel(value = "FilestoreDownloadDTO", description = "文件存储信息DTO")
@Data
public class FilestoreDownloadDTO {

	/**
	 * 文件UUID
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
	private Set<FileMetaData> metadata;
	/*
	 * 文件字节码
	 */
	private byte[] bytes;
	/**
	 * 文件流对象
	 */
	private InputStream stream;

}
