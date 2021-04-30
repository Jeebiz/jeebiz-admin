/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.device.dao.entities;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@Alias(value = "DeviceBindModel")
@TableName(value = "KDING_DEVICE_USERS")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class DeviceBindModel extends Model<DeviceBindModel> implements Cloneable {

    /**
	 * 主键，自增
	 */
	@TableId(value="id",type= IdType.AUTO)
	private Long id;
	/**
	 * 应用id
	 */
	@TableField(value = "APP_id")
	private String appId;
	/**
	 * 应用渠道编码
	 */
	@TableField(value = "APP_CHANNEL")
	private String appChannel;
	/**
	 *应用版本号
	 */
	@TableField(value = "APP_VERSION")
	private String appVersion;
	/**
	 *设备激活记录id
	 */
	@TableField(value = "ACTIVATEd_id")
    private Long activatedId;
	
	/**
	 * 是否删除 0未删除 1已删除
	 */
	@TableField(value = "IS_DELETE")
	private Integer isDelete;
	/**
	 * 创建人id
	 */
	@TableField(value = "CREATOR")
	private Long creator;
	/**
	 * 更新人id
	 */
	@TableField(value = "MODIFYER")
	private Long modifyer;
	/**
	 * 创建时间
	 */
	@TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
	private Date createTime;
	/**
	 * 更新时间
	 */
	@TableField(value = "MODIFY_TIME", fill = FieldFill.INSERT_UPDATE)
	private Date modifyTime;
    
}