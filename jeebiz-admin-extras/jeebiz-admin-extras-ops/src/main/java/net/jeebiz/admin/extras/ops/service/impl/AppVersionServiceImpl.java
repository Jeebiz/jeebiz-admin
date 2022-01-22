package net.jeebiz.admin.extras.ops.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.jeebiz.admin.extras.ops.dao.AppVersionMapper;
import net.jeebiz.admin.extras.ops.dao.entities.AppVersion;
import net.jeebiz.admin.extras.ops.service.IAppVersionService;

/**
 * 应用版本发布Service业务层处理
 */
@Service
public class AppVersionServiceImpl implements IAppVersionService
{
    @Autowired
    private AppVersionMapper appVersionMapper;

    /**
     * 查询应用版本发布
     * 
     * @param id 应用版本发布ID
     * @return 应用版本发布
     */
    @Override
    public AppVersion selectAppVersionById(Integer id)
    {
        return appVersionMapper.selectAppVersionById(id);
    }

    /**
     * 查询应用版本发布列表
     * 
     * @param appVersion 应用版本发布
     * @return 应用版本发布
     */
    @Override
    public List<AppVersion> selectAppVersionList(AppVersion appVersion)
    {
        return appVersionMapper.selectAppVersionList(appVersion);
    }

    /**
     * 新增应用版本发布
     * 
     * @param appVersion 应用版本发布
     * @return 结果
     */
    @Override
    public int insertAppVersion(AppVersion appVersion)
    {
        appVersion.setCreateTime(LocalDateTime.now());
        return appVersionMapper.insertAppVersion(appVersion);
    }

    /**
     * 修改应用版本发布
     * 
     * @param appVersion 应用版本发布
     * @return 结果
     */
    @Override
    public int updateAppVersion(AppVersion appVersion)
    {
        appVersion.setModifyTime(LocalDateTime.now());
        return appVersionMapper.updateAppVersion(appVersion);
    }

    /**
     * 批量删除应用版本发布
     * 
     * @param ids 需要删除的应用版本发布ID
     * @return 结果
     */
    @Override
    public int deleteAppVersionByIds(Integer[] ids)
    {
        return appVersionMapper.deleteAppVersionByIds(ids);
    }

    /**
     * 删除应用版本发布信息
     * 
     * @param id 应用版本发布ID
     * @return 结果
     */
    @Override
    public int deleteAppVersionById(Integer id)
    {
        return appVersionMapper.deleteAppVersionById(id);
    }
}
