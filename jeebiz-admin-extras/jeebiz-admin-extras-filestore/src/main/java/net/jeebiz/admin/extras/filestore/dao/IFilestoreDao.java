/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.filestore.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import net.jeebiz.admin.extras.filestore.dao.entities.FilestoreModel;
import net.jeebiz.boot.api.dao.BaseDao;

@Mapper
public interface IFilestoreDao extends BaseDao<FilestoreModel>{

	/**
	 * 批量查询文件记录
	 * @param paths
	 * @return
	 */
	List<FilestoreModel> getPaths(@Param("paths") List<String> paths);
	
	/**
	 * 批量查询文件记录
	 * @param uuids
	 * @return
	 */
	List<FilestoreModel> getFiles(@Param("uuids") List<String> uuids);

	/**
	 * 单个查询文件记录
	 * @param path
	 * @return
	 */
	FilestoreModel getByPath(@Param("path") String path);
	
	/**
	 * 单个查询文件记录
	 * @param uuid
	 * @return
	 */
	FilestoreModel getByUuid(@Param("uuid") String uuid);
	
	/**
	 * 批量删除文件记录
	 * @param uuids
	 * @return
	 */
	int deleteByUuids(@Param("uuids") List<String> uuids);
	
	/**
	 * 批量删除文件记录
	 * @param uuids
	 * @return
	 */
	int deleteByPaths(@Param("paths") List<String> paths);
	
}
