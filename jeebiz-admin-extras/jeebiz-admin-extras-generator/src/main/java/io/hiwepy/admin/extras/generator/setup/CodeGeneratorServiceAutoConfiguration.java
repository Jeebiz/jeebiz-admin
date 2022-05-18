package io.hiwepy.admin.extras.generator.setup;

import io.hiwepy.admin.extras.generator.service.CodeGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName CodeGeneratorServiceAutoConfiguration
 * @Description TODO
 * @Author zd
 * @Date 2019/7/18 15:05
 * @Version 1.0
 **/
@Configuration
@ConditionalOnWebApplication
@EnableConfigurationProperties(CodeGeneratorProperties.class)
public class CodeGeneratorServiceAutoConfiguration {

    @Autowired
    private CodeGeneratorProperties codeGeneratorProperties;

    @Bean
    @ConditionalOnMissingBean
    public CodeGeneratorService codeGeneratorService(){
        CodeGeneratorService codeGeneratorService = new CodeGeneratorService();
        codeGeneratorService.setCodeGeneratorProperties(getCodeGeneratorProperties());
        return codeGeneratorService;
    }

    public CodeGeneratorProperties getCodeGeneratorProperties() {
        return codeGeneratorProperties;
    }
}
