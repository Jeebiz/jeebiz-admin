package net.jeebiz.admin.extras.monitor.web.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 系统相关信息
 */
@ApiModel(value = "SysInfoVo", description = "系统相关信息")
@Data
public class SysInfoVo {

    /**
     * 服务器名称
     */
    @ApiModelProperty(name = "computerName", value = "服务器名称")
    private String computerName;

    /**
     * 服务器IP
     */
    @ApiModelProperty(name = "computerIp", value = "服务器IP")
    private String computerIp;

    /**
     * 项目路径
     */
    @ApiModelProperty(name = "userDir", value = "服务器IP")
    private String userDir;

    /**
     * 操作系统
     */
    @ApiModelProperty(name = "osName", value = "操作系统")
    private String osName;

    /**
     * 系统架构
     */
    @ApiModelProperty(name = "osArch", value = "系统架构")
    private String osArch;

}
