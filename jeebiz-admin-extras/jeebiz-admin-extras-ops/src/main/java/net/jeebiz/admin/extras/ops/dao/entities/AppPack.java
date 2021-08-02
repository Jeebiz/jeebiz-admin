package net.jeebiz.admin.extras.ops.dao.entities;

import lombok.Data;
import net.jeebiz.boot.api.dao.entities.BaseEntity;

/**
 * 应用打包信息对象
 */
@Data
public class AppPack extends BaseEntity<AppPack>
{

    /** 应用ID */
    private Integer appId;

    /** 渠道编码 */
    private String appChannel;

    /** 版本号 */
    private String appVersion;

    /** 应用下载链接 */
    private String linkUrl;

}
