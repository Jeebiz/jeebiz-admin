/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package io.hiwepy.admin.extras.filestore.dao.entities;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.Alias;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import io.hiwepy.admin.extras.filestore.enums.FilestoreChannel;
import io.hiwepy.boot.api.dao.entities.BaseEntity;

@SuppressWarnings("serial")
@Alias("FileEntity")
@TableName(value = "sys_data_files")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class FileEntity extends BaseEntity<FileEntity> implements Comparable<FileEntity> {

	/**     
	 * 文件id
	 */
	@TableId(value="f_id",type= IdType.AUTO)
	private String id;
	/**
	 * 客户端应用ID
	 */
	@TableField(value = "app_id")
	private String appId;
	/**
	 * 客户端应用渠道
	 */
	@TableField(value = "app_channel")
	private String appChannel;
	/**
	 * 客户端版本
	 */
	@TableField(value = "app_version")
	private String appVer;
	/**
	 *请求来源IP地址
	 */
	@TableField(value = "source_ip")
	private String ipAddress;
	/**
	 * 请求来源国：根据请求IP地址解析
	 */
	@TableField(value = "source_country")
	private String country;
	/**
	 * 文件UUid
	 */
	@TableField(value = "f_uuid")
	private String uuid;
	/**
	 * 文件类型
	 */
	@TableField(value = "f_ext")
	private String ext;
	/**
	 * 文件名
	 */
	@TableField(value = "f_name")
	private String name;
	/**
	 * 文件存储目标
	 */
	@TableField(value = "f_channel")
	private FilestoreChannel channel;
	/**
	 * 文件存储分组或存储桶
	 */
	@TableField(value = "f_group")
	private String group1;
	/**
	 * 文件名
	 */
	@TableField(value = "f_path")
	private String path;
	/**
	 * 缩略图访问地址（图片类型文件）
	 */
	@TableField(value = "f_thumb")
	private String thumb;
	/**
	 * 文件所属用户id
	 */
	@TableField(value = "f_uid")
	private String userId;
	/**
	 * 文件同批次的顺序编号
	 */
	@TableField(value = "f_order")
	private String orderBy;

	@Override
	public int compareTo(FileEntity o) {
		return StringUtils.compare(this.getUuid().concat(this.getOrderBy()), o.getUuid().concat(o.getOrderBy()));
	}
	 
}