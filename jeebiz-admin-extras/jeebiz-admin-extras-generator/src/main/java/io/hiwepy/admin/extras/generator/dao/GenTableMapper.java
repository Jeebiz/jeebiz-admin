package io.hiwepy.admin.extras.generator.dao;

import io.hiwepy.admin.extras.generator.dao.entities.GenTableEntity;

import java.util.List;

/**
 * 业务 数据层
 */
public interface GenTableMapper {
    /**
     * 查询业务列表
     * 
     * @param genTable 业务信息
     * @return 业务集合
     */
    public List<GenTableEntity> selectGenTableEntityList(GenTableEntity genTable);

    /**
     * 查询据库列表
     * 
     * @param genTable 业务信息
     * @return 数据库表集合
     */
    public List<GenTableEntity> selectDbTableList(GenTableEntity genTable);

    /**
     * 查询据库列表
     * 
     * @param tableNames 表名称组
     * @return 数据库表集合
     */
    public List<GenTableEntity> selectDbTableListByNames(String[] tableNames);

    /**
     * 查询所有表信息
     * 
     * @return 表信息集合
     */
    public List<GenTableEntity> selectGenTableEntityAll();

    /**
     * 查询表ID业务信息
     * 
     * @param id 业务ID
     * @return 业务信息
     */
    public GenTableEntity selectGenTableEntityById(Long id);

    /**
     * 查询表名称业务信息
     * 
     * @param tableName 表名称
     * @return 业务信息
     */
    public GenTableEntity selectGenTableEntityByName(String tableName);

    /**
     * 新增业务
     * 
     * @param genTable 业务信息
     * @return 结果
     */
    public int insertGenTableEntity(GenTableEntity genTable);

    /**
     * 修改业务
     * 
     * @param genTable 业务信息
     * @return 结果
     */
    public int updateGenTableEntity(GenTableEntity genTable);

    /**
     * 批量删除业务
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteGenTableEntityByIds(Long[] ids);
}
