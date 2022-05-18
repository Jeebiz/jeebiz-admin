package io.hiwepy.admin.extras.generator.setup;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.ConstVal;
import com.baomidou.mybatisplus.generator.config.FileOutConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.FileType;
import com.baomidou.mybatisplus.generator.engine.AbstractTemplateEngine;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

/**
 * @ClassName CustomFreemarkerTemplateEngine
 * @Description 自定义mybatis-plus-generator中针对于freemarker的修改,使生成文件名称为自定义输入名称
 * @Author zd
 * @Date 2019/7/8 15:44
 * @Version 1.0
 **/
public class CustomFreemarkerTemplateEngine extends FreemarkerTemplateEngine {

    private String name;

    @Override
    public AbstractTemplateEngine batchOutput() {
        try {
            List<TableInfo> tableInfoList = getConfigBuilder().getTableInfoList();
            for (TableInfo tableInfo : tableInfoList) {
                Map<String, Object> objectMap = getObjectMap(tableInfo);
                Map<String, String> pathInfo = getConfigBuilder().getPathInfo();
                TemplateConfig template = getConfigBuilder().getTemplate();
                // 自定义内容
                InjectionConfig injectionConfig = getConfigBuilder().getInjectionConfig();
                if (null != injectionConfig) {
                    injectionConfig.initMap();
                    objectMap.put("cfg", injectionConfig.getMap());
                    List<FileOutConfig> focList = injectionConfig.getFileOutConfigList();
                    if (CollectionUtils.isNotEmpty(focList)) {
                        for (FileOutConfig foc : focList) {
                            if (isCreate(FileType.OTHER, foc.outputFile(tableInfo))) {
                                writer(objectMap, foc.getTemplatePath(), foc.outputFile(tableInfo));
                            }
                        }
                    }
                }
                // Mp.java
                // DONE 修改生成类名为自定义类名,如果没有自定义则使用默认名
                String entityName = tableInfo.getEntityName();
                GlobalConfig globalConfig = getConfigBuilder().getGlobalConfig();
                if (null != entityName && null != pathInfo.get(ConstVal.ENTITY_PATH)) {
                    String entityFile;
                    if(StringUtils.isNotBlank(name)){
                        entityFile = String.format((pathInfo.get(ConstVal.ENTITY_PATH) + File.separator + globalConfig.getEntityName() +suffixJavaOrKt()), name);
                    }else{
                        entityFile = String.format((pathInfo.get(ConstVal.ENTITY_PATH) + File.separator + "%s" + suffixJavaOrKt()), entityName);
                    }
                    if (isCreate(FileType.ENTITY, entityFile)) {
                        writer(objectMap, templateFilePath(template.getEntity(getConfigBuilder().getGlobalConfig().isKotlin())), entityFile);
                    }
                }
                // MpMapper.java
                if (null != tableInfo.getMapperName() && null != pathInfo.get(ConstVal.MAPPER_PATH)) {
                    String mapperFile;
                    if(StringUtils.isNotBlank(name)){
                        mapperFile = String.format((pathInfo.get(ConstVal.MAPPER_PATH) + File.separator + globalConfig.getMapperName() +suffixJavaOrKt()), name);
                    }else{
                        mapperFile = String.format((pathInfo.get(ConstVal.MAPPER_PATH) + File.separator + tableInfo.getMapperName() + suffixJavaOrKt()), entityName);
                    }
                    if (isCreate(FileType.MAPPER, mapperFile)) {
                        writer(objectMap, templateFilePath(template.getMapper()), mapperFile);
                    }
                }
                // MpMapper.xml
                if (null != tableInfo.getXmlName() && null != pathInfo.get(ConstVal.XML_PATH)) {
                    String xmlFile;
                    if(StringUtils.isNotBlank(name)){
                        xmlFile = String.format((pathInfo.get(ConstVal.XML_PATH) + File.separator + globalConfig.getXmlName() + ConstVal.XML_SUFFIX ), name);
                    }else{
                        xmlFile = String.format((pathInfo.get(ConstVal.XML_PATH) + File.separator + tableInfo.getXmlName() + ConstVal.XML_SUFFIX), entityName);
                    }
                    if (isCreate(FileType.XML, xmlFile)) {
                        writer(objectMap, templateFilePath(template.getXml()), xmlFile);
                    }
                }
                // IMpService.java
                if (null != tableInfo.getServiceName() && null != pathInfo.get(ConstVal.SERVICE_PATH)) {
                    String serviceFile;
                    if(StringUtils.isNotBlank(name)){
                        serviceFile = String.format((pathInfo.get(ConstVal.SERVICE_PATH) + File.separator + globalConfig.getServiceName() + suffixJavaOrKt() ), name);
                    }else{
                        serviceFile =  String.format((pathInfo.get(ConstVal.SERVICE_PATH) + File.separator + tableInfo.getServiceName() + suffixJavaOrKt()), entityName);
                    }
                    if (isCreate(FileType.SERVICE, serviceFile)) {
                        writer(objectMap, templateFilePath(template.getService()), serviceFile);
                    }
                }
                // MpServiceImpl.java
                if (null != tableInfo.getServiceImplName() && null != pathInfo.get(ConstVal.SERVICE_IMPL_PATH)) {
                    String implFile;
                    if(StringUtils.isNotBlank(name)){
                        implFile = String.format((pathInfo.get(ConstVal.SERVICE_IMPL_PATH) + File.separator + globalConfig.getServiceImplName() + suffixJavaOrKt() ), name);
                    }else{
                        implFile =  String.format((pathInfo.get(ConstVal.SERVICE_IMPL_PATH) + File.separator + tableInfo.getServiceImplName() + suffixJavaOrKt()), entityName);
                    }
                    if (isCreate(FileType.SERVICE_IMPL, implFile)) {
                        writer(objectMap, templateFilePath(template.getServiceImpl()), implFile);
                    }
                }
                // MpController.java
                if (null != tableInfo.getControllerName() && null != pathInfo.get(ConstVal.CONTROLLER_PATH)) {
                    String controllerFile;
                    if(StringUtils.isNotBlank(name)){
                        controllerFile = String.format((pathInfo.get(ConstVal.CONTROLLER_PATH) + File.separator + globalConfig.getControllerName() + suffixJavaOrKt() ), name);
                    }else{
                        controllerFile =  String.format((pathInfo.get(ConstVal.CONTROLLER_PATH) + File.separator + tableInfo.getControllerName() + suffixJavaOrKt()), entityName);
                    }
                    if (isCreate(FileType.CONTROLLER, controllerFile)) {
                        writer(objectMap, templateFilePath(template.getController()), controllerFile);
                    }
                }
            }
        } catch (Exception e) {
        }
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }
}
