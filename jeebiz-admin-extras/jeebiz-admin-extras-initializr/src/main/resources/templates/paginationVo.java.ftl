package ${cfg.voPackageName};

<#if swagger2>
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
</#if>
<#if entityLombokModel>
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
</#if>
import com.knowway.cloud.api.vo.AbstractPaginationVo;

/**
* ${table.comment!}分页查询传输层对象
*
* @author ${author}
* @since ${date}
*/
<#if entityLombokModel>
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
</#if>
<#if swagger2>
@ApiModel(value="${table.comment!}分页查询传输层对象", description="${table.comment!}分页查询传输层对象")
</#if>
public class ${cfg.entityName?replace('Model','PaginationVo')} extends AbstractPaginationVo {
<#-- ----------  BEGIN 字段循环遍历  ---------->
<#list table.fields as field>
<#if field.propertyName!='id' && field.propertyName!='gxsj' && field.propertyName!='gxrxgh'>
    <#if field.keyFlag>
        <#assign keyPropertyName="${field.propertyName}"/>
    </#if>
    
    <#if field.comment!?length gt 0>
        <#if swagger2>
    /**
    * ${field.comment}
    */
    @ApiModelProperty(name = "${field.propertyName}",dataType = "${field.propertyType}",value = "${field.comment}")
        </#if>
    </#if>
    private ${field.propertyType} ${field.propertyName};
</#if>
</#list>
<#------------  END 字段循环遍历  ---------->

<#if !entityLombokModel>
    <#list table.fields as field>
        <#if field.propertyType == "boolean">
            <#assign getprefix="is"/>
        <#else>
            <#assign getprefix="get"/>
        </#if>
    public ${field.propertyType} ${getprefix}${field.capitalName}() {
    return ${field.propertyName};
    }

        <#if entityBuilderModel>
    public ${cfg.entityName} set${field.capitalName}(${field.propertyType} ${field.propertyName}) {
        <#else>
    public void set${field.capitalName}(${field.propertyType} ${field.propertyName}) {
        </#if>
    this.${field.propertyName} = ${field.propertyName};
        <#if entityBuilderModel>
    return this;
        </#if>
        }
    </#list>
</#if>
<#if !entityLombokModel>
    @Override
    public String toString() {
    return "${cfg.entityName}{" +
    <#list table.fields as field>
        <#if field_index==0>
            "${field.propertyName}=" + ${field.propertyName} +
        <#else>
            ", ${field.propertyName}=" + ${field.propertyName} +
        </#if>
    </#list>
    "}";
    }
</#if>
}
