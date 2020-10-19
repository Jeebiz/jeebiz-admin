package ${package.ServiceImpl};

import ${package.Entity}.${cfg.entityName};
import ${package.Mapper}.${cfg.mapperName};
import ${package.Service}.${cfg.serviceName};
import ${superServiceImplClassPackage};
import org.springframework.stereotype.Service;

/**
 * ${table.comment!} 服务实现类
 *
 * @author ${author}
 * @since ${date}
 */
@Service
<#if kotlin>
open class ${cfg.serviceImplName} : ${superServiceImplClass}<${cfg.mapperName}, ${cfg.entityName}>(), ${cfg.serviceName} {}
<#else>
public class ${cfg.serviceImplName} extends ${superServiceImplClass}<${cfg.entityName},${cfg.mapperName}> implements ${cfg.serviceName} {}
</#if>
