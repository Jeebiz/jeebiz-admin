/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.filestore.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import net.jeebiz.admin.extras.filestore.dao.entities.FilestoreModel;
import net.jeebiz.admin.extras.filestore.web.vo.FilestoreConfig;
import net.jeebiz.admin.extras.filestore.web.vo.FilestoreDownloadVo;
import net.jeebiz.admin.extras.filestore.web.vo.FilestoreVo;
import net.jeebiz.boot.api.service.IBaseService;

public interface IFilestoreService extends IBaseService<FilestoreModel>{

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
	FilestoreVo upload(MultipartFile file, int width, int height) throws Exception;
	
	/**
	 * 文件存储
	 * @param files 文件组
	 * @param width 缩放长度
	 * @param height 缩放高度
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
	FilestoreVo reupload(String uuid,  MultipartFile file, int width, int height) throws Exception;
	
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
	
	
}
