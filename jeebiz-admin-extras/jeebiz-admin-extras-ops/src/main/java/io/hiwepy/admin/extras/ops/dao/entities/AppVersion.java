package io.hiwepy.admin.extras.ops.dao.entities;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import io.hiwepy.boot.api.dao.entities.BaseEntity;

/**
 * 应用版本发布对象 
 */
@Data
public class AppVersion extends BaseEntity
{

    /** 应用版本ID */
    private Integer id;

    /** 应用ID */
    //@Excel(name = "应用ID")
    private String appId;

    /** 应用ID */
    //@Excel(name = "应用名称")
    private String appName;

    /** 渠道编码 */
    //@Excel(name = "渠道编码")
    private String appChannel;

    /** 版本号 */
    //@Excel(name = "版本号")
    private String appVersion;

    /** 母包下载链接 */
    //@Excel(name = "母包下载链接")
    private String linkUrl;

    /** 版本描述 */
    //@Excel(name = "版本描述")
    private String upgradeDesc;

    /** 是否强制更新 */
    //@Excel(name = "是否强制更新")
    private Integer forceUpgrade;

    /** 更新推送时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    //@Excel(name = "更新推送时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date upgradeTime;

    /** 是否发布（0：否，1：是） */
    //@Excel(name = "是否发布", readConverterExp = "0=：否，1：是")
    private Integer status;

}
