package net.jeebiz.admin.extras.inform.web.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import net.jeebiz.admin.extras.inform.goeasy.GoEasyOperationTemplate;
import net.jeebiz.boot.api.web.BaseApiController;

@Api(tags = "GoEasy推送")
@RestController
@RequestMapping("/jpush/")
public class GoEasyController extends BaseApiController {
	
    @Autowired
    private GoEasyOperationTemplate jPushOperationTemplate;
    
    
    public GoEasyOperationTemplate getjPushOperationTemplate() {
		return jPushOperationTemplate;
	}
    
}
