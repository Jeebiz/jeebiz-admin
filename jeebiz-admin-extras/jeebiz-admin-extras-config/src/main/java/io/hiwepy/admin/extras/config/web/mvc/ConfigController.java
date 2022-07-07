package io.hiwepy.admin.extras.config.web.mvc;


import io.hiwepy.admin.extras.config.dao.entities.ConfigEntity;
import io.hiwepy.admin.extras.config.service.IConfigService;
import io.hiwepy.admin.extras.config.web.dto.ConfigDTO;
import io.hiwepy.admin.extras.config.web.dto.ConfigNewDTO;
import io.hiwepy.boot.api.ApiRestResponse;
import io.hiwepy.boot.api.annotation.BusinessLog;
import io.hiwepy.boot.api.annotation.BusinessType;
import io.hiwepy.boot.api.web.BaseMapperController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * <p>
 * 三方集成配置信息 前端控制器
 * </p>
 *
 * @author wandl
 * @since 2022-06-14
 */
@Api(tags = "参数配置：系统配置")
@RestController
@RequestMapping("/config")
public class ConfigController extends BaseMapperController {

    @Autowired
    private IConfigService configService;

    @ApiOperation(value = "新增/更新配置", notes = "增加或更新一个参数配置信息")
    @PostMapping("new")
    @RequiresAuthentication
    @ResponseBody
    public ApiRestResponse<String> saveOrUpdate(@Valid @RequestBody ConfigNewDTO newDTO) throws Exception {
        // 新增一条配置记录
        boolean result = getConfigService().saveOrUpdate(newDTO);
        if(result) {
            return success("config.new.success", result);
        }
        // 逻辑代码，如果发生异常将不会被执行
        return fail("config.new.fail", result);
    }

    @ApiOperation(value = "参数配置信息", notes = "查询指定id的参数配置信息")
    @GetMapping("detail")
    @RequiresAuthentication
    @ResponseBody
    public ApiRestResponse<ConfigDTO> detail(@RequestParam("id") String id) throws Exception {
        ConfigDTO rtDto = getConfigService().getConfigById(id);
        if(rtDto == null) {
            return ApiRestResponse.fail(getMessage("config.get.empty"));
        }
        return ApiRestResponse.success(rtDto);
    }

    public IConfigService getConfigService() {
        return configService;
    }

}

