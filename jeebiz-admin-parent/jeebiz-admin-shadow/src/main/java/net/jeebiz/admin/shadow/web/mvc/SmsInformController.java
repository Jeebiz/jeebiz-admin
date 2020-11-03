package net.jeebiz.admin.shadow.web.mvc;

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
import net.jeebiz.admin.shadow.setup.sms.aliyun.AliyunSmsOperationTemplate;
import net.jeebiz.admin.shadow.web.vo.SmsCheckDTO;
import net.jeebiz.admin.shadow.web.vo.SmsSendDTO;
import net.jeebiz.boot.api.ApiRestResponse;
import net.jeebiz.boot.api.web.BaseMapperController;

@Api(tags = "短信消息：发送和校验")
@RestController
@Validated
@RequestMapping("/inform/sms/")
public class SmsInformController extends BaseMapperController {

	@Autowired
	private AliyunSmsOperationTemplate smsOperationTemplate;
	
	@ApiOperation(value = "短信发送", notes = "发送验证码短信")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "body", name = "sendDTO", value = "发送短信DTO", dataType = "SmsSendDTO") 
	})
	@PostMapping("send")
	public ApiRestResponse<String> send(@Validated @RequestBody SmsSendDTO sendDTO) {
		// 1、调用公共代码发送短信
		boolean rt = getSmsOperationTemplate().send(sendDTO.getPhone(), sendDTO.getType(), sendDTO.getCountryCode());
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
