package net.jeebiz.admin.shadow.web.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.jpush.spring.boot.PushObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.jeebiz.admin.shadow.setup.jpush.JPushOperationTemplate;
import net.jeebiz.boot.api.ApiRestResponse;
import net.jeebiz.boot.api.web.BaseApiController;

@Api(tags = "极光推送")
@RestController
@RequestMapping("/inform/push/")
public class JPushController extends BaseApiController {
    @Autowired
    private JPushOperationTemplate jPushOperationTemplate;

    @ApiOperation(value = "极光推送", notes = "测试推送")

    @GetMapping("testPush")
    public ApiRestResponse<String> testPush(String userId, String tag, String content, String alert){
        PushObject pushObject = new PushObject();
        pushObject.setMsgContent(content);
        pushObject.setAlert(alert);
        jPushOperationTemplate.send(userId,tag,pushObject);
        return success("push success");
    }
}
