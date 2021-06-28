package net.jeebiz.admin.extras.monitor.web.mvc;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.jeebiz.admin.extras.monitor.service.IServerInfoService;
import net.jeebiz.admin.extras.monitor.utils.OshiUtils;
import net.jeebiz.admin.extras.monitor.web.vo.CpuInfoVo;
import net.jeebiz.admin.extras.monitor.web.vo.ServerInfoVo;
import net.jeebiz.boot.api.ApiRestResponse;
import net.jeebiz.boot.api.utils.ByteUnitFormat;
import net.jeebiz.boot.api.web.BaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    public ApiRestResponse<ServerInfoVo> info(String unit){
        try {
            return ApiRestResponse.success(getServerInfoService().getServerInfo(ByteUnitFormat.valueOf(unit)));
        } catch (Exception e) {
            return fail("sys.monitor.get.fail");
        }
    }

    @ApiOperation(value = "CPU相关信息", notes = "获取CPU相关信息")
    @GetMapping("cpu/info")
    @RequiresPermissions("monitor:list")
    @ResponseBody
    public ApiRestResponse<CpuInfoVo> cpuInfo(String unit){
        try {
            return ApiRestResponse.success(getServerInfoService().getCpuInfo(ByteUnitFormat.valueOf(unit)));
        } catch (Exception e) {
            return fail("sys.monitor.get.fail");
        }
    }


    @ApiOperation(value = "CPU相关信息", notes = "获取CPU相关信息")
    @GetMapping("mem/info")
    @RequiresPermissions("monitor:list")
    @ResponseBody
    public ApiRestResponse<CpuInfoVo> memInfo(String unit){
        try {
            return ApiRestResponse.success(getServerInfoService().getCpuInfo(ByteUnitFormat.valueOf(unit)));
        } catch (Exception e) {
            return fail("sys.monitor.get.fail");
        }
    }

    @ApiOperation(value = "CPU相关信息", notes = "获取CPU相关信息")
    @GetMapping("jvm/info")
    @RequiresPermissions("monitor:list")
    @ResponseBody
    public ApiRestResponse<CpuInfoVo> jvmInfo(String unit){
        try {
            return ApiRestResponse.success(getServerInfoService().getCpuInfo(ByteUnitFormat.valueOf(unit)));
        } catch (Exception e) {
            return fail("sys.monitor.get.fail");
        }
    }

    @ApiOperation(value = "CPU相关信息", notes = "获取CPU相关信息")
    @GetMapping("disk/info")
    @RequiresPermissions("monitor:list")
    @ResponseBody
    public ApiRestResponse<CpuInfoVo> diskInfo(String unit){
        try {
            return ApiRestResponse.success(getServerInfoService().getCpuInfo(ByteUnitFormat.valueOf(unit)));
        } catch (Exception e) {
            return fail("sys.monitor.get.fail");
        }
    }

    public IServerInfoService getServerInfoService() {// monitor
        return serverInfoService;
    }
}
