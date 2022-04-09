package net.jeebiz.admin.authz.rbac0.setup.strategy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import lombok.extern.slf4j.Slf4j;
import net.jeebiz.admin.authz.rbac0.bo.AuthBO;

@Component
@Slf4j
public class AuthStrategyRouter implements InitializingBean {

    private Map<AuthChannel, AuthStrategy> map = new HashMap<>();

    @Autowired(required = false)
    private List<AuthStrategy> filestoreStrategyList;

    @Override
    public void afterPropertiesSet() throws Exception {
    	if(!CollectionUtils.isEmpty(filestoreStrategyList)) {
    		for (AuthStrategy strategy : filestoreStrategyList) {
                map.put(strategy.getChannel(), strategy);
            }
    	}
        log.info("AuthStrategyMap:{}", map);
    }

	public AuthStrategy routeFor(AuthChannel channel) {
        AuthStrategy orderStrategy = map.get(channel);
        return orderStrategy;
    }

	public <O extends AuthBO> AuthStrategy routeFor(O authBo) {
        AuthStrategy orderStrategy = map.get(authBo.getChannel());
        return orderStrategy;
    }

}
