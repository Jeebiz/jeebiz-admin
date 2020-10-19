/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.filestore.setup.provider;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import net.jeebiz.admin.extras.filestore.web.vo.FilestoreConfig;
import net.jeebiz.admin.extras.filestore.web.vo.FilestoreDownloadVo;
import net.jeebiz.admin.extras.filestore.web.vo.FilestoreVo;

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
	 * @param file 文件
	 * @return
	 */
	FilestoreVo upload(MultipartFile file, int width, int height) throws Exception;
	
	/**
	 * 存储接口
	 * @param to
	 * @param files
	 * @return
	 */
	List<FilestoreVo> upload(MultipartFile[] files, int width, int height) throws Exception;
	
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
	 * @param uuid 	原文件UUID
	 * @param file	文件
	 * @return
	 */
	FilestoreVo reupload(String uuid, MultipartFile file, int width, int height) throws Exception;
	
	/**
	 * 根据给出的文件相对路径下载文件
	 * @param path	要下载的文件path
	 * @return
	 */
	FilestoreDownloadVo downloadByPath(String path) throws Exception;

	/**
	 * 根据给出的文件Uuid下载文件
	 * @param uuid	要下载的文件Uuid
	 * @return
	 */
	FilestoreDownloadVo downloadByUuid(String uuid) throws Exception;
	
	/**
	  *   根据给出的文件相对路径获取文件信息
	 * @param paths
	 * @return
	 */
	List<FilestoreVo> listByPath(List<String> paths) throws Exception;

	/**
	  *   根据给出的文件相对路径获取文件信息
	 * @param uuids
	 * @return
	 */
	List<FilestoreVo> listByUuid(List<String> uuids) throws Exception;
	
}
