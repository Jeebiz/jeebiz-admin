package io.hiwepy.admin.extras.ops.service;

import java.util.List;

import io.hiwepy.admin.extras.ops.dao.entities.AppList;

/**
 * 应用信息Service接口
 */
public interface IAppListService 
{
    /**
     * 查询应用信息
     * 
     * @param appId 应用信息ID
     * @return 应用信息
     */
    public AppList selectAppListById(Integer appId);

    /**
     * 查询应用信息列表
     * 
     * @param appList 应用信息
     * @return 应用信息集合
     */
    public List<AppList> selectAppListList(AppList appList);

    /**
     * 新增应用信息
     * 
     * @param appList 应用信息
     * @return 结果
     */
    public int insertAppList(AppList appList);

    /**
     * 修改应用信息
     * 
     * @param appList 应用信息
     * @return 结果
     */
    public int updateAppList(AppList appList);

    /**
     * 批量删除应用信息
     * 
     * @param appIds 需要删除的应用信息ID
     * @return 结果
     */
    public int deleteAppListByIds(Integer[] appIds);

    /**
     * 删除应用信息信息
     * 
     * @param appId 应用信息ID
     * @return 结果
     */
    public int deleteAppListById(Integer appId);
}