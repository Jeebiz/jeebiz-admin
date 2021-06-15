/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.sms.aliyun;

import java.util.Map;

import org.springframework.boot.context.properties.PropertyMapper;

import com.alibaba.cloud.spring.boot.sms.SmsServiceImpl;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.dysmsapi.model.v20170525.SendBatchSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendBatchSmsResponse;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;

public class AliyunSmsTemplate {
	
	private SmsServiceImpl client;
	private AliyunSmsProperties smsProperties;
	
	public AliyunSmsTemplate(SmsServiceImpl client, AliyunSmsProperties smsProperties) {
		this.client = client;
		this.smsProperties = smsProperties;
	}

	public SendSmsResponse send(String phoneNumber, String templateCode, Map<String, Object> templateParams) throws ClientException {
		return this.send(phoneNumber, templateCode, templateParams, 0);
	}

	public SendSmsResponse send(String phoneNumber, String templateCode, Map<String, Object> templateParams, int signIdx) throws ClientException {
		
		// 1、实例化一个请求对象，根据调用的接口和实际情况
		SendSmsRequest req = new SendSmsRequest();
		
		// 2、待发送手机号（必填），采用 e.164 标准，+[国家或地区码][手机号] 例如+8613711112222， 其中前面有一个+号，86为国家码，13711112222为手机号
		req.setPhoneNumbers(phoneNumber);
		
		// 3、短信模板 Code（必填）: 必须填写已审核通过的模板 Code，可在短信控制台中找到 */
		req.setTemplateCode(templateCode);
		
		// 4、短信签名（必填）-可在短信控制台中找到
		req.setSignName(smsProperties.getSigns()[signIdx]);
		
        // 5、模板中的变量替换JSON串（可选）,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为 {\"name\":\"Tom\", \"code\":\"123\"}
		req.setTemplateParam(JSONObject.toJSONString(templateParams));

        //选填-上行短信扩展码(无特殊需求用户请忽略此字段)
        //request.setSmsUpExtendCode("90997");

        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
		req.setOutId("yourOutId");

		return this.sendSms(req);
	}
	
	public SendBatchSmsResponse send(String[] phoneNumbers, String templateCode, Map<String, Object> templateParams) throws ClientException{
		
		// 1、实例化一个请求对象，根据调用的接口和实际情况
		SendBatchSmsRequest req = new SendBatchSmsRequest();

		// 2、短信接收号码，JSON格式，批量上限为100个手机号码，批量调用相对于单条调用及时性稍有延迟，验证码类型的短信推荐使用单条调用的方式
		req.setPhoneNumberJson(JSONObject.toJSONString(phoneNumbers));
				
		// 3、短信模板CODE。请在控制台模板管理页面模板CODE一列查看
		req.setTemplateCode(templateCode);

		// 4、短信签名，JSON格式
		req.setSignNameJson(JSONObject.toJSONString(smsProperties.getSigns()));
		
		// 5、短信模板变量替换JSON串（可选），友情提示：如果JSON中需要带换行符，请参照标准的JSON协议。
		req.setTemplateParamJson(JSONObject.toJSONString(templateParams));
		
		// 上行短信扩展码，JSON格式，无特殊需要此字段的用户请忽略此字段
		//req.setSmsUpExtendCodeJson(smsUpExtendCodeJson);
		
		return this.sendBatchSms(req);
	}
	
	/*
	 * 填充请求参数
	 * https://help.aliyun.com/document_detail/55496.html?spm=a2c4g.11186623.6.672.d4b62c78YvfYt3
	 * https://help.aliyun.com/document_detail/101414.html?spm=a2c4g.11186623.6.625.6bad202a82LXQx
	 */
	public SendSmsResponse sendSms(SendSmsRequest req) throws ClientException {
		
		// 批量设置参数
		PropertyMapper map = PropertyMapper.get().alwaysApplyingWhenNonNull();
		
		map.from(smsProperties.getAcceptFormat()).to(req::setSysAcceptFormat);
		
		// 3、国际/港澳台短信 senderid: 国内短信填空，默认未开通，如需开通请联系 [sms helper]
		map.from(smsProperties.getConnectTimeout()).to(req::setSysConnectTimeout);

		/* 4、用户的 session 内容: 可以携带用户侧 ID 等上下文信息，server 会原样返回 */
		map.from(smsProperties.getReadTimeout()).to(req::setSysReadTimeout);
		
		/* 5、短信码号扩展号: 默认未开通，如需开通请联系 [sms helper] */
		map.from(smsProperties.getEncoding()).to(req::setSysEncoding);
		
		/*
		 * 通过 client 对象调用 SendSms 方法发起请求。注意请求方法名与请求对象是对应的 返回的 res 是一个 SendSmsResponse
		 * 类的实例，与请求对象对应
		 */
		return client.sendSmsRequest(req);
	}
	
	/*
	 * 填充请求参数
	 * https://help.aliyun.com/document_detail/102364.html?spm=a2c4g.11186623.6.624.63ee56e0YcYwoH
	 */
	public SendBatchSmsResponse sendBatchSms(SendBatchSmsRequest req) throws ClientException {
		
		// 批量设置参数
		PropertyMapper map = PropertyMapper.get().alwaysApplyingWhenNonNull();
		
		map.from(smsProperties.getAcceptFormat()).to(req::setSysAcceptFormat);
		
		/* 3、国际/港澳台短信 senderid: 国内短信填空，默认未开通，如需开通请联系 [sms helper] */
		map.from(smsProperties.getConnectTimeout()).to(req::setSysConnectTimeout);
	
		/* 4、用户的 session 内容: 可以携带用户侧 ID 等上下文信息，server 会原样返回 */
		map.from(smsProperties.getReadTimeout()).to(req::setSysReadTimeout);
		
		/* 5、短信码号扩展号: 默认未开通，如需开通请联系 [sms helper] */
		map.from(smsProperties.getEncoding()).to(req::setSysEncoding);
		
		/*
		 * 通过 client 对象调用 SendSms 方法发起请求。注意请求方法名与请求对象是对应的 返回的 res 是一个 SendSmsResponse
		 * 类的实例，与请求对象对应
		 */
		return client.sendSmsBatchRequest(req);
	}
	
}
