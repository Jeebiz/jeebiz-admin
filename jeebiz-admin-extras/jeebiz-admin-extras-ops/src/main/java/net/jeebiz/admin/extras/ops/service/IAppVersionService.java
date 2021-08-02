package net.jeebiz.admin.extras.ops.service;

import java.util.List;

import net.jeebiz.admin.extras.ops.dao.entities.AppVersion;

/**
 * 应用版本发布Service接口
 */
public interface IAppVersionService 
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
     * @param appVersions 应用版本发布
     * @return 应用版本发布集合
     */
    public List<AppVersion> selectAppVersionList(AppVersion appVersions);

    /**
     * 新增应用版本发布
     * 
     * @param appVersions 应用版本发布
     * @return 结果
     */
    public int insertAppVersion(AppVersion appVersions);

    /**
     * 修改应用版本发布
     * 
     * @param appVersions 应用版本发布
     * @return 结果
     */
    public int updateAppVersion(AppVersion appVersions);

    /**
     * 批量删除应用版本发布
     * 
     * @param ids 需要删除的应用版本发布ID
     * @return 结果
     */
    public int deleteAppVersionByIds(Integer[] ids);

    /**
     * 删除应用版本发布信息
     * 
     * @param id 应用版本发布ID
     * @return 结果
     */
    public int deleteAppVersionById(Integer id);
}
