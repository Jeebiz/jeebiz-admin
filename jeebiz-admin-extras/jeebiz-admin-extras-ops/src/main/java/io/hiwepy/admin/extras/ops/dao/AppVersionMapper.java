package io.hiwepy.admin.extras.ops.dao;

import java.util.List;

import io.hiwepy.admin.extras.ops.dao.entities.AppVersion;

/**
 * 应用版本发布Mapper接口
 */
public interface AppVersionMapper 
{
    /**
     * 查询应用版本发布
     * 
     * @param id 应用版本发布ID
     * @return 应用版本发布
     */
    public AppVersion selectAppVersionById(Integer id);

    /**
     * 查询应用版本发布列表
     * 
     * @param appVersion 应用版本发布
     * @return 应用版本发布集合
     */
    public List<AppVersion> selectAppVersionList(AppVersion appVersion);

    /**
     * 新增应用版本发布
     * 
     * @param appVersion 应用版本发布
     * @return 结果
     */
    public int insertAppVersion(AppVersion appVersion);

    /**
     * 修改应用版本发布
     * 
     * @param appVersion 应用版本发布
     * @return 结果
     */
    public int updateAppVersion(AppVersion appVersion);

    /**
     * 删除应用版本发布
     * 
     * @param id 应用版本发布ID
     * @return 结果
     */
    public int deleteAppVersionById(Integer id);

    /**
     * 批量删除应用版本发布
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteAppVersionByIds(Integer[] ids);
}
