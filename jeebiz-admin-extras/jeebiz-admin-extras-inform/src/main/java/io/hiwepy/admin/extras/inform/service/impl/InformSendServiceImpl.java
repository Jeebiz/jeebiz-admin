/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package io.hiwepy.admin.extras.inform.service.impl;

import io.hiwepy.admin.extras.inform.service.IInformSendService;
import io.hiwepy.admin.extras.inform.web.dto.InformRecordSendDTO;
import io.hiwepy.boot.api.ApiRestResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InformSendServiceImpl implements IInformSendService {


	@Override
	public ApiRestResponse<String> send(InformRecordSendDTO sendDTO) throws Exception {
		return null;
	}


}
