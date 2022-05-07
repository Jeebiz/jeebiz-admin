package io.hiwepy.admin.extras.ops.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.hiwepy.admin.extras.ops.dao.AppChannelMapper;
import io.hiwepy.admin.extras.ops.dao.entities.AppChannel;
import io.hiwepy.admin.extras.ops.service.IAppChannelService;

/**
 * 应用渠道信息Service业务层处理
 */
@Service
public class AppChannelServiceImpl implements IAppChannelService
{
    @Autowired
    private AppChannelMapper appChannelMapper;

    /**
     * 查询应用渠道信息
     * 
     * @param id 应用渠道信息ID
     * @return 应用渠道信息
     */
    @Override
    public AppChannel selectAppChannelById(Integer id)
    {
        return appChannelMapper.selectAppChannelById(id);
    }

    /**
     * 查询应用渠道信息列表
     * 
     * @param appChannel 应用渠道信息
     * @return 应用渠道信息
     */
    @Override
    public List<AppChannel> selectAppChannelList(AppChannel appChannel)
    {
        return appChannelMapper.selectAppChannelList(appChannel);
    }

    /**
     * 新增应用渠道信息
     * 
     * @param appChannel 应用渠道信息
     * @return 结果
     */
    @Override
    public int insertAppChannel(AppChannel appChannel)
    {
        appChannel.setCreateTime(LocalDateTime.now());
        return appChannelMapper.insertAppChannel(appChannel);
    }

    /**
     * 修改应用渠道信息
     * 
     * @param appChannel 应用渠道信息
     * @return 结果
     */
    @Override
    public int updateAppChannel(AppChannel appChannel)
    {
        appChannel.setModifyTime(LocalDateTime.now());
        return appChannelMapper.updateAppChannel(appChannel);
    }

    /**
     * 批量删除应用渠道信息
     * 
     * @param ids 需要删除的应用渠道信息ID
     * @return 结果
     */
    @Override
    public int deleteAppChannelByIds(Integer[] ids)
    {
        return appChannelMapper.deleteAppChannelByIds(ids);
    }

    /**
     * 删除应用渠道信息信息
     * 
     * @param id 应用渠道信息ID
     * @return 结果
     */
    @Override
    public int deleteAppChannelById(Integer id)
    {
        return appChannelMapper.deleteAppChannelById(id);
    }
}
