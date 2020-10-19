package net.jeebiz.admin.shadow.setup.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ComponentScan({ "net.jeebiz.**.webmvc", "net.jeebiz.**.web", "net.jeebiz.**.controller" })
public class WebMvcConfiguration implements WebMvcConfigurer {
	
}
	