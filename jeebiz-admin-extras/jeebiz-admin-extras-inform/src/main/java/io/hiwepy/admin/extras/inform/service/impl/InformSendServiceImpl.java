/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package io.hiwepy.admin.extras.inform.service.impl;

import com.google.common.collect.Maps;
import io.hiwepy.admin.extras.inform.bo.InformSendBO;
import io.hiwepy.admin.extras.inform.bo.InformSendResult;
import io.hiwepy.admin.extras.inform.dao.InformTemplateMapper;
import io.hiwepy.admin.extras.inform.dao.entities.InformTemplateEntity;
import io.hiwepy.admin.extras.inform.service.IInformSendService;
import io.hiwepy.admin.extras.inform.service.IInformTemplateService;
import io.hiwepy.admin.extras.inform.strategy.InformSendStrategyRouter;
import io.hiwepy.admin.extras.inform.web.dto.InformRecordSendDTO;
import io.hiwepy.boot.api.ApiRestResponse;
import io.hiwepy.boot.api.service.BaseServiceImpl;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.biz.utils.WebUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

@Service
public class InformSendServiceImpl extends BaseServiceImpl<InformTemplateMapper, InformTemplateEntity> implements IInformSendService {

	@Autowired
	private InformSendStrategyRouter informSendStrategyRouter;
	@Autowired
	private IInformTemplateService informTemplateService;

	@Override
	public ApiRestResponse<String> send(InformRecordSendDTO sendDTO, HttpServletRequest request) throws Exception {

		// 1、根据参数查询对应的模板是否存
		InformTemplateEntity templateEntity = informTemplateService.getById(sendDTO.getTemplateId());
		if(Objects.isNull(templateEntity)){
			return ApiRestResponse.fail(getMessage("inform.template.get.empty"));
		}

		InformSendBO sendBO = InformSendBO.builder()
				//.appId(appId)
				//.appChannel(appChannel)
				//.appVer(appVersion)
				//.bizId(sendDTO.getBizId())
				.channel(templateEntity.getChannel())
				.ipAddress(WebUtils.getRemoteAddr(request))
				.title(templateEntity.getTitle())
				.signature(templateEntity.getSignature())
				.content(templateEntity.getContent())
				.templateId(templateEntity.getTemplateId())
				.templateParams(MapUtils.isEmpty(sendDTO.getTemplateParams()) ? Maps.newHashMap() : sendDTO.getTemplateParams())
				.payload(sendDTO.getPayload())
				.toList(sendDTO.getToList())
				.build();

		// 1、调用公共代码发送短信
		InformSendResult sendRt = informSendStrategyRouter.routeFor(sendBO).send(sendBO, InformSendResult.class);

		return null;
	}

}
