package net.jeebiz.admin.extras.ops.dao.entities;

import lombok.Data;
import net.jeebiz.boot.api.dao.entities.BaseEntity;

/**
 * 应用信息对象
 */
@Data
public class AppList extends BaseEntity<AppList>
{

    /** 应用ID */
    private Integer appId;

    /** 应用名称 */
    private String appName;

    /** 应用包名 */
    private String appPackage;

    /** 应用描述 */
    private String appDesc;

}
