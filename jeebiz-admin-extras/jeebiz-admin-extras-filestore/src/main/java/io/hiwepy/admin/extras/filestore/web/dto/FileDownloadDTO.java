package io.hiwepy.admin.extras.filestore.web.dto;

import java.util.Set;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import io.hiwepy.admin.extras.filestore.bo.FileMetaData;

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
	private Set<FileMetaData> metadata;

}
