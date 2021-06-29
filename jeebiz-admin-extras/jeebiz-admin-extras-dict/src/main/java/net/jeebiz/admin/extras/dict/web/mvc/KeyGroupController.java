/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.dict.web.mvc;

import java.util.List;

import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.biz.utils.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import net.jeebiz.admin.extras.dict.dao.entities.KeyGroupModel;
import net.jeebiz.admin.extras.dict.service.IKeyGroupService;
import net.jeebiz.admin.extras.dict.setup.Constants;
import net.jeebiz.admin.extras.dict.web.dto.KeyGroupDTO;
import net.jeebiz.admin.extras.dict.web.dto.KeyGroupNewDTO;
import net.jeebiz.admin.extras.dict.web.dto.KeyGroupPaginationDTO;
import net.jeebiz.admin.extras.dict.web.dto.KeyGroupRenewDTO;
import net.jeebiz.boot.api.ApiRestResponse;
import net.jeebiz.boot.api.annotation.AllowableValues;
import net.jeebiz.boot.api.annotation.BusinessLog;
import net.jeebiz.boot.api.annotation.BusinessType;
import net.jeebiz.boot.api.web.BaseApiController;
import net.jeebiz.boot.api.web.Result;

@Api(tags = "数据字典：基础数据分组")
@RestController
@RequestMapping("/dict/group/")
@Validated
public class KeyGroupController extends BaseApiController {
	
	@Autowired
	private IKeyGroupService keyGroupService;
	
