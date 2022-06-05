package io.hiwepy.admin.authz.org.setup.strategy;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
 
@Service
public class TreeStrategyRouter implements InitializingBean {

    @Autowired
    private List<TreeStrategy> authStrategies;

    private Map<TreeChannel, TreeStrategy> map = new HashMap();

    @Override
    public void afterPropertiesSet() throws Exception {
        for (TreeStrategy item : authStrategies) {
            map.put(item.getChannel(), item);
        }
    }
    
    public TreeStrategy route(TreeChannel channel) {
        TreeStrategy authStrategy = map.get(channel);
        return authStrategy;
    }
    
}
