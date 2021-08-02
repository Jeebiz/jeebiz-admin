package net.jeebiz.admin.extras.ops.dao;

import java.util.List;

import net.jeebiz.admin.extras.ops.dao.entities.AppChannel;

/**
 * 应用渠道信息Mapper接口
 */
public interface AppChannelMapper 
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
     * @param AppChannel 应用渠道信息
     * @return 应用渠道信息集合
     */
    public List<AppChannel> selectAppChannelList(AppChannel AppChannel);

    /**
     * 新增应用渠道信息
     * 
     * @param AppChannel 应用渠道信息
     * @return 结果
     */
    public int insertAppChannel(AppChannel AppChannel);

    /**
     * 修改应用渠道信息
     * 
     * @param AppChannel 应用渠道信息
     * @return 结果
     */
    public int updateAppChannel(AppChannel AppChannel);

    /**
     * 删除应用渠道信息
     * 
     * @param id 应用渠道信息ID
     * @return 结果
     */
    public int deleteAppChannelById(Integer id);

    /**
     * 批量删除应用渠道信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteAppChannelByIds(Integer[] ids);
}
