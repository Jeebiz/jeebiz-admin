package net.jeebiz.admin.extras.dict.web.mvc;


import java.util.List;

import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import net.jeebiz.admin.extras.dict.dao.entities.DictRegionEntity;
import net.jeebiz.admin.extras.dict.service.IDictRegionService;
import net.jeebiz.admin.extras.dict.web.dto.DictRegionDTO;
import net.jeebiz.admin.extras.dict.web.dto.DictRegionPaginationDTO;
import net.jeebiz.boot.api.ApiRestResponse;
import net.jeebiz.boot.api.dao.entities.PairModel;
import net.jeebiz.boot.api.web.BaseApiController;
import net.jeebiz.boot.api.web.Result;

/**
 * <p>
 * 国家地区代码 http://doc.chacuo.net/iso-3166-1 前端控制器
 * </p>
 *
 * @author wandl
 * @since 2022-01-22
 */
@Controller
@RequestMapping("/dict/region")
public class DictRegionController extends BaseApiController {

    @Autowired
    private IDictRegionService regionService;

    @ApiOperation(value = "分页查询国家地区代码", notes = "分页查询国家地区代码")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "paginationDTO", value = "国家地区代码筛选条件", dataType = "DictRegionPaginationDTO")
    })
    @PostMapping("list")
    @RequiresPermissions("region:list")
    @ResponseBody
    public Result<DictRegionDTO> list(@Valid @RequestBody DictRegionPaginationDTO paginationDTO){

        DictRegionEntity entity =  getBeanMapper().map(paginationDTO, DictRegionEntity.class);
        Page<DictRegionEntity> pageResult = getRegionService().getPagedList(entity);
        List<DictRegionDTO> retList = Lists.newArrayList();
        for (DictRegionEntity regionEntity : pageResult.getRecords()) {
            retList.add(getBeanMapper().map(regionEntity, DictRegionDTO.class));
        }

        return new Result<DictRegionDTO>(pageResult, retList);

    }

    @ApiOperation(value = "查询国家地区代码（键值对）", notes = "查询国家地区代码（键值对）")
    @GetMapping("pairs")
    @RequiresAuthentication
    @ResponseBody
    public ApiRestResponse<List<PairModel>> pairs() throws Exception {
        return ApiRestResponse.success(getRegionService().getPairList());
    }

    public IDictRegionService getRegionService() {
        return regionService;
    }
}

