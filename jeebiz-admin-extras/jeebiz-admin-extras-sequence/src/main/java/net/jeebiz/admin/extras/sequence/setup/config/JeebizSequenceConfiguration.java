/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.sequence.setup.config;

import java.util.Objects;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import net.jeebiz.boot.api.sequence.Sequence;

@Configuration

@ConditionalOnClass(Sequence.class)
@EnableConfigurationProperties(JeebizSequenceProperties.class)
public class JeebizSequenceConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public Sequence sequence(JeebizSequenceProperties properties) {
    	
    	long dataCenterId = Objects.isNull(properties.getDataCenterId()) ? 0L : properties.getDataCenterId(); 
    	long workerId = Objects.isNull(properties.getWorkerId()) ? 0x000000FF & Sequence.getLastIPAddress() : properties.getWorkerId(); 
    	
        return new Sequence(dataCenterId, workerId, properties.isClock(), properties.getTimeOffset(), properties.isRandomSequence());
    }
	
}
