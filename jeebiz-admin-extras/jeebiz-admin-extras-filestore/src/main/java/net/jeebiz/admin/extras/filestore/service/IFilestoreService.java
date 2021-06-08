/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.filestore.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import net.jeebiz.admin.extras.filestore.dao.entities.FileEntity;
import net.jeebiz.admin.extras.filestore.web.dto.FileDTO;
import net.jeebiz.admin.extras.filestore.web.dto.FileDownloadDTO;
import net.jeebiz.admin.extras.filestore.web.dto.FilestoreConfig;
import net.jeebiz.boot.api.service.IBaseMapperService;

public interface IFilestoreService extends IBaseMapperService<FileEntity>{

	/**
	 * 文件服务配置
	 * @return
	 */
	FilestoreConfig getConfig();
	
	/**
	 * 文件存储
	 * @param file 文件
	 * @param width 缩放长度
	 * @param height 缩放高度
	 * @return
	 */
	FileDTO upload(String uid, MultipartFile file, int width, int height) throws Exception;
	
	/**
	 * 文件存储
	 * @param files 文件组
	 * @param width 缩放长度
	 * @param height 缩放高度
	 * @return
	 */
	List<FileDTO> upload(String uid, MultipartFile[] files, int width, int height) throws Exception;

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
	FileDTO reupload(String uid, String uuid,  MultipartFile file, int width, int height) throws Exception;
	
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
	
	
}
