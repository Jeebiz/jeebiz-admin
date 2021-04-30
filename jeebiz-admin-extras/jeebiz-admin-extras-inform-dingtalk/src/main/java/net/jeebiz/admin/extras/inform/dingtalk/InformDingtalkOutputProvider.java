/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.inform.dingtalk;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiMessageCorpconversationAsyncsendV2Request;
import com.dingtalk.api.request.OapiMessageSendToConversationRequest;
import com.dingtalk.api.response.OapiMessageCorpconversationAsyncsendV2Response;
import com.dingtalk.api.response.OapiMessageSendToConversationResponse;
import com.dingtalk.spring.boot.DingTalkTemplate;
import com.taobao.api.ApiException;

import net.jeebiz.admin.extras.inform.setup.InformProvider;
import net.jeebiz.admin.extras.inform.setup.provider.InformOutputProvider;
import net.jeebiz.admin.extras.inform.web.dto.InformRecordDTO;

public class InformDingtalkOutputProvider implements InformOutputProvider<InformRecordDTO> {

	private DingTalkClient dingTalkClient = new DefaultDingTalkClient("https://oapi.dingtalk.com/message/send_to_conversation");

	private DingTalkTemplate dingTalkTemplate;
	
	public InformDingtalkOutputProvider(DingTalkTemplate dingTalkTemplate) {
		super();
		this.dingTalkTemplate = dingTalkTemplate;
	}

	@Override
	public InformProvider getProvider() {
		return InformProvider.DINGTALK;
	}
	
	@Override
	public boolean output(InformRecordDTO informDTO) {
		
		try {
			
			OapiMessageCorpconversationAsyncsendV2Request request = new OapiMessageCorpconversationAsyncsendV2Request();
			request.setUseridList("01376814877479");
			request.setAgentId(153858650L);
			request.setToAllUser(false);

			OapiMessageCorpconversationAsyncsendV2Request.Msg msg = new OapiMessageCorpconversationAsyncsendV2Request.Msg();
			msg.setMsgtype("text");
			msg.setText(new OapiMessageCorpconversationAsyncsendV2Request.Text());
			msg.getText().setContent("test123");
			request.setMsg(msg);
			
			/*
			msg.setMsgtype("image");
			msg.setImage(new OapiMessageCorpconversationAsyncsendV2Request.Image());
			msg.getImage().setMediaId("@lADOdvRYes0CbM0CbA");
			request.setMsg(msg);

			msg.setMsgtype("file");
			msg.setFile(new OapiMessageCorpconversationAsyncsendV2Request.File());
			msg.getFile().setMediaId("@lADOdvRYes0CbM0CbA");
			request.setMsg(msg);

			msg.setMsgtype("link");
			msg.setLink(new OapiMessageCorpconversationAsyncsendV2Request.Link());
			msg.getLink().setTitle("test");
			msg.getLink().setText("test");
			msg.getLink().setMessageUrl("test");
			msg.getLink().setPicUrl("test");
			request.setMsg(msg);

			msg.setMsgtype("markdown");
			msg.setMarkdown(new OapiMessageCorpconversationAsyncsendV2Request.Markdown());
			msg.getMarkdown().setText("##### text");
			msg.getMarkdown().setTitle("### Title");
			request.setMsg(msg);

			msg.setOa(new OapiMessageCorpconversationAsyncsendV2Request.OA());
			msg.getOa().setHead(new OapiMessageCorpconversationAsyncsendV2Request.Head());
			msg.getOa().getHead().setText("head");
			msg.getOa().setBody(new OapiMessageCorpconversationAsyncsendV2Request.Body());
			msg.getOa().getBody().setContent("xxx");
			msg.setMsgtype("oa");
			request.setMsg(msg);

			msg.setActionCard(new OapiMessageCorpconversationAsyncsendV2Request.ActionCard());
			msg.getActionCard().setTitle("xxx123411111");
			msg.getActionCard().setMarkdown("### 测试123111");
			msg.getActionCard().setSingleTitle("测试测试");
			msg.getActionCard().setSingleUrl("https://www.baidu.com");
			msg.setMsgtype("action_card");
			request.setMsg(msg);*/
			
			String accessToken = dingTalkTemplate.getAccessToken("", ""); 
			OapiMessageCorpconversationAsyncsendV2Response response = dingTalkClient.execute(request, accessToken);
			
			
		} catch (ApiException e) {
			e.printStackTrace();
		}
		
		try {
			
			OapiMessageSendToConversationRequest req = new OapiMessageSendToConversationRequest();
			req.setSender("01376814877479");
			req.setCid("14ac70d94e79377b88aa5fc75759fe84");
			OapiMessageSendToConversationRequest.Msg msg = new OapiMessageSendToConversationRequest.Msg();

			// 文本消息
			OapiMessageSendToConversationRequest.Text text = new OapiMessageSendToConversationRequest.Text();
			text.setContent("测试测试");
			msg.setText(text);
			msg.setMsgtype("text");
			req.setMsg(msg);
			
			/**
			// 图片
			OapiMessageSendToConversationRequest.Image image = new OapiMessageSendToConversationRequest.Image();
			image.setMediaId("@lADOdvRYes0CbM0CbA");
			msg.setImage(image);
			msg.setMsgtype("image");
			req.setMsg(msg);

			// 文件
			OapiMessageSendToConversationRequest.File file = new OapiMessageSendToConversationRequest.File();
			file.setMediaId("@lADOdvRYes0CbM0CbA");
			msg.setFile(file);
			msg.setMsgtype("file");
			req.setMsg(msg);


			OapiMessageSendToConversationRequest.Markdown markdown = new OapiMessageSendToConversationRequest.Markdown();
			markdown.setText("# 这是支持markdown的文本 \\n## 标题2  \\n* 列表1 \\n![alt 啊](https://gw.alipayobjects.com/zos/skylark-tools/public/files/b424a1af2f0766f39d4a7df52ebe0083.png)");
			markdown.setTitle("首屏会话透出的展示内容");
			msg.setMarkdown(markdown);
			msg.setMsgtype("markdown");
			req.setMsg(msg);


			OapiMessageSendToConversationRequest.ActionCard actionCard = new OapiMessageSendToConversationRequest.ActionCard();
			actionCard.setTitle("是透出到会话列表和通知的文案");
			actionCard.setMarkdown("持markdown格式的正文内");
			actionCard.setSingleTitle("查看详情");
			actionCard.setSingleUrl("https://open.dingtalk.com");
			msg.setActionCard(actionCard);
			msg.setMsgtype("action_card");
			req.setMsg(msg);

			// link消息
			OapiMessageSendToConversationRequest.Link link = new OapiMessageSendToConversationRequest.Link();
			link.setMessageUrl("https://www.baidu.com/");
			link.setPicUrl("@lADOdvRYes0CbM0CbA");
			link.setText("步扬测试");
			link.setTitle("oapi");
			msg.setLink(link);
			msg.setMsgtype("link");
			req.setMsg(msg);
			*/
			
			String accessToken = dingTalkTemplate.getAccessToken("", ""); 
			OapiMessageSendToConversationResponse  response = dingTalkClient.execute(req, accessToken);
			/**
			 * 	返回说明：
			 * 	接收者可以是单聊接收者或者群聊会话里的接收者，如果接收者是当前接口调用所使用的企业的员工，则是有效接收者。
			 * 	接口返回所有有效接收者的userId。
			 * 	非有效接收者是收不到消息的。
			 * {
				    "errcode": 0,
				    "errmsg": "ok",
				    "receiver": "Userid1|Userid2"
				}
			 */
			
			
		} catch (ApiException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
}
