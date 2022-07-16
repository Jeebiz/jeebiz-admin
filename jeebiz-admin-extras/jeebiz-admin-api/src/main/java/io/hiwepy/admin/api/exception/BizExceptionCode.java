package io.hiwepy.admin.api.exception;

import io.hiwepy.boot.api.ApiRestResponse;
import io.hiwepy.boot.api.Constants;
import io.hiwepy.boot.api.CustomApiCode;
import io.hiwepy.boot.api.exception.BizRuntimeException;
import org.springframework.biz.context.NestedMessageSource;
import org.springframework.biz.utils.SpringContextUtils;
import org.springframework.context.i18n.LocaleContextHolder;

/**
 * 1、通用异常参数枚举
 * a、国际化key 是大写的字符 _ 拼接，统一使用小写的如，api.not.start
 * b、变量按服务区分，统一加上服务名， 比如 third.api.fail 就是 third 服务的，user.info.save.fail 就表示 user 服务的
 * c、编码按服务进行分段，比如
 * common：10000 - 20000，
 * user：20000+
 */
public enum BizExceptionCode implements CustomApiCode {

	// common

	SIGN_MISSING("1403", 1403, "Signature information missing", "签名信息缺失"),

	SUCCESS_V1("SUCCESS", 200, "success", "SUCCESS"),
	FAILED("FAILED", 1000, "failed", "失败"),
	REPEATED("REPEATED", 2000, "repeated submit", "repeated submit"),
	SYSTEM_ERROR("system.error", 9999, "system error", "流量过大系统开小差啦，请尝试重新发起"),
	REMOTE_INVOKE_ERROR("remote.invoke.error", 9998, "remoteInvokeError", "远程调用失败"),
	MESSAGE_KEY_IS_NULL("message.key.is.null", 9997, "messageKeyIsNull", "messageKey不能为空"),
	DATABASE_FILTERS_EXISTS("database.filters.exists", 9996, "", ""),
	PARAM_NOT_EXISTS("param.not.exists", 9995, "", ""),

	// 短信相关
	SMS_SEND_REGION_REQUIRED("sms.send.region.required", 1502, "sms send region required", "请选择国家或地区"),
	SMS_PHONE_ERROR("sms.phone.error", 1502, "sms send region required", "手机号码不合法"),
	SMS_PHONE_MAX_ERROR("sms.phone.max.error", 1504, "phone is error", "验证码发送次数已超过上限"),
	SMS_ERROR("sms.send.backlist.limit", 1505, "sms error", "验证码发送失败请重试"),
	SMS_CODE_ERROR("sms.check.fail", 1506, "code is error", "验证码验证失败"),

	DEVICE_BAN("login.device.ban", 20017, "Your device has been banned from logging in, please contact customer service",
			"该设备已封禁，请联系客服"),
	SIGN_EXPIRED("login.token.expired", 20018, "Sign expired,Please log in again", "签名过期"),

	DATA_NOT_EXISTS("data.not.exists", 8888, "未找到数据", "未找到数据"),
	
	// 用户相关 30000-40000
	SAVE_USER_INFO_FAIL("user.info.save.fail", 30000, "", "保存用户信息失败"),
	USER_INFO_IS_NOT_EXSIT("user.info.is.not.exsit", 30001, "", "用户信息不存在"),
	SFZJH_ALREADY_EXIST("user.sfzjh.is.already.exsit", 30002, "身份证号已存在", "身份证号已存在"),

	//树节点相关 40000-41000
	PARENT_NODE_NOT_EXISTS("parent.node.not.exists", 40000, "父级节点不存在", "父级节点不存在"),
    TREE_ALREADY_EXIST("tree.already.exist", 40001, "树已存在", "树已存在"),

    //部门相关 41000-42000
    DEPT_NOT_EXISTS("dept.not.exists", 41000, "部门不存在", "部门不存在"),

	//角色相关 42000-43000
	ROLE_NOT_EXISTS("role.not.exists", 42000, "角色不存在", "角色不存在"),
	ROLE_USER_NOT_EXISTS("role.user.not.exists", 42001, "用户不存在", "用户不存在"),
	ROLE_USER_EXPORT_FAIL("role.user.export.fail", 42002, "导出用户失败", "导出用户失败"),

	 //年级相关 51
	NJ_NOT_EXISTS("NJ.not.exists", 51000, "年级不存在", "年级不存在"),
	NJ_ERROR("nj.error", 51001, "年级错误", "年级错误"),
	NJ_TIME_ERROR("nj.time.error", 51001, "入学时间错误", "入学时间错误"),
	NJ_ALREADY_EXIST("nj.already.exist", 51002, "年级已存在", "年级已存在"),
	;

	/**
	 * 国际化Key
	 */
	private String i18nKey;

	/**
	 * 错误编号
	 */
	private int errorCode;

	/**
	 * 错误信息
	 */
	private String errorMsg;

	/**
	 * 错误描述
	 */
	private String desc;


	private BizExceptionCode(String i18nKey, int errorCode, String errorMsg, String desc) {
		this.i18nKey = i18nKey;
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
		this.desc = desc;
	}

	public void throwException() {
		throw this.asException();
	}

	public static void throwByErrorcode(Integer errorCode) {
		getByErrorcode(errorCode).throwException();
	}

	public static BizExceptionCode getByErrorcode(Integer errorCode) {
		if (errorCode == null) {
			return BizExceptionCode.SYSTEM_ERROR;
		}
		BizExceptionCode[] errorCodes = values();

		for (BizExceptionCode bizErrorCode : errorCodes) {
			if (errorCode.equals(bizErrorCode.getErrorCode())) {
				return bizErrorCode;
			}
		}
		return BizExceptionCode.SYSTEM_ERROR;
	}

	public static CustomApiCode getApiCodeByErrorcode(Integer errorCode) {
		return getByErrorcode(errorCode);
	}

	public BizRuntimeException asException() {
		return new BizRuntimeException(this.getErrorCode(), this.getI18nKey(), this.getErrorMsg());
	}

	/**
	 * 1、根据异常枚举构建接口返回对象
	 * @param args 额外参数
	 * @return 接口返回对象
	 */
	public <T> ApiRestResponse<T> asResponse(Object... args) {
		NestedMessageSource messageSource = SpringContextUtils.getContext().getApplicationContext().getBean(NestedMessageSource.class);
		return this.asResponse(messageSource, args);
	}

	/**
	 * 1、根据异常枚举构建接口返回对象
	 * @param messageSource I18N 国际化资源对象
	 * @param args 额外参数
	 * @return 接口返回对象
	 */
	public <T> ApiRestResponse<T> asResponse(NestedMessageSource messageSource, Object... args) {
		this.errorMsg = messageSource.getMessage(this.getI18nKey(), args, this.getErrorMsg(), LocaleContextHolder.getLocale());
		return ApiRestResponse.of(this);
	}

	public String getI18nKey() {
		return i18nKey;
	}

	public String getDesc() {
		return desc;
	}

	public Integer getErrorCode() {
		return errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	@Override
	public int getCode() {
		return errorCode;
	}

	@Override
	public String getReason() {
		return errorMsg;
	}

	@Override
	public String getStatus() {
		return Constants.RT_FAIL;
	}

}
