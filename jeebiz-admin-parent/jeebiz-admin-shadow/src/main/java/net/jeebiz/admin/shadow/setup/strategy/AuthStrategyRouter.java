package net.jeebiz.admin.shadow.setup.strategy;

import net.jeebiz.admin.shadow.bo.AuthBO;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.shiro.authc.AuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
 
@Service
public class AuthStrategyRouter implements InitializingBean {

    @Autowired
    private List<AuthStrategy> authStrategies;

    private Map<AuthChannel, AuthStrategy> map = new HashMap();

    @Override
    public void afterPropertiesSet() throws Exception {
        for (AuthStrategy item : authStrategies) {
            map.put(item.getChannel(), item);
        }
    }
    
    public <T> AuthStrategy<T> route(AuthChannel channel) {
        AuthStrategy<T> authStrategy = map.get(channel);
        return authStrategy;
    }
    
    public <T> AuthStrategy<T> route(AuthBO<T> authBO) {
        AuthStrategy<T> authStrategy = map.get(authBO.getChannel());
        return authStrategy;
    }
    
    public <T> AuthStrategy<T> route(Authentication token) {
        AuthStrategy<T> authStrategy = map.get(AuthChannel.valueOf(token));
        return authStrategy;
    }
    
}
