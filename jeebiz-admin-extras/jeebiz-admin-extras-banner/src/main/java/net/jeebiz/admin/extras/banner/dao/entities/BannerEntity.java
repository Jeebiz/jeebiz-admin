package net.jeebiz.admin.extras.banner.dao.entities;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import org.apache.ibatis.type.Alias;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.jeebiz.boot.api.dao.entities.PaginationEntity;

import java.time.LocalDateTime;

/**
 * @author wandl
 */
@Alias("BannerEntity")
@SuppressWarnings("serial")
@TableName(value = "sys_data_banner")
@Data
@EqualsAndHashCode(callSuper = false)
public class BannerEntity extends PaginationEntity<BannerEntity> {

	/**
	 * 主键ID
	 */
	@TableId(value="id",type= IdType.AUTO)
	private String id;
	/**
	 * 客户端应用ID；多个客户端使用,拼接
	 */
	@TableField(value = "app_id")
	private String appId;
	/**
	 * 客户端应用渠道编码；多个编码使用,拼接
	 */
	@TableField(value = "app_channel")
	private String appChannel;
	/**
	 * 地区编码；多个编码使用,拼接
	 */
	@TableField(value = "region_code")
	private String region;
	/**
	 * 语区标签；多个语区使用,拼接
	 */
	@TableField(value = "language")
	private String language;
	/**
	 * 该横幅对应的标题
	 */
	@TableField(value = "title")
	private String title;
	/**
	 * 该横幅对应的描述
	 */
	@TableField(value = "desc")
	private String desc;
	/**
	 * 横幅图标路径
	 */
	@TableField(value = "icon_url")
	private String iconUrl;
	/**
	 * 横幅图片路径
	 */
	@TableField(value = "img_url")
	private String imgUrl;
	/**
	 * 跳转路径
	 */
	@TableField(value = "jump_url")
	private String jumpUrl;
	/**
	 * 横幅类型（0：首页轮班、1：我的页面轮播、2：搜索页面轮播）
	 */
	@TableField(value = "type")
	private Integer type;
	/**
	 * 显示状态（0：不显示、1：显示）
	 */
	@TableField(value = "status")
	private Integer status;
	/**
	 * 链接类型（0:H5网页、1:客户端、2:仅图片、3:内联网页）
	 */
	@TableField(value = "link_type")
	private Integer linkType;
	/**
	 * '扩展字段：过期时间、等待时间'
	 */
	@TableField(value = "extend")
	private String extend;
	/**
	 * 权重值，数字越小越靠前
	 */
	@TableField(value = "priority")
	private Integer priority;
	/**
	 * 开放时间
	 */
	@TableField(value = "open_time")
	private LocalDateTime openTime;
	/**
	 * 关闭时间
	 */
	@TableField(value = "close_time")
	private LocalDateTime closeTime;

}
