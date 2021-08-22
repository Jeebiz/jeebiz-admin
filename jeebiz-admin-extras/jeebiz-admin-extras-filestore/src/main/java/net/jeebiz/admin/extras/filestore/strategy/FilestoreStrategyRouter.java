package net.jeebiz.admin.extras.filestore.strategy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import lombok.extern.slf4j.Slf4j;
import net.jeebiz.admin.extras.filestore.bo.FileDeleteBO;
import net.jeebiz.admin.extras.filestore.bo.FileUploadBO;
import net.jeebiz.admin.extras.filestore.bo.FilesUploadBO;
import net.jeebiz.admin.extras.filestore.enums.FilestoreChannel;

@Component
@Slf4j
public class FilestoreStrategyRouter implements InitializingBean {
	
    private Map<FilestoreChannel, FilestoreStrategy> map = new HashMap<>();
    
    @Autowired(required = false)
    private List<FilestoreStrategy> smsSendStrategyList;

    @Override
    public void afterPropertiesSet() throws Exception {
    	if(!CollectionUtils.isEmpty(smsSendStrategyList)) {
    		for (FilestoreStrategy strategy : smsSendStrategyList) {
                map.put(strategy.getChannel(), strategy);
            }
    	}
        log.info("smsSendStrategyMap:{}", map);
    }
    
	public FilestoreStrategy routeFor(FilestoreChannel channel) {
        FilestoreStrategy orderStrategy = map.get(channel);
        return orderStrategy;
    }
	
	public <O extends FileUploadBO> FilestoreStrategy routeFor(O smsBo) {
        FilestoreStrategy orderStrategy = map.get(smsBo.getChannel());
        return orderStrategy;
    }
	
	public <O extends FilesUploadBO> FilestoreStrategy routeFor(O smsBo) {
        FilestoreStrategy orderStrategy = map.get(smsBo.getChannel());
        return orderStrategy;
    }

	public <O extends FileDeleteBO> FilestoreStrategy routeFor(O smsBo) {
        FilestoreStrategy orderStrategy = map.get(smsBo.getChannel());
        return orderStrategy;
    }

}
