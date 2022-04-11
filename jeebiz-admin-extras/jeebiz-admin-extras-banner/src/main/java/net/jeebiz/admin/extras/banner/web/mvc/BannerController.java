package net.jeebiz.admin.extras.banner.web.mvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.biz.authz.principal.ShiroPrincipal;
import org.apache.shiro.biz.utils.SubjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.hiwepy.ip2region.spring.boot.ext.RegionEnum;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import net.jeebiz.admin.extras.banner.dao.entities.BannerEntity;
import net.jeebiz.admin.extras.banner.service.IBannerService;
import net.jeebiz.admin.extras.banner.web.dto.BannerDTO;
import net.jeebiz.admin.extras.banner.web.dto.BannerNewDTO;
import net.jeebiz.admin.extras.banner.web.dto.BannerPaginationDTO;
import net.jeebiz.admin.extras.banner.web.dto.BannerRenewDTO;
import net.jeebiz.admin.extras.banner.web.param.BannerQueryParams;
import net.jeebiz.boot.api.ApiRestResponse;
import net.jeebiz.boot.api.XHeaders;
import net.jeebiz.boot.api.utils.WebUtils;
import net.jeebiz.boot.api.web.BaseMapperController;
import net.jeebiz.boot.api.web.Result;
import net.jeebiz.boot.extras.external.region.NestedRegionTemplate;
import springfox.documentation.annotations.ApiIgnore;

/**
 */
@Api(tags = "横幅轮播图: 用于配置轮播图")
@Controller
@RequestMapping("/banner/")
public class BannerController extends BaseMapperController {

	@Autowired
	private IBannerService bannerService;
	@Autowired
	private NestedRegionTemplate regionTemplate;

	@ApiOperation(value = "横幅轮播图列表", notes = "分页查询横幅轮播图列表")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "body", name = "queryDTO", value = "横幅轮播图查询参数", dataType = "BannerPaginationDTO")
	})
	@PostMapping("list")
	@RequiresPermissions("banner:list")
	@ResponseBody
	public ApiRestResponse<Result<BannerDTO>> list(@Valid @RequestBody BannerPaginationDTO queryDTO) throws Exception {
		BannerEntity model = getBeanMapper().map(queryDTO, BannerEntity.class);
		Page<BannerEntity> pageResult = getBannerService().getPagedList(model);
		List<BannerDTO> retList = new ArrayList<BannerDTO>();
		for (BannerEntity BannerModel : pageResult.getRecords()) {
			retList.add(getBeanMapper().map(BannerModel, BannerDTO.class));
		}
		return ApiRestResponse.success(new Result<BannerDTO>(pageResult, retList));
	}

	@ApiOperation(value = "查询横幅轮播图", notes = "查询满足当前条件的横幅轮播图列表（用于显示）")
	@GetMapping("list")
	@RequiresAuthentication
	@ResponseBody
	public ApiRestResponse<List<BannerDTO>> list(@Valid BannerQueryParams params,
										  @RequestHeader(XHeaders.X_APP_ID) String appId,
										  @RequestHeader(value = XHeaders.X_APP_CHANNEL, defaultValue = "0") String appChannel,
										  @RequestHeader(value = XHeaders.X_REGION, required = false) String regionCode,
										  @ApiIgnore HttpServletRequest request) throws Exception {
		if(!StringUtils.hasText(regionCode)){
			RegionEnum regionEnum = regionTemplate.getRegionByIp(WebUtils.getRemoteAddr(request));
			regionCode = regionEnum.getCode2();
		}
		List<BannerDTO> bannerList = getBannerService().getBannerList(appId, appChannel, regionCode, params.getLanguage(), params.getType());
		return ApiRestResponse.success(bannerList);
	}

	@ApiOperation(value = "创建横幅轮播图", notes = "增加横幅轮播图信息")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "body", name = "bannerDTO", value = "横幅轮播图信息", dataType = "BannerNewDTO")
	})
	@PostMapping("new")
	@RequiresPermissions("banner:new")
	@ResponseBody
	public ApiRestResponse<String> newBanner(@RequestBody @Valid BannerNewDTO bannerDTO) throws Exception {

		BannerEntity entity = getBeanMapper().map(bannerDTO, BannerEntity.class);
		entity.setCreator(SubjectUtils.getPrincipal(ShiroPrincipal.class).getUserid());
		entity.setCreateTime(LocalDateTime.now());

		boolean count = getBannerService().save(entity);
		return success("banner.new.success", count);
	}


	@ApiOperation(value = "修改横幅轮播图", notes = "编辑横幅轮播图信息")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "body", name = "bannerDTO", value = "横幅轮播图信息", dataType = "BannerRenewDTO")
	})
	@PostMapping("renew")
	@RequiresPermissions("banner:renew")
	@ResponseBody
	public ApiRestResponse<String> renew(@RequestBody @Valid BannerRenewDTO bannerDTO) throws Exception {
		BannerEntity entity = getBeanMapper().map(bannerDTO, BannerEntity.class);
		entity.setModifyer(SubjectUtils.getPrincipal(ShiroPrincipal.class).getUserid());
		entity.setModifyTime(LocalDateTime.now());
		boolean rt = getBannerService().updateById(entity);
		return success("banner.renew.success", rt);
	}

	@ApiOperation(value = "删除横幅轮播图", notes = "根据ID删除横幅轮播图")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id", value = "横幅轮播图ID", required = true, dataType = "String")
	})
	@PostMapping("delete")
	@RequiresPermissions("banner:del")
	@ResponseBody
	public ApiRestResponse<String> delApp(@RequestParam("id") String id) throws Exception {
		boolean rt = getBannerService().removeById(id);
		return success("banner.delete.success", rt);
	}

	public IBannerService getBannerService() {
		return bannerService;
	}
}
