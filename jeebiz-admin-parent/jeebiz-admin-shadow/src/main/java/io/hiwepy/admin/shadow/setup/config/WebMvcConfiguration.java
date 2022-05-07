package io.hiwepy.admin.shadow.setup.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ComponentScan({ "io.hiwepy.**.webmvc", "io.hiwepy.**.web", "io.hiwepy.**.controller" })
public class WebMvcConfiguration implements WebMvcConfigurer {
	
}
	