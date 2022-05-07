package io.hiwepy.admin.extras.monitor.web.mvc;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import hitool.core.format.ByteUnitFormat;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.hiwepy.admin.extras.monitor.service.IServerInfoService;
import io.hiwepy.admin.extras.monitor.web.vo.CpuInfoVo;
import io.hiwepy.admin.extras.monitor.web.vo.JvmInfoVo;
import io.hiwepy.admin.extras.monitor.web.vo.MemInfoVo;
import io.hiwepy.admin.extras.monitor.web.vo.ServerInfoVo;
import io.hiwepy.admin.extras.monitor.web.vo.SysDiskInfoVo;
import io.hiwepy.boot.api.ApiRestResponse;
import io.hiwepy.boot.api.web.BaseController;

@Api(tags = "Sys：系统监控（Ok）")
@RestController
@RequestMapping(value = "/server/")
@Validated
public class ServerInfoController extends BaseController {

    @Autowired
    private IServerInfoService serverInfoService;

    @ApiOperation(value = "系统服务器信息", notes = "查询功能菜单扁平结构数据")
    @GetMapping("info")
    @RequiresPermissions("monitor:list")
    @ResponseBody
    public ApiRestResponse<ServerInfoVo> info(){
        try {
            return ApiRestResponse.success(getServerInfoService().getServerInfo(ByteUnitFormat.G));
        } catch (Exception e) {
            return fail("server.monitor.info.get.fail");
        }
    }

    @ApiOperation(value = "CPU相关信息", notes = "获取CPU相关信息")
    @GetMapping("cpu/info")
    @RequiresPermissions("monitor:list")
    @ResponseBody
    public ApiRestResponse<CpuInfoVo> cpuInfo(){
        try {
            return ApiRestResponse.success(getServerInfoService().getCpuInfo(ByteUnitFormat.G));
        } catch (Exception e) {
            return fail("server.cpu.info.get.fail");
        }
    }

    @ApiOperation(value = "内存相关信息", notes = "获取内存相关信息")
    @GetMapping("mem/info")
    @RequiresPermissions("monitor:list")
    @ResponseBody
    public ApiRestResponse<MemInfoVo> memInfo(){
        try {
            return ApiRestResponse.success(getServerInfoService().getMemInfo(ByteUnitFormat.G));
        } catch (Exception e) {
            return fail("server.mem.info.get.fail");
        }
    }

    @ApiOperation(value = "JVM相关信息", notes = "获取JVM相关信息")
    @GetMapping("jvm/info")
    @RequiresPermissions("monitor:list")
    @ResponseBody
    public ApiRestResponse<JvmInfoVo> jvmInfo(){
        try {
            return ApiRestResponse.success(getServerInfoService().getJvmInfo(ByteUnitFormat.G));
        } catch (Exception e) {
            return fail("server.jvm.info.get.fail");
        }
    }

    @ApiOperation(value = "磁盘相关信息", notes = "获取磁盘相关信息")
    @GetMapping("disk/info")
    @RequiresPermissions("monitor:list")
    @ResponseBody
    public ApiRestResponse<List<SysDiskInfoVo>> diskInfo(){
        try {
            return ApiRestResponse.success(getServerInfoService().getDiskInfos(ByteUnitFormat.G));
        } catch (Exception e) {
            return fail("server.disk.info.get.fail");
        }
    }

    public IServerInfoService getServerInfoService() {// monitor
        return serverInfoService;
    }
}
