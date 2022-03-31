package net.jeebiz.admin.extras.inform.strategy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import lombok.extern.slf4j.Slf4j;
import net.jeebiz.admin.extras.inform.bo.InformSendBO;
import net.jeebiz.admin.extras.inform.emums.InformSendChannel;

@Component
@Slf4j
public class InformSendStrategyRouter implements InitializingBean {
	
    private Map<InformSendChannel, InformSendStrategy> map = new HashMap<>();
    
    @Autowired(required = false)
    private List<InformSendStrategy> payStrategyList;

    @Override
    public void afterPropertiesSet() throws Exception {
    	if(!CollectionUtils.isEmpty(payStrategyList)) {
    		for (InformSendStrategy payStrategy : payStrategyList) {
	            map.put(payStrategy.getChannel(), payStrategy);
	        }
    	}
        log.info("payStrategyMap:{}", map);
    }
    
	public <O extends InformSendBO> InformSendStrategy routeFor(O payBo) {
        InformSendStrategy orderStrategy = map.get(payBo.getChannel());
        return orderStrategy;
    }

}
