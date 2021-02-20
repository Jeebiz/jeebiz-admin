package net.jeebiz.admin.extras.inform.goeasy;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;

import io.goeasy.GoEasy;
import io.goeasy.publish.GoEasyError;
import io.goeasy.publish.PublishListener;
import net.jeebiz.admin.extras.inform.dao.entities.InformRecordModel;
import net.jeebiz.admin.extras.inform.setup.InformProvider;
import net.jeebiz.admin.extras.inform.setup.provider.InformOutputProvider;
import net.jeebiz.boot.api.exception.BizRuntimeException;

public class InformGoEasyOutputProvider implements InformOutputProvider<InformRecordModel>, InitializingBean {

	protected GoEasy goEasy = null;
	
	public InformGoEasyOutputProvider(GoEasy goEasy) {
		super();
		this.goEasy = goEasy;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		goEasy = new GoEasy( "hangzhou.goeasy.io", "BC-de8c5bb452a4496f8c2e6eb81c6b3309");
		//goEasy.otp(secretKey)
		
	}
	
	@Override
	public InformProvider getProvider() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public boolean output(InformRecordModel inform) {
		
		try {
			
			Map<String, Object> msg = new HashMap<>();
			
			msg.put("title", inform.getTitle());
			msg.put("content", inform.getContent());
			msg.put("bid", inform.getBid());
			msg.put("payload", StringUtils.hasText(inform.getPayload()) ? JSONObject.parse(inform.getPayload()) : Maps.newHashMap());
			
			
			goEasy.publish(inform.getToUid(), JSONObject.toJSONString(msg), new PublishListener(){
			   
				@Override
			    public void onSuccess() {
			        System.out.print("消息发布成功。");
			    }
				
			    @Override
			    public void onFailed(GoEasyError error) {
			        String rtMsg = "消息发布失败, 错误编码：" + error.getCode() + " 错误信息： " + error.getContent();
			        System.out.print(rtMsg);
			        throw new BizRuntimeException(rtMsg);
			    }
			    
			});
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
