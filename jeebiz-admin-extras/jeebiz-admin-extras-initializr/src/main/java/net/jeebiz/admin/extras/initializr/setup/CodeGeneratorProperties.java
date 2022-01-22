package net.jeebiz.admin.extras.initializr.setup;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @ClassName CodeGeneratorProperties
 * @Description 代码生成配置类
 * @Author zd
 * @Date 2019/7/18 15:00
 * @Version 1.0
 **/
@ConfigurationProperties(CodeGeneratorProperties.PREFIX)
public class CodeGeneratorProperties {

    public static final String PREFIX = "mybatis-plus.codegenerator";

    /**
     * 自定义继承的Entity类全称，带包名
     */
    private String superEntityClass = "net.jeebiz.boot.api.dao.entities.PaginationModel";

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

    public String getSuperEntityClass() {
        return superEntityClass;
    }

    public void setSuperEntityClass(String superEntityClass) {
        this.superEntityClass = superEntityClass;
    }

    public String getSuperControllerClass() {
        return superControllerClass;
    }

    public void setSuperControllerClass(String superControllerClass) {
        this.superControllerClass = superControllerClass;
    }

    public String getSuperServiceClass() {
        return superServiceClass;
    }

    public void setSuperServiceClass(String superServiceClass) {
        this.superServiceClass = superServiceClass;
    }

    public String getSuperServiceImplClass() {
        return superServiceImplClass;
    }

    public void setSuperServiceImplClass(String superServiceImplClass) {
        this.superServiceImplClass = superServiceImplClass;
    }

    public String getSuperMapperClass() {
        return superMapperClass;
    }

    public void setSuperMapperClass(String superMapperClass) {
        this.superMapperClass = superMapperClass;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getParentPackageName() {
        return parentPackageName;
    }

    public void setParentPackageName(String parentPackageName) {
        this.parentPackageName = parentPackageName;
    }
}
