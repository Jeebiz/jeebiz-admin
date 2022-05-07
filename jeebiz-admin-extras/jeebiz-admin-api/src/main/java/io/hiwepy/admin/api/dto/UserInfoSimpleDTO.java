package io.hiwepy.admin.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserInfoSimpleDTO {
	
	/**
	 * 用户id
	 */
	@ApiModelProperty(name = "userId", value = "用户id")
	private Long userId;
	/**
	 * 用户号
	 */
	@ApiModelProperty(name = "userCode", value = "用户号")
	private String userCode;
	/**
	 * 昵称
	 */
	@ApiModelProperty(name = "nickname", value = "昵称")
	private String nickname;
	/**
	 * 头像
	 */
	@ApiModelProperty(name = "avatar", value = "头像")
	private String avatar;
	/**
	 * 性别 1男、2女
	 */
	@ApiModelProperty(name = "gender", value = "性别（ 1：男、2：女）")
	private Integer gender;
	/**
	 * 用户年龄
	 */
	@ApiModelProperty(name = "age", value = "用户年龄")
	private int age;
	/**
	 * 经验
	 */
	@ApiModelProperty(name = "experience", value = "经验")
	private Long experience = 0L;
	/**
	 * 金豆
	 */
	@ApiModelProperty(name = "coin", value = "金豆")
   private Long coin = 0L;
}
