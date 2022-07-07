package io.hiwepy.admin.extras.banner.web.dto;

import com.baomidou.mybatisplus.annotation.TableField;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 横幅配置信息DTO
 */
@ApiModel(value = "BannerDTO", description = "横幅配置信息DTO")
@Data
public class BannerDTO {

	/**
	 * 主键ID
	 */
	@ApiModelProperty(value = "主键ID")
	private Integer id;
	/**
	 * 客户端应用ID；多个客户端使用,拼接
	 */
	@ApiModelProperty(notes = "客户端应用ID；多个客户端使用,拼接")
	private String appId;
	/**
	 * 客户端应用渠道编码；多个编码使用,拼接
	 */
	@ApiModelProperty(notes = "客户端应用渠道编码；多个编码使用,拼接")
	private String appChannel;
	/**
	 * 地区编码；多个编码使用,拼接
	 */
	@ApiModelProperty(notes = "地区编码；多个编码使用,拼接")
	private String region;
	/**
	 * 语区标签；多个语区使用,拼接
	 */
	@ApiModelProperty(notes = "语区标签；多个语区使用,拼接")
	private String language;
	/**
	 * 该横幅对应的标题
	 */
	@ApiModelProperty(notes = "该横幅对应的标题")
	private String title;
	/**
	 * 该横幅对应的描述
	 */
	@ApiModelProperty(notes = "该横幅对应的描述")
	private String desc;
	/**
	 * 横幅图标路径
	 */
	@ApiModelProperty(notes = "横幅图标路径")
	private String iconUrl;
	/**
	 * 横幅图片路径
	 */
	@ApiModelProperty(notes = "横幅图片路径")
	private String imgUrl;
	/**
	 * 跳转路径
	 */
	@ApiModelProperty(notes = "跳转路径")
	private String jumpUrl;
	/**
	 * 横幅类型（0：首页轮班、1：我的页面轮播、2：搜索页面轮播）
	 */
	@ApiModelProperty(notes = "横幅类型（0：首页轮班、1：我的页面轮播、2：搜索页面轮播）")
	private Integer type;
	/**
	 * 显示状态（0：不显示、1：显示）
	 */
	@TableField(value = "status")
	@ApiModelProperty(notes = "显示状态（0：不显示、1：显示）")
	private Integer status;
	/**
	 * 链接类型（0:H5网页、1:客户端、2:仅图片、3:内联网页）
	 */
	@ApiModelProperty(notes = "链接类型（0:H5网页、1:客户端、2:仅图片、3:内联网页）")
	private Integer linkType;
	/**
	 * 间隔时间
	 */
	@ApiModelProperty(value = "间隔时间")
	private Integer intervalTime;
	/**
	 * 过期时间
	 */
	@ApiModelProperty(value = "过期时间")
	private Integer expireTime;
	/**
	 * 等待时间
	 */
	@ApiModelProperty(value = "等待时间")
	private Integer waitTime;
	/**
	 * 权重值，数字越小越靠前
	 */
	@ApiModelProperty(notes = "权重值，数字越小越靠前")
	private Integer priority;
	/**
	 * 扩展字段：过期时间、等待时间
	 */
	@ApiModelProperty(notes = "扩展字段：过期时间、等待时间")
	@JsonIgnore
	private String extend;
	/**
	 * 开放时间
	 */
	@ApiModelProperty(notes = "开放时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "GMT+8")
	private LocalDateTime openTime;
	/**
	 * 关闭时间
	 */
	@ApiModelProperty(notes = "关闭时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "GMT+8")
	private LocalDateTime closeTime;

}
