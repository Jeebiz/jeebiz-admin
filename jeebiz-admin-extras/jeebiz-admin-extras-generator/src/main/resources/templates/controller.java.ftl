package ${package.Controller};

import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import com.google.common.collect.Lists;

import io.hiwepy.boot.api.ApiRestResponse;
import io.hiwepy.boot.api.annotation.BusinessLog;
import io.hiwepy.boot.api.annotation.BusinessType;
import io.hiwepy.boot.api.webmvc.Result;

import javax.validation.Valid;
import java.util.List;
import ${package.Service}.${cfg.serviceName};

<#if restControllerStyle>
import org.springframework.web.bind.annotation.RestController;
<#else>
import org.springframework.stereotype.Controller;
</#if>
<#if superControllerClassPackage??>
import ${superControllerClassPackage};
</#if>
<#list table.fields as field>
<#if field.propertyName=='sfqy'>
import javax.validation.constraints.NotBlank;
</#if>
</#list>
<#assign pageVo="${cfg.createName + 'PaginationVo'}"/>
<#assign queryVo="${cfg.createName+'QueryVo'}"/>
<#assign newVo="${cfg.createName+'NewVo'}"/>
<#assign renewVo="${cfg.createName+'ReNewVo'}"/>
<#assign resultVo="${cfg.createName+'ResultVo'}"/>
import ${package.Entity}.${cfg.entityName};
import ${cfg.voPackageName}.${pageVo};
import ${cfg.voPackageName}.${queryVo};
import ${cfg.voPackageName}.${newVo};
import ${cfg.voPackageName}.${renewVo};
import ${cfg.voPackageName}.${resultVo};
<#if cfg.createExportMethod==true>
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import com.knowway.cloud.extras.imexport.service.idataExportService;
</#if>
<#if cfg.createImportMethod==true>
import com.knowway.cloud.extras.imexport.service.idataImportService;
import io.hiwepy.fastxls.core.model.ConstraintViolationResult;
import org.springframework.web.multipart.MultipartFile;
import javax.validation.ConstraintViolationException;
import io.hiwepy.fastxls.core.model.RowMap;
</#if>

/**
 * ${table.comment!} 前端控制器
 *
 * @author ${author}
 * @since ${date}
 */
@Api(tags = {"${table.comment} : 业务实现"})
<#if restControllerStyle>
@RestController
<#else>
 @Controller
</#if>
@Validated
@RequestMapping("<#if package.ModuleName??>/${package.ModuleName}</#if>/${cfg.createName?uncap_first}")
<#if kotlin>
class ${cfg.controllerName}<#if superControllerClass??> : ${superControllerClass}()</#if>
<#else>
<#if superControllerClass??>
public class ${cfg.controllerName} extends ${superControllerClass} {
<#else>
 public class ${cfg.controllerName} {
</#if>

    private final ${cfg.serviceName} ${cfg.serviceName[1..]?uncap_first};
<#if cfg.createExportMethod==true>

    private final idataExportService dataExportService;
</#if>
<#if cfg.createImportMethod==true>

    private final idataImportService dataImportService;
</#if>

    public ${cfg.controllerName}(${cfg.serviceName} ${cfg.serviceName[1..]?uncap_first}<#if cfg.createExportMethod==true>,idataExportService dataExportService</#if><#if cfg.createImportMethod==true>,idataImportService dataImportService</#if>){
        this.${cfg.serviceName[1..]?uncap_first} = ${cfg.serviceName[1..]?uncap_first};
        <#if cfg.createExportMethod==true>this.dataExportService = dataExportService;</#if>
        <#if cfg.createImportMethod==true>this.dataImportService = dataImportService;</#if>
    }

