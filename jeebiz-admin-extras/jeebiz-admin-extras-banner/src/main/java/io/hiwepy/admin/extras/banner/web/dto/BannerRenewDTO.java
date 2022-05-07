package io.hiwepy.admin.extras.banner.web.dto;

import com.baomidou.mybatisplus.annotation.TableField;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 横幅配置更新DTO
 */
@ApiModel(value = "BannerRenewDTO", description = "横幅配置更新DTO")
@Data
public class BannerRenewDTO {

	/**
	 * 主键ID
	 */
	@ApiModelProperty(value = "主键ID")
	private Integer id;
	/**
	 * 客户端应用ID；多个客户端使用,拼接
	 */
	@ApiModelProperty(required = true, notes = "客户端应用ID；多个客户端使用,拼接")
	private String appId;
	/**
	 * 客户端应用渠道编码；多个编码使用,拼接
	 */
	@ApiModelProperty(required = true, notes = "客户端应用渠道编码；多个编码使用,拼接")
	private String appChannel;
	/**
	 * 地区编码；多个编码使用,拼接
	 */
	@ApiModelProperty(required = true, notes = "地区编码；多个编码使用,拼接")
	private String region;
	/**
	 * 语区标签；多个语区使用,拼接
	 */
	@ApiModelProperty(required = true, notes = "语区标签；多个语区使用,拼接")
	private String language;
	/**
	 * 该横幅对应的标题
	 */
	@ApiModelProperty(required = true, notes = "该横幅对应的标题")
	private String title;
	/**
	 * 该横幅对应的描述
	 */
	@ApiModelProperty(required = true, notes = "该横幅对应的描述")
	private String desc;
	/**
	 * 横幅图标路径
	 */
	@ApiModelProperty(required = true, notes = "横幅图标路径")
	private String iconUrl;
	/**
	 * 横幅图片路径
	 */
	@ApiModelProperty(required = true, notes = "横幅图片路径")
	private String imgUrl;
	/**
	 * 跳转路径
	 */
	@ApiModelProperty(required = true, notes = "跳转路径")
	private String jumpUrl;
	/**
	 * 横幅类型（0：首页轮班、1：我的页面轮播、2：搜索页面轮播）
	 */
	@ApiModelProperty(required = true, notes = "横幅类型（0：首页轮班、1：我的页面轮播、2：搜索页面轮播）")
	private Integer type;
	/**
	 * 显示状态（0：不显示、1：显示）
	 */
	@TableField(value = "status")
	@ApiModelProperty(required = true, notes = "显示状态（0：不显示、1：显示）")
	private Integer status;
	/**
	 * 链接类型（0:H5网页、1:客户端、2:仅图片、3:内联网页）
	 */
	@ApiModelProperty(required = true, notes = "链接类型（0:H5网页、1:客户端、2:仅图片、3:内联网页）")
	private Integer linkType;
	/**
	 * 扩展字段：过期时间、等待时间
	 */
	@ApiModelProperty(notes = "扩展字段：过期时间、等待时间")
	private String extend;
	/**
	 * 权重值，数字越小越靠前
	 */
	@ApiModelProperty(required = true, notes = "权重值，数字越小越靠前")
	private Integer priority;
}
