package io.hiwepy.admin.authz.org.web.mvc;


import io.hiwepy.admin.authz.org.service.ITreeConfigService;
import io.hiwepy.admin.authz.org.setup.strategy.TreeNode;
import io.hiwepy.boot.api.ApiRestResponse;
import io.hiwepy.boot.api.web.BaseMapperController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 机构人员管理
 * </p>
 *
 */
@Api(tags = "树节点管理")
@RestController
@RequestMapping("/tree/")
public class TreeController extends BaseMapperController {

    @Autowired
    private ITreeConfigService treeConfigService;

    @ApiOperation(value = "获取机构树", httpMethod = "GET")
    @GetMapping("getTree")
    public ApiRestResponse<List<TreeNode>> getTreeByTreeId(@RequestParam @NotBlank(message = "学校id不能为空") String term) {
        try {
            return ApiRestResponse.success(getTreeConfigService().getTreeByTreeId(term));
        } catch (Exception e) {
            logException(this, e);
            return fail("tree.get.fail");
        }
    }

    public ITreeConfigService getTreeConfigService() {
        return treeConfigService;
    }
}

