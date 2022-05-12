package ${package.Mapper};

import ${package.Entity}.${cfg.entityName};
import ${superMapperClassPackage};

/**
 * ${table.comment!} Mapper 接口
 *
 * @author ${author}
 * @since ${date}
 */
<#if kotlin>
interface ${cfg.mapperName} : ${superMapperClass}<${cfg.entityName}>
<#else>
public interface ${cfg.mapperName} extends ${superMapperClass}<${cfg.entityName}> {}
</#if>
