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
    @ApiModelProperty(name = "dirName", value = "盘符路径")
    private String dirName;

    /**
     * 盘符类型
     */
    @ApiModelProperty(name = "sysTypeName", value = "盘符类型")
    private String sysTypeName;

    /**
     * 文件类型
     */
    @ApiModelProperty(name = "typeName", value = "文件类型")
    private String typeName;

    /**
     * 总大小
     */
    @ApiModelProperty(name = "total", value = "总大小")
    private String total;

    /**
     * 已经使用量
     */
    @ApiModelProperty(name = "used", value = "已经使用量")
    private String used;

    /**
     * 剩余大小
     */
    @ApiModelProperty(name = "free", value = "剩余大小")
    private String free;

    /**
     * 磁盘使用率
     */
    @ApiModelProperty(name = "usage", value = "磁盘使用率")
    private double usage;

}
