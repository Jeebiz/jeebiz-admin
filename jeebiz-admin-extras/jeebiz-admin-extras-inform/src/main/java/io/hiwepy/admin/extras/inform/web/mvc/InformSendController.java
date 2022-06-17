/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package io.hiwepy.admin.extras.inform.web.mvc;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import io.hiwepy.admin.extras.inform.dao.entities.InformRecordEntity;
import io.hiwepy.admin.extras.inform.service.IInformRecordService;
import io.hiwepy.admin.extras.inform.service.IInformSendService;
import io.hiwepy.admin.extras.inform.setup.Constants;
import io.hiwepy.admin.extras.inform.web.dto.InformRecordDTO;
import io.hiwepy.admin.extras.inform.web.dto.InformRecordPaginationDTO;
import io.hiwepy.admin.extras.inform.web.dto.InformRecordSendDTO;
import io.hiwepy.admin.extras.inform.web.dto.InformRecordStatsDTO;
import io.hiwepy.boot.api.ApiRestResponse;
import io.hiwepy.boot.api.annotation.BusinessLog;
import io.hiwepy.boot.api.annotation.BusinessType;
import io.hiwepy.boot.api.web.BaseMapperController;
import io.hiwepy.boot.api.web.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.biz.authz.principal.ShiroPrincipal;
import org.apache.shiro.biz.utils.SubjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Api(tags = "消息通知：发送站内消息")
@RestController
@RequestMapping("/inform/")
public class InformSendController extends BaseMapperController {
	
	@Autowired
	private IInformSendService informSendService;

	@ApiOperation(value = "发送消息通知", notes = "发送消息通知")
	@PostMapping("send")
	@ResponseBody
	public ApiRestResponse<String> send(@Valid @RequestBody InformRecordSendDTO sendDTO,
										@ApiIgnore HttpServletRequest request) throws Exception {
		return getInformSendService().send(sendDTO, request);
	}

	public IInformSendService getInformSendService() {
		return informSendService;
	}
}