    @ApiOperation(value = "分页查询${table.comment}记录",notes = "分页查询${table.comment}记录")
    @PostMapping({"page"})
    @PreAuthorize("authenticated")
    public Result<${resultVo}> page(@Valid @RequestBody ${pageVo} paginationVo){
        ${cfg.entityName} model = this.getBeanMapper().map(paginationVo,${cfg.entityName}.class);
        Page<${cfg.entityName}> pageResult = this.get${cfg.serviceName[1..]}().getPagedList(model);
        List<${resultVo}> retList = Lists.newArrayList();
        for (${cfg.entityName} ${cfg.entityName?uncap_first} : pageResult.getRecords()) {
            retList.add(getBeanMapper().map(${cfg.entityName?uncap_first}, ${resultVo}.class));
        }
        return new Result(pageResult, retList);
    }

    @ApiOperation(value = "查询${table.comment}列表记录",notes = "查询${table.comment}列表记录")
    @PostMapping({"list"})
    @PreAuthorize("authenticated")
    public ApiRestResponse<List<${resultVo}>> list(@Valid @RequestBody ${queryVo} queryVo){
        ${cfg.entityName} model = this.getBeanMapper().map(queryVo,${cfg.entityName}.class);
        List<${cfg.entityName}> list = this.get${cfg.serviceName[1..]}().getModelList(model);
        List<${resultVo}> retList = Lists.newArrayList();
        for (${cfg.entityName} ${cfg.entityName?uncap_first} : list) {
            retList.add(getBeanMapper().map(${cfg.entityName?uncap_first}, ${resultVo}.class));
        }
        return ApiRestResponse.success(retList);
    }

    @ApiOperation(value = "指定${table.comment}记录", notes = "根据id查询${table.comment}记录")
    @ApiImplicitParams({
        @ApiImplicitParam( paramType = "query", name = "id", required = true,value = "id", dataType = "String")
    })
    @GetMapping({"detail"})
    @PreAuthorize("authenticated")
    public ApiRestResponse<${resultVo}> detail(String id){
        ${cfg.entityName} model = get${cfg.serviceName[1..]}().getModel(id);
        if(model == null) {
            return ApiRestResponse.empty(getMessage("<#if package.ModuleName??>${package.ModuleName}.</#if>get.empty"));
        }
        return ApiRestResponse.success(getBeanMapper().map(model,${resultVo}.class));
    }

    @ApiOperation(value = "增加${table.comment}记录",notes = "增加${table.comment}记录")
    @BusinessLog( module = "增加${table.comment}记录", business = "增加${table.comment}记录", opt = BusinessType.INSERT)
    @PostMapping({"new"})
    @PreAuthorize("authenticated")
    public ApiRestResponse<String> insert(@Valid @RequestBody ${newVo} newVo){
        ${cfg.entityName} model  = this.getBeanMapper().map(newVo,${cfg.entityName}.class);
        int result = get${cfg.serviceName[1..]}().insert(model);
        if(result == 1) {
            return success("<#if package.ModuleName??>${package.ModuleName}.</#if>new.success", result);
        }
        return fail("<#if package.ModuleName??>${package.ModuleName}.</#if>new.fail", result);
    }

    @ApiOperation(value = "修改${table.comment}记录", notes = "修改${table.comment}记录")
    @BusinessLog(module = "修改${table.comment}记录", business = "修改${table.comment}记录", opt = BusinessType.UPDATE)
    @PostMapping("renew")
    @PreAuthorize("authenticated")
    public ApiRestResponse<String> update(@Valid @RequestBody ${renewVo} renewVo){
        ${cfg.entityName} model = getBeanMapper().map(renewVo,${cfg.entityName}.class);
        int count = this.get${cfg.serviceName[1..]}().getCountByUid(model.getId());
        if (count == 0){
            return fail("<#if package.ModuleName??>${package.ModuleName}.</#if>renew.not.exists");
        }
        int result = this.get${cfg.serviceName[1..]}().update(model);
        if(result > 0) {
            return success("<#if package.ModuleName??>${package.ModuleName}.</#if>renew.success", result);
        }
        return fail("<#if package.ModuleName??>${package.ModuleName}.</#if>renew.fail", result);
    }

