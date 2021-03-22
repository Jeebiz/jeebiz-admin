/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.basedata.web.mvc;

import java.util.List;

import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import net.jeebiz.admin.extras.basedata.dao.entities.KeyGroupModel;
import net.jeebiz.admin.extras.basedata.dao.entities.KeyValueModel;
import net.jeebiz.admin.extras.basedata.service.IKeyGroupService;
import net.jeebiz.admin.extras.basedata.service.IKeyValueService;
import net.jeebiz.admin.extras.basedata.setup.Constants;
import net.jeebiz.admin.extras.basedata.web.vo.KeyGroupVo;
import net.jeebiz.admin.extras.basedata.web.vo.KeyValueGroupRenewVo;
import net.jeebiz.admin.extras.basedata.web.vo.KeyValueNewVo;
import net.jeebiz.admin.extras.basedata.web.vo.KeyValuePaginationVo;
import net.jeebiz.admin.extras.basedata.web.vo.KeyValueRenewVo;
import net.jeebiz.admin.extras.basedata.web.vo.KeyValueVo;
import net.jeebiz.boot.api.ApiRestResponse;
import net.jeebiz.boot.api.annotation.BusinessLog;
import net.jeebiz.boot.api.annotation.BusinessType;
import net.jeebiz.boot.api.dao.entities.PairModel;
import net.jeebiz.boot.api.utils.HttpStatus;
import net.jeebiz.boot.api.utils.StringUtils;
import net.jeebiz.boot.api.webmvc.BaseMapperController;
import net.jeebiz.boot.api.webmvc.Result;

@Api(tags = "基础数据：键值对形式的数据维护")
@ApiResponses({ 
	@ApiResponse(code = 0, message = "请求成功", response = ApiRestResponse.class),
	@ApiResponse(code = HttpStatus.SC_OK, message = "操作成功", response = ApiRestResponse.class),
	@ApiResponse(code = HttpStatus.SC_CREATED, message = "已创建", response = ApiRestResponse.class),
	@ApiResponse(code = HttpStatus.SC_UNAUTHORIZED, message = "请求要求身份验证", response = ApiRestResponse.class),
	@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "权限不足", response = ApiRestResponse.class),
	@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "请求资源不存在", response = ApiRestResponse.class),
	@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "服务器内部异常", response = ApiRestResponse.class),
	@ApiResponse(code = 10001, message = "认证失败", response = ApiRestResponse.class),
	@ApiResponse(code = 10021, message = "授权失败", response = ApiRestResponse.class),
	@ApiResponse(code = 10022, message = "Token缺失", response = ApiRestResponse.class),
	@ApiResponse(code = 10023, message = "Token已过期", response = ApiRestResponse.class),
	@ApiResponse(code = 10024, message = "Token已失效", response = ApiRestResponse.class),
	@ApiResponse(code = 10025, message = "Token错误", response = ApiRestResponse.class),
	@ApiResponse(code = 10110, message = "不允许访问（功能未授权）", response = ApiRestResponse.class),
	@ApiResponse(code = 10111, message = "请求失败", response = ApiRestResponse.class),
	@ApiResponse(code = 10112, message = "数据为空", response = ApiRestResponse.class),
	@ApiResponse(code = 10113, message = "参数类型不匹配", response = ApiRestResponse.class),
	@ApiResponse(code = 10114, message = "缺少矩阵变量", response = ApiRestResponse.class),
	@ApiResponse(code = 10115, message = "缺少URI模板变量", response = ApiRestResponse.class),
	@ApiResponse(code = 10116, message = "缺少Cookie变量", response = ApiRestResponse.class),
	@ApiResponse(code = 10117, message = "缺少请求头", response = ApiRestResponse.class),
	@ApiResponse(code = 10118, message = "缺少参数", response = ApiRestResponse.class),
	@ApiResponse(code = 10119, message = "缺少请求对象", response = ApiRestResponse.class),
	@ApiResponse(code = 10120, message = "参数规则不满足", response = ApiRestResponse.class),
	@ApiResponse(code = 10121, message = "参数绑定错误", response = ApiRestResponse.class),
	@ApiResponse(code = 10122, message = "参数解析错误", response = ApiRestResponse.class),
	@ApiResponse(code = 10123, message = "参数验证失败", response = ApiRestResponse.class),
	@ApiResponse(code = 10201, message = "服务器：运行时异常", response = ApiRestResponse.class),
	@ApiResponse(code = 10202, message = "服务器：空值异常", response = ApiRestResponse.class),
	@ApiResponse(code = 10203, message = "服务器：数据类型转换异常", response = ApiRestResponse.class),
	@ApiResponse(code = 10204, message = "服务器：IO异常", response = ApiRestResponse.class),
	@ApiResponse(code = 10205, message = "服务器：未知方法异常", response = ApiRestResponse.class),
	@ApiResponse(code = 10206, message = "服务器：非法参数异常", response = ApiRestResponse.class),
	@ApiResponse(code = 10207, message = "服务器：数组越界异常", response = ApiRestResponse.class),
	@ApiResponse(code = 10208, message = "服务器：网络异常", response = ApiRestResponse.class)
})
@RestController
@RequestMapping("/extras/basedata/keyvalue/")
public class KeyValueController extends BaseMapperController {
	
