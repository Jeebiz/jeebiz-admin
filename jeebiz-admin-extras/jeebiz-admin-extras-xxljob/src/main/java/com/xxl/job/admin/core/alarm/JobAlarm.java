package com.xxl.job.admin.core.alarm;

import io.hiwepy.admin.extras.xxljob.dao.entities.XxlJobInfo;
import io.hiwepy.admin.extras.xxljob.dao.entities.XxlJobLog;

/**
 * @author xuxueli 2020-01-19
 */
public interface JobAlarm {

    /**
     * job alarm
     *
     * @param info
     * @param jobLog
     * @return
     */
    public boolean doAlarm(XxlJobInfo info, XxlJobLog jobLog);

}
