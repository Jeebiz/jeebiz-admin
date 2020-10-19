package net.jeebiz.admin.extras.initializr.web.mvc;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import net.jeebiz.admin.extras.initializr.service.CodeGeneratorService;
import net.jeebiz.boot.api.ApiRestResponse;
import net.jeebiz.boot.api.annotation.AllowableValues;
import net.jeebiz.boot.api.web.BaseApiController;

/**
 * @ClassName GeneratorController
 * @Description 代码生成 : 业务实现
 * @Author zd
 * @Date 2019/7/9 17:47
 * @Version 1.0
 **/
@RestController
@Api(tags = {"代码生成 : 业务实现--注意:需要自己在i18n文件中添加国际化提示信息"})
@RequestMapping("/generator/")
@Validated
public class GeneratorController extends BaseApiController {

    @Autowired
    private CodeGeneratorService codeGeneratorService;

    @ApiOperation(value = "生成结构代码模板",notes = "生成结构代码模板")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "projectPath", value = "要生成文件的项目绝对路径", required = true, dataType = "String",
                    example = "E:\\knowWayWorkspace\\knowway-smartedu\\smartedu-projects\\smartedu-attendance\\smartedu-epidemic"),
            @ApiImplicitParam(paramType = "query", name = "createName", value = "要生成的类的英文名称", required = true, dataType = "String",example = "PaysicalTest"),
            @ApiImplicitParam(paramType = "query", name = "moudlePackageName", value = "包路径除去com.knowway.smartedu以及后面具体包名后的特定路径",
                    required = true,dataType = "String",example = "epidemics"),
            @ApiImplicitParam(paramType = "query", name = "jdbcUrl", value = "oracle数据库连接地址", dataType = "String",
                    defaultValue = "jdbc:oracle:thin:@192.168.2.161:1521:orcl"),
            @ApiImplicitParam(paramType = "query", name = "jdbcUsername", value = "数据库用户名", dataType = "String" , defaultValue = "zh_cloud_dev"),
            @ApiImplicitParam(paramType = "query", name = "jdbcPassword", value = "数据库密码", dataType = "String", defaultValue = "zh_cloud_dev"),
            @ApiImplicitParam(paramType = "query", name = "include", value = "要生成的表名", required = true, dataType = "String",example = "GXXX_YQ_CRMSZB"),
            @ApiImplicitParam(paramType = "query", name = "tablePrefix", value = "表名前缀", required = true,dataType = "String",example = "GXXX_YQ_"),
            @ApiImplicitParam(paramType = "query", name = "author", value = "作者", required = true, dataType = "String",example = "zd"),
            @ApiImplicitParam(paramType = "query", name = "enableCache", value = "是否启用缓存,默认不启用", required = true, dataType = "String",
                    allowableValues = "true,false",defaultValue = "false"),
            @ApiImplicitParam(paramType = "query", name = "createExportMethod", value = "是否创建文件导出方法", dataType = "String",
                    allowableValues = "true,false",defaultValue = "true"),
            @ApiImplicitParam(paramType = "query", name = "createImportMethod", value = "是否创建文件导入方法", dataType = "String",
                    allowableValues = "true,false",defaultValue = "true")
    })
    @GetMapping({"new"})
    public ApiRestResponse<String> newRow(@Valid @NotBlank(message = "项目绝对地址不能为空!") String projectPath,
                                          @NotBlank(message = "生成类英文名称不能为空!") String createName, String moudlePackageName,
                                          @RequestParam(required = false,defaultValue = "jdbc:oracle:thin:@192.168.2.161:1521:orcl") String jdbcUrl,
                                          @RequestParam(required = false,defaultValue = "zh_cloud_dev") String jdbcUsername,
                                          @RequestParam(required = false,defaultValue = "zh_cloud_dev") String jdbcPassword,
                                          @NotBlank(message = "生成表不能为空!") String include, String tablePrefix,
                                          @NotBlank(message = "作者不能为空!") String author,
                                          @AllowableValues(allows = "true,false") String enableCache,
                                          @AllowableValues(allows = "true,false") String createExportMethod,
                                          @AllowableValues(allows = "true,false") String createImportMethod) {
        getCodeGeneratorService().generator(projectPath,createName,moudlePackageName,jdbcUrl,jdbcUsername,jdbcPassword,
                include,tablePrefix,author,Boolean.parseBoolean(enableCache),Boolean.parseBoolean(createExportMethod),
                Boolean.parseBoolean(createImportMethod));
        return success("generator.new.success");
    }

    private CodeGeneratorService getCodeGeneratorService() {
        return codeGeneratorService;
    }
}