    @ApiOperation(value = "删除${table.comment}记录", notes = "删除${table.comment}记录")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "query", name = "id", value = "${table.comment}记录id", required = true, dataType = "String")
    })
    @BusinessLog(module = "删除${table.comment}记录", business = "删除${table.comment}记录" , opt = BusinessType.DELETE)
    @GetMapping("delete")
    @PreAuthorize("authenticated")
    public ApiRestResponse<String> del(String id){
        int total = this.get${cfg.serviceName[1..]}().delete(id);
        if(total > 0) {
            return success("<#if package.ModuleName??>${package.ModuleName}.</#if>delete.success",total);
        }
        return fail("<#if package.ModuleName??>${package.ModuleName}.</#if>delete.fail",total);
    }

<#list table.fields as field>
<#if field.propertyName=='sfqy'>
     @ApiOperation(value = "根据记录id更新启用状态", notes = "根据记录id更新启用状态")
     @ApiImplicitParams({
         @ApiImplicitParam(paramType = "query", name = "id", required = true, value = "记录id", dataType = "String"),
         @ApiImplicitParam(paramType = "query", name = "status", required = true, value = "启用状态(0:停用,1:启用)", dataType = "String")
     })
     @GetMapping("status")
     @PreAuthorize("authenticated")
     public ApiRestResponse<String> setStatus(@NotBlank(message = "记录id不能为空!") String id,@NotBlank(message = "启用停用状态不能为空!") String status) {
         //查询该课程是否已结束
         int count=this.get${cfg.serviceName[1..]}().setStatus(id,status);
         if (count == 0) {
             return fail("<#if package.ModuleName??>${package.ModuleName}.</#if>status.fail",count);
         }
         return success("<#if package.ModuleName??>${package.ModuleName}.</#if>status.success",count);
     }
</#if>
</#list>

<#if cfg.createExportMethod==true>
     @ApiOperation(value = "导出Excel${table.comment}", notes = "导出Excel${table.comment}")
     @PostMapping("download/excel")
     @PreAuthorize("authenticated")
     public ResponseEntity<byte[]> exportExcel(@Valid @RequestBody ${queryVo} queryVo) throws Exception {
         ${cfg.entityName} model = this.getBeanMapper().map(queryVo,${cfg.entityName}.class);
         List<${cfg.entityName}> list = this.get${cfg.serviceName[1..]}().getModelList(model);
         Map<String,Object> param = new HashMap<>();
         param.put("list", list);
         return getDataExportService().dataExportTpl("${cfg.createName}_001", param);
     }
</#if>
<#if cfg.createImportMethod==true>

     @ApiOperation(value = "导入Excel${table.comment}", notes = "导入Excel${table.comment}")
     @PostMapping("excel/upload")
     @PreAuthorize("authenticated")
     public ApiRestResponse uploadExcel(MultipartFile file) throws Exception{
        String fileName = file.getOriginalFilename();
        if (!(fileName.endsWith(".xls") || fileName.endsWith(".xlsx"))) {
            return this.fail("上传文件格式错误");
        }
        if (file.isEmpty()) {
            return this.fail("上传文件为空");
        }
        HashMap param = new HashMap();
        // 调用公共方法进行导入
        ConstraintViolationResult<RowMap> result = getDataImportService().dataImport(
                "TAB_${table.name?capitalize}", file, param);
        if (!result.accept()) {
            throw new ConstraintViolationException(result.getConstraintViolations());
        }
        return success("<#if package.ModuleName??>${package.ModuleName}.</#if>excel.upload.success");
     }
</#if>

    public ${cfg.serviceName} get${cfg.serviceName[1..]}(){ return this.${cfg.serviceName[1..]?uncap_first}; }
<#if cfg.createExportMethod==true>

    public idataExportService getDataExportService() { return dataExportService;}
</#if>
<#if cfg.createImportMethod==true>

    public idataImportService getDataImportService() { return dataImportService;}
</#if>
}
</#if>
