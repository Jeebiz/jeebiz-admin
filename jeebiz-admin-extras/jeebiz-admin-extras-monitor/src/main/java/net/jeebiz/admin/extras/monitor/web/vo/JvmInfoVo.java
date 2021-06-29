package net.jeebiz.admin.extras.monitor.web.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * JVM相关信息
 */
@ApiModel(value = "JvmInfoVo", description = "JVM相关信息")
@Data
public class JvmInfoVo {

	/**
	 * JDK名称
	 */
	@ApiModelProperty(name = "name", value = "JDK名称")
	private String name;

	/**
	 * JDK版本
	 */
	@ApiModelProperty(name = "version", value = "JDK版本")
	private String version;

	/**
	 * JDK路径
	 */
	@ApiModelProperty(name = "home", value = "JDK路径")
	private String home;
	
	/**
	 * 当前JVM占用的内存总数(M)
	 */
	@ApiModelProperty(name = "total", value = "当前JVM占用的内存总数(M)")
	private double total;

	/**
	 * JVM最大可用内存总数(M)
	 */
	@ApiModelProperty(name = "max", value = "JVM最大可用内存总数(M)")
	private double max;

	/**
	 * JVM空闲内存(M)
	 */
	@ApiModelProperty(name = "free", value = "JVM空闲内存(M)")
	private double free;

	/**
	 * JVM已用内存
	 */
	@ApiModelProperty(name = "used", value = "JVM已用内存")
	private double used;
	
	/**
	 * JVM使用率
	 */
	@ApiModelProperty(name = "usage", value = "JVM使用率")
	private double usage;
	
	/**
	 * JDK启动时间
	 */
	@ApiModelProperty(name = "startTime", value = "JDK启动时间")
	private String startTime;

	/**
	 * JDK运行时间
	 */
	@ApiModelProperty(name = "runTime", value = "JDK运行时间")
	private String runTime;

}
