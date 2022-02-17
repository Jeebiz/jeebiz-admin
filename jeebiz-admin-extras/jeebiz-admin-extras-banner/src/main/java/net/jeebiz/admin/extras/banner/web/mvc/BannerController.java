package net.jeebiz.admin.extras.banner.web.mvc;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.biz.authz.principal.ShiroPrincipal;
import org.apache.shiro.biz.utils.SubjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import net.jeebiz.admin.extras.banner.dao.entities.BannerEntity;
import net.jeebiz.admin.extras.banner.service.IBannerService;
import net.jeebiz.admin.extras.banner.web.dto.BannerDTO;
import net.jeebiz.admin.extras.banner.web.dto.BannerPaginationDTO;
import net.jeebiz.boot.api.ApiRestResponse;
import net.jeebiz.boot.api.web.BaseMapperController;
import net.jeebiz.boot.api.web.Result;

/**
 */
@Api(tags = "我的应用:用于实现需要管理应用信息的认证功能")
@Controller
@RequestMapping("/banner/")
public class BannerController extends BaseMapperController {
	
	@Autowired
	private IBannerService myAppService;
	
	@ApiOperation(value = "我的应用列表", notes = "分页查询已维护的应用基本信息、订阅服务量")
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "body", name = "appDTO", value = "应用信息传输对象", dataType = "BannerPaginationDTO") 
	})
	@PostMapping("list")
	@RequiresPermissions("myapp:list")
	@ResponseBody
	public Object list(@Valid @RequestBody BannerPaginationDTO appDTO) throws Exception {
		
		BannerEntity model = getBeanMapper().map(appDTO, BannerEntity.class);
		model.setUserId(SubjectUtils.getPrincipal(ShiroPrincipal.class).getUserid());
		
		Page<BannerEntity> pageResult = getMyAppService().getPagedList(model);
		List<BannerDTO> retList = new ArrayList<BannerDTO>();
		for (BannerEntity BannerModel : pageResult.getRecords()) {
			retList.add(getBeanMapper().map(BannerModel, BannerDTO.class));
		}
		
		return new Result<BannerDTO>(pageResult, retList);
		
	}
	
	@ApiOperation(value = "创建我的应用", notes = "增加我的应用信息： 应用名称、开发语言、部署地址等")
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "body", name = "appDTO", value = "应用信息传输对象", dataType = "BannerDTO") 
	})
	@PostMapping("new")
	@RequiresPermissions("myapp:new")
	@ResponseBody
	public ApiRestResponse<String> newApp(@RequestBody @Valid BannerDTO appDTO) throws Exception {
		
		BannerEntity model = getBeanMapper().map(appDTO, BannerEntity.class);
		model.setUserId(SubjectUtils.getPrincipal(ShiroPrincipal.class).getUserid());
		model.setUid(UUID.randomUUID().toString().replace("-", "").toUpperCase());
		
		boolean count = getMyAppService().save(model);
		return success("myapp.new.success", count);
	}
	
	@ApiOperation(value = "查询应用信息", notes = "根据应用ID查询应用信息")
	@ApiImplicitParams({ 
		@ApiImplicitParam( name = "id", required = true, value = "应用ID", dataType = "String")
	})
	@PostMapping("detail/{id}")
	@RequiresPermissions("myapp:detail")
	@ResponseBody
	public Object detail(@PathVariable String id) throws Exception { 
		return getMyAppService().getModel(id);
	}

	@ApiOperation(value = "修改我的应用", notes = "编辑 应用名称、开发语言、部署地址等")
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "body", name = "appDTO", value = "应用信息DTO", dataType = "BannerDTO") 
	})
	@PostMapping("edit")
	@RequiresPermissions("myapp:edit")
	@ResponseBody
	public ApiRestResponse<String> editApp(@RequestBody @Valid BannerDTO appDTO) throws Exception {
		BannerEntity model = getBeanMapper().map(appDTO, BannerEntity.class);
		boolean count = getMyAppService().updateById(model);
		return success("myapp.edit.success", count);
	}

	@ApiOperation(value = "删除我的应用", notes = "根据应用ID删除我的应用；删除应用后订阅服务信息、认证信息都将清除")
	@ApiImplicitParams({ 
		@ApiImplicitParam(name = "appId", value = "应用ID", required = true, dataType = "String")
	})
	@PostMapping(value = "del/{appId}")
	@RequiresPermissions("myapp:del")
	@ResponseBody
	public ApiRestResponse<String> delApp(@PathVariable("appId") String appId) throws Exception {
		boolean count = getMyAppService().removeById(appId);
		return success("myapp.del.success", count);
	}

	public IBannerService getMyAppService() {
		return myAppService;
	}

	public void setMyAppService(IBannerService myAppService) {
		this.myAppService = myAppService;
	}

}
