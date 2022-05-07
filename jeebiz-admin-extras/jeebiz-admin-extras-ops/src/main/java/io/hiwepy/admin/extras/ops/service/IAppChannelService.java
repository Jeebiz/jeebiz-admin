package io.hiwepy.admin.extras.ops.service;

import java.util.List;

import io.hiwepy.admin.extras.ops.dao.entities.AppChannel;

/**
 * 应用渠道信息Service接口
 */
public interface IAppChannelService 
{
    /**
     * 查询应用渠道信息
     * 
     * @param id 应用渠道信息ID
     * @return 应用渠道信息
     */
    public AppChannel selectAppChannelById(Integer id);

    /**
     * 查询应用渠道信息列表
     * 
     * @param appChannel 应用渠道信息
     * @return 应用渠道信息集合
     */
    public List<AppChannel> selectAppChannelList(AppChannel appChannel);

    /**
     * 新增应用渠道信息
     * 
     * @param appChannel 应用渠道信息
     * @return 结果
     */
    public int insertAppChannel(AppChannel appChannel);

    /**
     * 修改应用渠道信息
     * 
     * @param appChannel 应用渠道信息
     * @return 结果
     */
    public int updateAppChannel(AppChannel appChannel);

    /**
     * 批量删除应用渠道信息
     * 
     * @param ids 需要删除的应用渠道信息ID
     * @return 结果
     */
    public int deleteAppChannelByIds(Integer[] ids);

    /**
     * 删除应用渠道信息信息
     * 
     * @param id 应用渠道信息ID
     * @return 结果
     */
    public int deleteAppChannelById(Integer id);
}
