package net.jeebiz.admin.authz.feature.strategy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import lombok.extern.slf4j.Slf4j;
import net.jeebiz.admin.authz.feature.enums.FeatureNodeType;

@Component
@Slf4j
public class FeatureStrategyRouter implements InitializingBean {
	
    private Map<FeatureNodeType, FeatureStrategy> map = new HashMap<>();
    
    @Autowired(required = false)
    private List<FeatureStrategy> featureStrategyList;

    @Override
    public void afterPropertiesSet() throws Exception {
    	if(!CollectionUtils.isEmpty(featureStrategyList)) {
    		for (FeatureStrategy strategy : featureStrategyList) {
                map.put(strategy.getNodeType(), strategy);
            }
    	}
        log.info("smsSendStrategyMap:{}", map);
    }
    
	public FeatureStrategy routeFor(FeatureNodeType channel) {
        FeatureStrategy orderStrategy = map.get(channel);
        return orderStrategy;
    }

}