	@Autowired
	private IKeyGroupService keyGroupService;
	@Autowired
	private IKeyValueService keyValueService;
	
	@ApiOperation(value = "分页查询基础数据", notes = "分页查询基础数据")
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "body", name = "paginationVo", value = "用户信息筛选条件", dataType = "KeyValuePaginationVo")
	})
	@ApiResponses({ 
		@ApiResponse(code = HttpStatus.SC_OK, message = "操作成功", response = Result.class)
	})
	@BusinessLog(module = Constants.EXTRAS_BASEDATA, business = "根据分组分页查询基础数据", opt = BusinessType.SELECT)
	@PostMapping("list")
	@RequiresPermissions("keyvalue:list")
	@ResponseBody
	public Object list(@Valid @RequestBody KeyValuePaginationVo paginationVo){
		
		KeyValueModel model =  getBeanMapper().map(paginationVo, KeyValueModel.class);
		Page<KeyValueModel> pageResult = getKeyValueService().getPagedList(model);
		List<KeyValueVo> retList = Lists.newArrayList();
		for (KeyValueModel keyvalueModel : pageResult.getRecords()) {
			retList.add(getBeanMapper().map(keyvalueModel, KeyValueVo.class));
		}
		
		return new Result<KeyValueVo>(pageResult, retList);
		
	}
	
	@ApiOperation(value = "查询基础数据分组", notes = "查询基础数据分组")
	@ApiResponses({ 
		@ApiResponse(code = HttpStatus.SC_OK, message = "操作成功", response = KeyGroupVo.class)
	})
	@BusinessLog(module = Constants.EXTRAS_BASEDATA, business = "查询基础数据分组", opt = BusinessType.SELECT)
	@GetMapping("groups")
	@RequiresPermissions("keyvalue:list")
	@ResponseBody
	public Object groups() throws Exception {
		List<KeyGroupModel> records = getKeyGroupService().getPairValues();
		List<KeyGroupVo> retList = Lists.newArrayList();
		for (KeyGroupModel groupModel : records) {
			retList.add(getBeanMapper().map(groupModel, KeyGroupVo.class));
		}
		return retList;
	}
	
	@ApiOperation(value = "根据分组查询基础数据", notes = "根据分组查询基础数据")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "form", name = "gkey", value = "基础数据分组", required = true, dataType = "String")
	})
	@ApiResponses({ 
		@ApiResponse(code = HttpStatus.SC_OK, message = "操作成功", response = PairModel.class)
	})
	@BusinessLog(module = Constants.EXTRAS_BASEDATA, business = "根据分组查询基础数据", opt = BusinessType.SELECT)
	@GetMapping("pairs")
	@RequiresPermissions("keyvalue:list")
	@ResponseBody
	public Object pairs(@RequestParam String gkey) throws Exception {
		return getKeyValueService().getPairValues(gkey);
	}
	
	@ApiOperation(value = "创建基础数据", notes = "增加一个新的基础数据")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "body", name = "vo", value = "基础数据传输对象", dataType = "KeyValueNewVo") 
	})
	@BusinessLog(module = Constants.EXTRAS_BASEDATA, business = "创建基础数据", opt = BusinessType.INSERT)
	@PostMapping("new")
	@RequiresPermissions("keyvalue:new")
	@ResponseBody
	public Object keyvalue(@Valid @RequestBody KeyValueNewVo vo) throws Exception {
		KeyValueModel model = getBeanMapper().map(vo, KeyValueModel.class);
		
		int ct = getKeyValueService().getCount(model);
		if(ct > 0) {
			return fail("keyvalue.new.conflict");
		}
		// 新增一条数据库配置记录
		int result = getKeyValueService().insert(model);
		if(result == 1) {
			return success("keyvalue.new.success", result);
		}
		// 逻辑代码，如果发生异常将不会被执行
		return fail("keyvalue.new.fail", result);
	}
	
	@ApiOperation(value = "删除基础数据", notes = "删除基础数据")
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "form", name = "ids", value = "基础数据ID,多个用,拼接", required = true, dataType = "String")
	})
	@BusinessLog(module = Constants.EXTRAS_BASEDATA, business = "删除基础数据", opt = BusinessType.UPDATE)
	@GetMapping("delete")
	@RequiresPermissions("keyvalue:delete")
	@ResponseBody
	public Object delete(@RequestParam String ids) throws Exception {
		// 执行基础数据删除操作
		List<String> idList = Lists.newArrayList(StringUtils.tokenizeToStringArray(ids));
		int result = getKeyValueService().batchDelete(idList);
		if(result > 0) {
			return success("keyvalue.delete.success", result);
		}
		// 逻辑代码，如果发生异常将不会被执行
		return fail("keyvalue.delete.fail", result);
	}
	
	
	@ApiOperation(value = "更新基础数据", notes = "更新基础数据")
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "body", name = "vo", value = "基础数据", required = true, dataType = "KeyValueRenewVo"),
	})
	@BusinessLog(module = Constants.EXTRAS_BASEDATA, business = "更新基础数据", opt = BusinessType.UPDATE)
	@PostMapping("renew")
	@RequiresPermissions("keyvalue:renew")
	@ResponseBody
	public Object renew(@Valid @RequestBody KeyValueRenewVo vo) throws Exception {
		KeyValueModel model = getBeanMapper().map(vo, KeyValueModel.class);
		int ct = getKeyValueService().getCount(model);
		if(ct > 0) {
			return fail("keyvalue.renew.conflict");
		}
		int result = getKeyValueService().update(model);
		if(result == 1) {
			return success("keyvalue.renew.success", result);
		}
		// 逻辑代码，如果发生异常将不会被执行
		return fail("keyvalue.renew.fail", result);
	}
	
	@ApiOperation(value = "更新基础数据状态", notes = "更新基础数据状态")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "form", name = "id", required = true, value = "基础数据ID", dataType = "String"),
		@ApiImplicitParam(paramType = "form", name = "status", required = true, value = "基础数据状态", dataType = "String", allowableValues = "1,0")
	})
	@BusinessLog(module = Constants.EXTRAS_BASEDATA, business = "更新基础数据状态", opt = BusinessType.UPDATE)
	@PostMapping("status")
	@RequiresPermissions("keyvalue:status")
	@ResponseBody
	public Object status(@RequestParam String id, @RequestParam String status) throws Exception {
		int result = getKeyValueService().setStatus(id, status);
		if(result == 1) {
			return success("keyvalue.status.success", result);
		}
		// 逻辑代码，如果发生异常将不会被执行
		return fail("keyvalue.status.fail", result);
	}
	
	@ApiOperation(value = "批量更新分组内的基础数据", notes = "批量更新分组内的基础数据")
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "body", name = "renewVo", value = "基础数据集合", required = true, dataType = "KeyValueGroupRenewVo"),
	})
	@BusinessLog(module = Constants.EXTRAS_BASEDATA, business = "批量更新分组内的基础数据", opt = BusinessType.UPDATE)
	@PostMapping(value = "batch/renew")
	@RequiresPermissions("keyvalue:renew")
	@ResponseBody
	public Object batchRenew(@Valid @RequestBody KeyValueGroupRenewVo renewVo) throws Exception {
		
		try {
			
			List<KeyValueModel> list = Lists.newArrayList();
			for (KeyValueRenewVo keyvalueVo : renewVo.getDatas()) {
				KeyValueModel model = getBeanMapper().map(keyvalueVo, KeyValueModel.class);
				model.setGkey(renewVo.getGkey());
				list.add(model);
			}
			// 批量执行基础数据更新操作
			getKeyValueService().batchUpdate(list);
			return success("keyvalue.renew.success");
		} catch (Exception e) {
			return fail("keyvalue.renew.fail");
		}
	}
	
	@ApiOperation(value = "查询基础数据信息", notes = "根据ID查询基础数据信息")
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "path", name = "id", required = true, value = "基础数据ID", dataType = "String")
	})
	@ApiResponses({ 
		@ApiResponse(code = HttpStatus.SC_OK, message = "操作成功", response = KeyValueVo.class)
	})
	@BusinessLog(module = Constants.EXTRAS_BASEDATA, business = "查询基础数据信息", opt = BusinessType.SELECT)
	@GetMapping("detail/{id}")
	@RequiresPermissions("keyvalue:detail")
	@ResponseBody
	public Object detail(@PathVariable("id") String id) throws Exception { 
		KeyValueModel model = getKeyValueService().getModel(id);
		if(model == null) {
			return ApiRestResponse.empty(getMessage("keyvalue.get.empty"));
		}
		return getBeanMapper().map(model, KeyValueVo.class);
	}

	public IKeyGroupService getKeyGroupService() {
		return keyGroupService;
	}

	public IKeyValueService getKeyValueService() {
		return keyValueService;
	}
	
}
