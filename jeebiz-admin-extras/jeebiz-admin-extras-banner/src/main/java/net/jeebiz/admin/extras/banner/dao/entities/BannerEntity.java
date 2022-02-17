package net.jeebiz.admin.extras.banner.dao.entities;

import org.apache.ibatis.type.Alias;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.jeebiz.boot.api.dao.entities.PaginationEntity;

@Alias("BannerEntity")
@SuppressWarnings("serial")
@TableName(value = "sys_data_myapp")
@Data
@EqualsAndHashCode(callSuper = false)
public class BannerEntity extends PaginationEntity<BannerEntity> {

	/**
	 * 数据源ID
	 */
	@TableId(value="app_id",type= IdType.AUTO)
	private String id;
	/** 应用UID */
	@TableField(value = "app_uid")
	private String uid;
	/** 应用名称 */
	@TableField(value = "app_name")
	private String name;
	/** 应用描述 */
	@TableField(value = "app_intro")
	private String intro;
	/** 应用开发语言 */
	@TableField(value = "app_lang")
	private String lang;
	/** 应用部署地址 */
	@TableField(value = "app_addr")
	private String addr;
	/** 应用Key:RSA公钥 */
	@TableField(value = "app_key")
	private String appKey;
	/** 应用Secret:RSA私钥 */
	@TableField(value = "app_secret")
	private String appSecret;
	/** 应用所属人ID */
	@TableField(value = "app_userid")
	private String userId;

}
