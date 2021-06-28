package net.jeebiz.admin.extras.monitor.web.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedList;
import java.util.List;

/**
 * 服务器信息
 */
@ApiModel(value = "ServerInfoVo", description = "服务器信息")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServerInfoVo {

    /**
     * CPU相关信息
     */
    @ApiModelProperty(name = "sys", value = "CPU相关信息")
    private CpuInfoVo cpu;

    /**
     * 內存相关信息
     */
    @ApiModelProperty(name = "sys", value = "內存相关信息")
    private MemInfoVo mem;

    /**
     * JVM相关信息
     */
    @ApiModelProperty(name = "jvm", value = "JVM相关信息")
    private JvmInfoVo jvm;

    /**
     * 系统相关信息
     */
    @ApiModelProperty(name = "sys", value = "系统相关信息")
    private SysInfoVo sys;

    /**
     * 磁盘信息
     */
    @ApiModelProperty(name = "disks",  value = "磁盘信息")
    private List<SysDiskInfoVo> disks;

}
