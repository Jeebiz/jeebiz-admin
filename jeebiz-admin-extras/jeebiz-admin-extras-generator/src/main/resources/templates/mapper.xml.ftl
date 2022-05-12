<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${package.Mapper}.${cfg.mapperName}">

<#if enableCache>
    <!-- 开启二级缓存 -->
    <cache type="net.oschina.j2cache.mybatis.J2CacheAdapter"/>

</#if>
<#if baseResultMap>
    <!-- 通用查询映射结果 -->
    <resultMap id="BaseResultMap" type="${cfg.entityName}">
<#list table.fields as field>
<#if field.keyFlag><#--生成主键排在第一位-->
        <id column="${field.name}" property="${field.propertyName}"/>
</#if>
</#list>
<#list table.commonFields as field><#--生成公共字段 -->
        <result column="${field.name}" property="${field.propertyName}"/>
</#list>
<#list table.fields as field>
<#if !field.keyFlag><#--生成普通字段 -->
        <result column="${field.name}" property="${field.propertyName}"/>
</#if>
</#list>
    </resultMap>

</#if>
<#if baseColumnList>
    <!-- 通用查询结果列 -->
    <sql id="Base_Column_List">
<#list table.commonFields as field>
        ${field.name},
</#list>
        ${table.fieldNames}
    </sql>

</#if>
    <!-- 插入${table.comment}记录 -->
    <insert id="insert" parameterType="${cfg.entityName}"<#if enableCache> flushCache="true"</#if>>
        <selectKey keyProperty="id" order="BEFORE" resultType="java.lang.String">
            SELECT sys_guid() FROM DUAL
        </selectKey>
        INSERT INTO ${table.name} (
        <#list table.fields as field><#if field.propertyName!='cjsj' && field.propertyName!='gxsj' && field.propertyName!='gxrxgh'>${field.name}<#if field_has_next && field_index lte (table.fields?size)-4>,</#if></#if></#list>
        ) VALUES (
        <#list table.fields as field><#if field.propertyName!='cjsj' && field.propertyName!='gxsj' && field.propertyName!='gxrxgh'>${"#{"+field.propertyName+"}"}<#if field_has_next && field_index lte (table.fields?size)-4>,</#if></#if></#list>
        )
    </insert>

    <!-- 更新${table.comment}记录 -->
    <update id="update" parameterType="${cfg.entityName}"<#if enableCache> flushCache="true"</#if>>
        UPDATE ${table.name} t
        <set>
    <#list table.fields as field>
        <#if field.propertyName != "id" && field.propertyName!='cjsj' && field.propertyName!='cjrxgh'>
            <if test="${field.propertyName} != null and ${field.propertyName} != ''">t.${field.name} = ${"#{"+field.propertyName+"}"},</if>
        </#if>
    </#list>
        </set>
        WHERE t.id=<#noparse>#{id}</#noparse>
    </update>

    <!-- 根据id删除${table.comment}记录 -->
    <delete id="delete" parameterType="String"<#if enableCache> flushCache="true"</#if>>
        DELETE FROM ${table.name} WHERE id=<#noparse>#{id}</#noparse>
    </delete>

    <!-- 根据Uid查询记录数量 -->
    <select id="getCountByUid" parameterType="String" resultType="Integer"<#if enableCache> useCache="true"</#if>>
        SELECT COUNT(1) FROM ${table.name} WHERE id=<#noparse>#{uid}</#noparse>
    </select>

    <!-- 分页获取${table.comment}记录 -->
    <select id="getPagedList" parameterType="${cfg.entityName}" resultMap="BaseResultMap"<#if enableCache> useCache="false"</#if>>
        SELECT <include refid="Base_Column_List"></include>
        FROM ${table.name}
        <where>
    <#list table.fields as field>
        <#if field.propertyName!='id' && field.propertyName!='cjsj' && field.propertyName!='gxsj' && field.propertyName!='cjrxgh' && field.propertyName!='gxrxgh'>
            <if test="model.${field.propertyName} != null and model.${field.propertyName} != ''">
               AND ${field.name} = <#noparse>#{model.</#noparse>${field.propertyName}<#noparse>}</#noparse>
            </if>
        </#if>
    </#list>
        </where>
        ORDER BY CJSJ DESC
    </select>

    <!-- 获取${table.comment}列表记录(参数实体对象) -->
    <select id="getModelList" parameterType="${cfg.entityName}" resultMap="BaseResultMap"<#if enableCache> useCache="true"</#if>>
        SELECT <include refid="Base_Column_List"></include>
        FROM ${table.name}
        <where>
    <#list table.fields as field>
        <#if field.propertyName!='id' && field.propertyName!='cjsj' && field.propertyName!='gxsj' && field.propertyName!='cjrxgh' && field.propertyName!='gxrxgh'>
            <if test="${field.propertyName} != null and ${field.propertyName} != ''">
                AND ${field.name} = <#noparse>#{</#noparse>${field.propertyName}<#noparse>}</#noparse>
            </if>
        </#if>
    </#list>
        </where>
        ORDER BY CJSJ DESC
    </select>

    <!-- 查询${table.comment}记录详情 -->
    <select id="getModel" parameterType="${cfg.entityName}" resultMap="BaseResultMap"<#if enableCache> useCache="true"</#if>>
        SELECT <include refid="Base_Column_List"></include> FROM ${table.name} WHERE id=<#noparse>#{id}</#noparse>
    </select>

<#list table.fields as field>
<#if field.propertyName=='sfqy'>
    <!-- 根据记录id更新启用状态 -->
    <update id="setStatus" parameterType="String"<#if enableCache> flushCache="true"</#if>>
        UPDATE ${table.name} SET SFQY=<#noparse>#{status}</#noparse> <#noparse>WHERE id=#{id}</#noparse>
    </update>
</#if>
</#list>
</mapper>
