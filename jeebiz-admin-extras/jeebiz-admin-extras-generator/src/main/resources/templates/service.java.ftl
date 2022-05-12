package ${package.Service};

import ${package.Entity}.${cfg.entityName};
import ${superServiceClassPackage};

/**
 * ${table.comment!} 服务类
 *
 * @author ${author}
 * @since ${date}
 */
<#if kotlin>
interface ${cfg.serviceName} : ${superServiceClass}<${cfg.entityName}>
<#else>
public interface ${cfg.serviceName} extends ${superServiceClass}<${cfg.entityName}> {}
</#if>
