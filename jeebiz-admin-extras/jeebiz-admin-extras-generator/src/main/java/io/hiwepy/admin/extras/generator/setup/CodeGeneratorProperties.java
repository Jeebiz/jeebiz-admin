package io.hiwepy.admin.extras.generator.setup;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @ClassName CodeGeneratorProperties
 * @Description 代码生成配置类
 * @Author zd
 * @Date 2019/7/18 15:00
 * @Version 1.0
 **/
@Data
@ConfigurationProperties(CodeGeneratorProperties.PREFIX)
public class CodeGeneratorProperties {

    public static final String PREFIX = "mybatis-plus.codegenerator";

    /**
     * 自定义继承的Entity类全称，带包名
     */
    private String superEntityClass = "io.hiwepy.boot.api.dao.entities.PaginationModel";

    /**
     * 自定义继承的Controller类全称，带包名
     */
    private String superControllerClass = "com.knowway.cloud.api.webmvc.BaseApiController";

    /**
     * 自定义继承的service类全称，带包名
     */
    private String superServiceClass = "com.knowway.cloud.api.service.BaseService";

    /**
     * 自定义继承的serviceImpl类全称，带包名
     */
    private String superServiceImplClass = "com.knowway.cloud.api.service.BaseServiceImpl";

    /**
     * 自定义继承的Mapper类全称，带包名
     */
    private String superMapperClass = "com.knowway.cloud.api.dao.BaseMapper";

    /**
     * 数据库驱动名称,默认ORACLE驱动
     */
    private String driverName = "oracle.jdbc.driver.OracleDriver";

    /**
     * 父包名。如果为空，将下面子包名必须写全部， 否则就只需写子包名
     */
    private String parentPackageName = "com.knowway.smartedu";


}
