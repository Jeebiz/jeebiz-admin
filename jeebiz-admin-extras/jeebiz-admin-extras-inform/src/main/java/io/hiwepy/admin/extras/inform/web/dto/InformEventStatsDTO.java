/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package io.hiwepy.admin.extras.inform.web.dto;

import io.hiwepy.admin.extras.inform.emums.InformSendChannel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "InformEventStatsDTO", description = "消息事件统计DTO")
@Data
public class InformEventStatsDTO {

    /**
     * 消息通知事件行为
     */
    @ApiModelProperty(value = "消息通知事件行为")
    private InformSendChannel channel;
    /**
     * 消息通知事件已发消息总数
     */
    @ApiModelProperty(value = "消息通知事件已发消息总数")
    private Integer total;
    /**
     * 消息通知事件已发消息未读总数
     */
    @ApiModelProperty(value = "消息通知事件已发消息未读总数")
    private Integer unread;

}
