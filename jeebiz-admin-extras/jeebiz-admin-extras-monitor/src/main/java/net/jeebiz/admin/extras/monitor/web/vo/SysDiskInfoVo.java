package net.jeebiz.admin.extras.monitor.web.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 系统磁盘相关信息
 */
@ApiModel(value = "SysDiskInfoVo", description = "系统磁盘相关信息")
@Data
public class SysDiskInfoVo {

	/**
	 * 盘符路径
	 */
	@ApiModelProperty(name = "mount", value = "盘符路径")
	private String mount;

	/**
	 * 盘符类型
	 */
	@ApiModelProperty(name = "name", value = "盘符类型")
	private String name;

	/**
	 * 文件类型
	 */
	@ApiModelProperty(name = "type", value = "文件类型")
	private String type;

	/**
	 * 磁盘总大小
	 */
	@ApiModelProperty(name = "total", value = "磁盘总大小")
	private String total;

	/**
	 * 已用磁盘大小
	 */
	@ApiModelProperty(name = "used", value = "已用磁盘大小")
	private String used;

	/**
	 * 剩余磁盘大小
	 */
	@ApiModelProperty(name = "free", value = "剩余磁盘大小")
	private String free;

	/**
	 * 磁盘使用率
	 */
	@ApiModelProperty(name = "usage", value = "磁盘使用率")
	private double usage;

}
