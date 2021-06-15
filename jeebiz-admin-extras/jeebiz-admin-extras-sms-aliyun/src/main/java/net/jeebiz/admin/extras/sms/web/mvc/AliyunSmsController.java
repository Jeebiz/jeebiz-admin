package net.jeebiz.admin.extras.sms.web.mvc;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import net.jeebiz.admin.extras.sms.aliyun.AliyunSmsOperationTemplate;
import net.jeebiz.admin.extras.sms.web.dto.SmsCheckDTO;
import net.jeebiz.admin.extras.sms.web.dto.SmsSendDTO;
import net.jeebiz.boot.api.ApiRestResponse;
import net.jeebiz.boot.api.XHeaders;
import net.jeebiz.boot.api.annotation.ApiIdempotent;
import net.jeebiz.boot.api.web.BaseMapperController;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = "短信消息（阿里云）：发送和校验")
@RestController
@Validated
@RequestMapping("/aliyun/sms/")
public class AliyunSmsController extends BaseMapperController {

	@Autowired
	private AliyunSmsOperationTemplate smsOperationTemplate;
	
	@ApiOperation(value = "短信发送", notes = "发送验证码短信")
	@ApiIdempotent(value = "#{sendDTO.countryCode}#{sendDTO.phone}-#{sendDTO.type}", spel = true, expireMillis = 50000)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "body", name = "sendDTO", value = "发送短信DTO", dataType = "SmsSendDTO") 
	})
	@PostMapping("send")
	public ApiRestResponse<String> send(@Validated @RequestBody SmsSendDTO sendDTO, @ApiIgnore HttpServletRequest request) {
		// 1、调用公共代码发送短信
		String appId = request.getHeader(XHeaders.X_APP_ID);
		boolean rt = getSmsOperationTemplate().send(appId, sendDTO.getPhone(), sendDTO.getType(), sendDTO.getCountryCode());
		if(rt) {
			return success("sms.send.success");
		}
		return fail("sms.send.fail");
	}
	
	@ApiOperation(value = "短信校验", notes = "验证手机号码收到的验证码")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "body", name = "checkDTO", value = "短信验证DTO", dataType = "SmsCheckDTO") 
	})
    @PostMapping("check")
    public ApiRestResponse<Boolean> check(@Validated @RequestBody SmsCheckDTO checkDTO) {
		// 1、调用公共代码验证短信
		Boolean rt = getSmsOperationTemplate().check(checkDTO.getPhone(), checkDTO.getType(), checkDTO.getCountryCode(), checkDTO.getVcode());
		if(rt) {
			return ApiRestResponse.success(rt);
		}
    	return ApiRestResponse.fail(Boolean.FALSE);
    }
	
	public AliyunSmsOperationTemplate getSmsOperationTemplate() {
		return smsOperationTemplate;
	}
	
}
