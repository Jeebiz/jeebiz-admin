/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.filestore.dao.entities;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.jeebiz.boot.api.dao.entities.BaseModel;

@SuppressWarnings("serial")
@Alias("FilestoreModel")
@Getter
@Setter
@ToString
public class FilestoreModel extends BaseModel<FilestoreModel> implements Comparable<FilestoreModel> {
	
	private int order;
	
	/**
	 * 文件ID
	 */
	private String id;
	/**
	 * 文件UUID
	 */
	private String uuid;
	/**
	 * 文件类型
	 */
	private String ext;
	/**
	 * 文件名
	 */
	private String name;
	/**
	 * 文件存储目标：local:服务本地,fdfs:FastDFS存储服务,oss:阿里云对象存储
	 */
	private String to;
	/**
	 * 文件存储分组或存储桶
	 */
	private String group = "group1";
	/**
	 * 文件名
	 */
	private String path;
	/*
	 * 缩略图访问地址（图片类型文件）
	 */
	private String thumb;
	/**
	 * 文件所属用户ID
	 */
	private String uid;

	@Override
	public int compareTo(FilestoreModel o) {
		return Integer.compare(this.getOrder(), o.getOrder());
	}
	 
}