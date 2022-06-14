/**
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved.
 */
package io.hiwepy.admin.extras.inform.web.dto;

import io.hiwepy.admin.extras.inform.emums.InformSendChannel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import lombok.Getter;
import lombok.Data;

@ApiModel(value = "InformEventDTO", description = "消息通知事件DTO")
@Data
public class InformEventDTO {

    /**
     * 消息通知事件id
     */
    @ApiModelProperty(value = "消息通知事件id")
    private String id;
    /**
     * 消息通知事件类型
     */
    @ApiModelProperty(value = "消息通知事件类型")
    private String type;

    /**
     * 消息通知事件行为
     */
    @ApiModelProperty(value = "消息通知事件行为")
    private InformSendChannel channel;

    /**
     * 消息通知事件关联模板id
     */
    @ApiModelProperty(value = "消息通知事件关联模板id")
    private String templateId;

    /**
     * 消息通知事件说明
     */
    @ApiModelProperty(value = "消息通知事件说明")
    private String intro;

    /**
     * 消息通知事件状态：（0:停用、1:启用）
     */
    @ApiModelProperty(value = "消息通知事件状态：（0:停用、1:启用）", allowableValues="0,1", example = "1")
    private Integer status;

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
    /**
     * 消息通知事件创建时间
     */
    @ApiModelProperty(value = "消息通知创建时间")
    private String time24;

}