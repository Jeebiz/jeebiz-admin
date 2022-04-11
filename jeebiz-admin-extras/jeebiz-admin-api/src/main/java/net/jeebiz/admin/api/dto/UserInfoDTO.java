package net.jeebiz.admin.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserInfoDTO {

//    public static final long serialVersionUID = 1L;

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
	@ApiModelProperty(name = "gender", value = "性别 1男、2女")
	private Integer gender;
	/**
	 * 生日
	 */
	@ApiModelProperty(name = "birthday", value = "生日")
	private Long birthday;
	/**
	 * 用户年龄
	 */
	@ApiModelProperty(name = "age", value = "用户年龄")
	private int age;
	/**
	 * 照片墙 json数组
	 */
	@ApiModelProperty(name = "pictureWall", value = "照片墙 json数组")
	private String pictureWall;
	/**
	 * 等级字段，默认1
	 */
	@ApiModelProperty(name = "level", value = "等级字段，默认1")
	private Integer level;
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
	/**
	 * 签名
	 */
	@ApiModelProperty(name = "signature", value = "签名")
	private String signature = "";
	/**
	 * 用户状态 默认1（正常） 0异常
	 */
	@ApiModelProperty(name = "status", value = "用户状态 默认1（正常） 0异常")
	private Integer status;
	/**
	 * 手机号
	 */
	@ApiModelProperty(name = "mobile", value = "手机号")
	private String mobile;
	/**
	 * 注册日期
	 */
	private String createTime;

}
