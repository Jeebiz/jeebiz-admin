/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.core.setup.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.extra.converters.JSONArrayStringConverter;
import com.github.dozermapper.extra.converters.JSONObjectStringConverter;
import com.github.dozermapper.extra.converters.number.BigDecimalStringConverter;
import com.github.dozermapper.extra.converters.number.BigIntegerStringConverter;
import com.github.dozermapper.spring.DozerBeanMapperBuilderCustomizer;

@Configuration
public class DozerExtraConfiguration implements DozerBeanMapperBuilderCustomizer {

	@Bean
	public BigDecimalStringConverter bigDecimalStringConverter() {
		return new BigDecimalStringConverter();
	}
	
	@Bean
	public BigIntegerStringConverter bigIntegerStringConverter() {
		return new BigIntegerStringConverter();
	}
	
	@Bean
	public JSONArrayStringConverter jsonArrayStringConverter() {
		return new JSONArrayStringConverter();
	}
	
	@Bean
	public JSONObjectStringConverter jsonObjectStringConverter() {
		return new JSONObjectStringConverter();
	}

	@Override
	public void customize(DozerBeanMapperBuilder builder) {
		builder.withCustomConverters(jsonArrayStringConverter(), jsonObjectStringConverter(), bigDecimalStringConverter(), bigDecimalStringConverter());
		
	}
	
}
