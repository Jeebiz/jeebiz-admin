/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.sequence.web.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.jeebiz.boot.api.ApiRestResponse;
import net.jeebiz.boot.api.sequence.Sequence;

@Api(tags = "ID生成器：雪花模型ID生成器（Ok）")
@RestController
@RequestMapping("sequence")
public class SequenceController {
	
	@Autowired
	protected Sequence sequence;
	
	@ApiOperation(value = "下一个ID", notes = "获取下一个ID序号")
	@GetMapping("next")
	public ApiRestResponse<Long> uid() {
		return ApiRestResponse.success(this.sequence.nextId());
	}
	
}
