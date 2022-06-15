/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package io.hiwepy.admin.extras.inform.service;

import io.hiwepy.admin.extras.inform.web.dto.InformRecordSendDTO;
import io.hiwepy.boot.api.ApiRestResponse;

public interface IInformSendService {

	ApiRestResponse<String> send(InformRecordSendDTO sendDTO) throws Exception;
	
}
