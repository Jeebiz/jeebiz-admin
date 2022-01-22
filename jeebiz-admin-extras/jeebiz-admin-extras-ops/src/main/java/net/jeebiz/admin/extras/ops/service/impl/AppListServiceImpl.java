package net.jeebiz.admin.extras.ops.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.jeebiz.admin.extras.ops.dao.AppListMapper;
import net.jeebiz.admin.extras.ops.dao.entities.AppList;
import net.jeebiz.admin.extras.ops.service.IAppListService;

/**
 * 应用信息Service业务层处理
 */
@Service
public class AppListServiceImpl implements IAppListService
{
    @Autowired
    private AppListMapper appListMapper;

    /**
     * 查询应用信息
     * 
     * @param appId 应用信息ID
     * @return 应用信息
     */
    @Override
    public AppList selectAppListById(Integer appId)
    {
        return appListMapper.selectAppListById(appId);
    }

    /**
     * 查询应用信息列表
     * 
     * @param appList 应用信息
     * @return 应用信息
     */
    @Override
    public List<AppList> selectAppListList(AppList appList)
    {
        return appListMapper.selectAppListList(appList);
    }

    /**
     * 新增应用信息
     * 
     * @param appList 应用信息
     * @return 结果
     */
    @Override
    public int insertAppList(AppList appList)
    {
        appList.setCreateTime(LocalDateTime.now());
        return appListMapper.insertAppList(appList);
    }

    /**
     * 修改应用信息
     * 
     * @param appList 应用信息
     * @return 结果
     */
    @Override
    public int updateAppList(AppList appList)
    {
        appList.setModifyTime(LocalDateTime.now());
        return appListMapper.updateAppList(appList);
    }

    /**
     * 批量删除应用信息
     * 
     * @param appIds 需要删除的应用信息ID
     * @return 结果
     */
    @Override
    public int deleteAppListByIds(Integer[] appIds)
    {
        return appListMapper.deleteAppListByIds(appIds);
    }

    /**
     * 删除应用信息信息
     * 
     * @param appId 应用信息ID
     * @return 结果
     */
    @Override
    public int deleteAppListById(Integer appId)
    {
        return appListMapper.deleteAppListById(appId);
    }
}