	@ApiOperation(value = "分页查询基础数据分组", notes = "分页查询基础数据分组")
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "body", name = "paginationDTO", value = "用户信息筛选条件", dataType = "KeyGroupPaginationDTO")
	})
	@PostMapping("list")
	@RequiresPermissions("keygroup:list")
	@ResponseBody
	public Result<KeyGroupDTO> list(@Valid @RequestBody KeyGroupPaginationDTO paginationDTO){
		
		KeyGroupModel model =  getBeanMapper().map(paginationDTO, KeyGroupModel.class);
		Page<KeyGroupModel> pageResult = getKeyGroupService().getPagedList(model);
		List<KeyGroupDTO> retList = Lists.newArrayList();
		for (KeyGroupModel keygroupModel : pageResult.getRecords()) {
			retList.add(getBeanMapper().map(keygroupModel, KeyGroupDTO.class));
		}
		
		return new Result<KeyGroupDTO>(pageResult, retList);
		
	}
	
	@ApiOperation(value = "基础数据分组（下拉数据）", notes = "查询基础数据分组集合")
	@GetMapping("pairs")
	@RequiresAuthentication
	@ResponseBody
	public ApiRestResponse<List<KeyGroupDTO>> groups() throws Exception {
		List<KeyGroupModel> records = getKeyGroupService().getKeyGroupList();
		List<KeyGroupDTO> retList = Lists.newArrayList();
		for (KeyGroupModel groupModel : records) {
			retList.add(getBeanMapper().map(groupModel, KeyGroupDTO.class));
		}
		return ApiRestResponse.success(retList);
	}
	
	@ApiOperation(value = "创建基础数据分组", notes = "增加一个新的基础数据分组")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "body", name = "DTO", value = "基础数据分组传输对象", dataType = "KeyGroupNewDTO") 
	})
	@BusinessLog(module = Constants.EXTRAS_BASEDATA, business = "创建基础数据分组", opt = BusinessType.INSERT)
	@PostMapping("new")
	@RequiresPermissions("keygroup:new")
	@ResponseBody
	public ApiRestResponse<String> keygroup(@Valid @RequestBody KeyGroupNewDTO DTO) throws Exception {
		
		// 检查编码是否存在
		int ct = getKeyGroupService().getCountByCode(DTO.getKey(), null);
		if(ct > 0) {
			return fail("keygroup.new.key.conflict");
		}
		// 检查名称是否存在
		ct = getKeyGroupService().getCountByName(DTO.getValue(), null);
		if(ct > 0) {
			return fail("keygroup.new.value.conflict");
		}
		
		// 新增一条数据库配置记录
		KeyGroupModel model = getBeanMapper().map(DTO, KeyGroupModel.class);
		boolean result = getKeyGroupService().save(model);
		if(result) {
			return success("keygroup.new.success", result);
		}
		// 逻辑代码，如果发生异常将不会被执行
		return fail("keygroup.new.fail", result);
	}
	
	@ApiOperation(value = "删除基础数据分组", notes = "删除基础数据分组")
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "query", name = "ids", value = "基础数据分组id,多个用,拼接", required = true, dataType = "String")
	})
	@BusinessLog(module = Constants.EXTRAS_BASEDATA, business = "删除基础数据分组", opt = BusinessType.UPDATE)
	@GetMapping("delete")
	@RequiresPermissions("keygroup:delete")
	@ResponseBody
	public ApiRestResponse<String> delete(@RequestParam String ids) throws Exception {
		// 执行基础数据分组删除操作
		List<String> idList = Lists.newArrayList(StringUtils.tokenizeToStringArray(ids));
		boolean result = getKeyGroupService().removeByIds(idList);
		if(result) {
			return success("keygroup.delete.success", result);
		}
		// 逻辑代码，如果发生异常将不会被执行
		return fail("keygroup.delete.fail", result);
	}
	
	@ApiOperation(value = "更新基础数据分组", notes = "更新基础数据分组")
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "body", name = "DTO", value = "基础数据分组", required = true, dataType = "KeyGroupRenewDTO"),
	})
	@BusinessLog(module = Constants.EXTRAS_BASEDATA, business = "更新基础数据分组", opt = BusinessType.UPDATE)
	@PostMapping("renew")
	@RequiresPermissions("keygroup:renew")
	@ResponseBody
	public ApiRestResponse<String> renew(@Valid @RequestBody KeyGroupRenewDTO DTO) throws Exception {
		
		// 检查编码是否存在
		int ct = getKeyGroupService().getCountByCode(DTO.getKey(), DTO.getValue());
		if(ct > 0) {
			return fail("keygroup.renew.key.conflict");
		}
		// 检查名称是否存在
		ct = getKeyGroupService().getCountByName(DTO.getValue(), DTO.getValue());
		if(ct > 0) {
			return fail("keygroup.renew.value.conflict");
		}
		
		KeyGroupModel model = getBeanMapper().map(DTO, KeyGroupModel.class);
		boolean result = getKeyGroupService().updateById(model);
		if(result) {
			return success("keygroup.renew.success", result);
		}
		// 逻辑代码，如果发生异常将不会被执行
		return fail("keygroup.renew.fail", result);
	}
	
	@ApiOperation(value = "更新基础数据分组状态", notes = "更新基础数据分组状态")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "id", required = true, value = "基础数据分组id", dataType = "String"),
		@ApiImplicitParam(paramType = "query", name = "status", required = true, value = "基础数据分组状态", dataType = "String", allowableValues = "1,0")
	})
	@BusinessLog(module = Constants.EXTRAS_BASEDATA, business = "更新基础数据分组状态", opt = BusinessType.UPDATE)
	@GetMapping("status")
	@RequiresPermissions("keygroup:status")
	@ResponseBody
	public ApiRestResponse<String> status(@RequestParam String id, @AllowableValues(allows = "0,1",message = "数据状态错误") @RequestParam String status) throws Exception {
		int result = getKeyGroupService().setStatus(id, status);
		if(result == 1) {
			return success("keygroup.status.success", result);
		}
		// 逻辑代码，如果发生异常将不会被执行
		return fail("keygroup.status.fail", result);
	}
	
	@ApiOperation(value = "查询基础数据分组信息", notes = "根据id查询基础数据分组信息")
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "query", name = "id", required = true, value = "基础数据分组id", dataType = "String")
	})
	@GetMapping("detail")
	@RequiresPermissions("keygroup:detail")
	@ResponseBody
	public ApiRestResponse<KeyGroupDTO> detail(@RequestParam("id") String id) throws Exception { 
		KeyGroupModel model = getKeyGroupService().getById(id);
		if(model == null) {
			return ApiRestResponse.fail(getMessage("keygroup.get.empty"));
		}
		return ApiRestResponse.success(getBeanMapper().map(model, KeyGroupDTO.class));
	}

	public IKeyGroupService getKeyGroupService() {
		return keyGroupService;
	}
	
}