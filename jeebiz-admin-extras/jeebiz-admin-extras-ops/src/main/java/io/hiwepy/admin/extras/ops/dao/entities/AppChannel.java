package io.hiwepy.admin.extras.ops.dao.entities;

import lombok.Data;
import io.hiwepy.boot.api.dao.entities.BaseEntity;

/**
 * 应用渠道信息对象
 */
@Data
public class AppChannel extends BaseEntity<AppChannel>
{

    /** 应用渠道ID */
    private Integer id;

    /** 应用ID */
    private String appId;

    /** 应用ID */
    private String appName;

    /** 渠道编码 */
    private String channelCode;

    /** 渠道描述 */
    private String channelDesc;

    /** 最新版本 */
    private String appVersion;

    /** 渠道包下载地址 */
    private String downloadUrl;

}
