/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package io.hiwepy.admin.extras.article.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ApiModel(value = "ArticleAuthorDTO", description = "用户信息参数DTO")
@Getter
@Setter
@ToString
public class ArticleAuthorDTO {

	/**
	 * 用户id
	 */
	@ApiModelProperty(name = "id", dataType = "String", value = "用户id")
	private String id;
	/**
	 * 用户别名（昵称）
	 */
	@ApiModelProperty(name = "alias", required = true, dataType = "String", value = "用户昵称")
	private String alias;
	/**
	 * 用户头像：图片路径或图标样式
	 */
	@ApiModelProperty(name = "avatar", dataType = "String", value = "用户头像：图片路径或图标样式")
	private String avatar;
	/**
	 * 用户名
	 */
	@ApiModelProperty(name = "account", required = true, dataType = "String", value = "用户名")
	private String account;
	/**
	 * 手机号码
	 */
	@ApiModelProperty(name = "phone", required = true, dataType = "String", value = "手机号码")
	private String phone;
	/**
	 * 电子邮箱
	 */
	@ApiModelProperty(name = "email", required = true, dataType = "String", value = "电子邮箱")
	private String email;
	/**
	 * 用户备注
	 */
	@ApiModelProperty(name = "remark", required = true, dataType = "String", value = "用户备注")
	private String remark;
	/**
	 * 性别：（male：男，female：女）
	 */
	@ApiModelProperty(name = "gender", required = true, dataType = "String", value = "性别：（male：男，female：女）")
	private String gender;
	/**
	 * 出生日期
	 */
	@ApiModelProperty(name = "birthday", required = true, dataType = "String", value = "出生日期")
	private String birthday;
	/**
	 * 身份证号码
	 */
	@ApiModelProperty(name = "idcard", required = true, dataType = "String", value = "身份证号码")
	private String idcard;

}
