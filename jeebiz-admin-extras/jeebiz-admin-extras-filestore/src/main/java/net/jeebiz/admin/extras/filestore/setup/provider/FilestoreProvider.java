/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.filestore.setup.provider;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import net.jeebiz.admin.extras.filestore.web.dto.FilestoreConfig;
import net.jeebiz.admin.extras.filestore.web.dto.FileDTO;
import net.jeebiz.admin.extras.filestore.web.dto.FileDownloadDTO;

public interface FilestoreProvider {

	/**
	 * Filestore Provider
	 * @return
	 */
	FilestoreEnum getProvider();
	
	/**
	 * Filestore Config
	 * @return
	 */
	FilestoreConfig getConfig();
	
	/**
	 * 文件存储
	 * @param bucketName bucket 
	 * @param file 文件
	 * @return
	 */
	FileDTO upload(MultipartFile file, int width, int height) throws Exception;
	
	/**
	 * 存储接口
	 * @param bucketName bucket 
	 * @param files
	 * @return
	 */
	List<FileDTO> upload(MultipartFile[] files, int width, int height) throws Exception;
	
	/**
	 * 删除文件
	 * @param paths 要删除文件路径
	 * @return
	 */
	boolean deleteByPath(List<String> paths) throws Exception;

	/**
	 * 删除文件
	 * @param uuids 要删除文件信息
	 * @return
	 */
	boolean deleteByUuid(List<String> uuids) throws Exception;
	
	/**
	 * 重新存储指定文件
	 * @param uuid 	原文件UUid
	 * @param file	文件
	 * @return
	 */
	FileDTO reupload(String uuid, MultipartFile file, int width, int height) throws Exception;
	
	/**
	 * 根据给出的文件相对路径下载文件
	 * @param path	要下载的文件path
	 * @return
	 */
	FileDownloadDTO downloadByPath(String path) throws Exception;

	/**
	 * 根据给出的文件Uuid下载文件
	 * @param uuid	要下载的文件Uuid
	 * @return
	 */
	FileDownloadDTO downloadByUuid(String uuid) throws Exception;
	
	/**
	  *   根据给出的文件相对路径获取文件信息
	 * @param paths
	 * @return
	 */
	List<FileDTO> listByPath(List<String> paths) throws Exception;

	/**
	  *   根据给出的文件相对路径获取文件信息
	 * @param uuids
	 * @return
	 */
	List<FileDTO> listByUuid(List<String> uuids) throws Exception;
	
}
