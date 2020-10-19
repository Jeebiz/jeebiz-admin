package net.jeebiz.admin.extras.initializr.setup;

/**
 * @ClassName Constants
 * @Description 自定义常量类
 * @Author zd
 * @Date 2019/7/18 15:40
 * @Version 1.0
 **/
public class Constants {

    /**
     * TRUE
     */
    public static final boolean TRUE = true;

    /**
     * FALSE
     */
    public static final boolean FALSE = false;

    /**
     * 实体命名方式
     */
    public static final String ENTITY_NAME = "%sModel";

    /**
     * mapper 命名方式
     */
    public static final String MAPPER_NAME = "I%sDao";

    /**
     * Mapper xml 命名方式
     */
    public static final String XML_NAME = "%sMapper";

    /**
     * service 命名方式
     */
    public static final String SERVICE_NAME = "I%sService";

    /**
     * service impl 命名方式
     */
    public static final String SERVICE_IMPL_NAME = "%sServiceImpl";

    /**
     * controller 命名方式
     */
    public static final String CONTROLLER_NAME = "%sController";

    /**
     * Entity包名
     */
    public static final String ENTITY_PACKAGE_NAME = "dao.entities";

    /**
     * Service包名
     */
    public static final String SERVICE_PACKAGE_NAME = "service";

    /**
     * Service Impl包名
     */
    public static final String SERVICE_IMPL_PACKAGE_NAME = "service.impl";

    /**
     * Mapper包名
     */
    public static final String MAPPER_PACKAGE_NAME = "dao";

    /**
     * Mapper XML包名
     */
    public static final String XML_PACKAGE_NAME = "dao.sqlmap.oracle";

    /**
     * Controller包名
     */
    public static final String CONTROLLER_PACKAGE_NAME = "web.mvc";

    /**
     * .连接符
     */
    public static final String DOT = ".";

    /**
     * 项目java目录
     */
    public static final String JAVA_PATH = "\\src\\main\\java\\";

    /**
     * 项目resource目录
     */
    public static final String RESOURCE_PATH = "\\src\\main\\resources\\";

    /**
     * 自定义返回列表Vo模板地址
     */
    public static final String RESULT_VO_PATH = "/templates/resultVo.java.ftl";

    /**
     * 自定义返回新增Vo模板地址
     */
    public static final String NEW_VO_PATH = "/templates/newVo.java.ftl";

    /**
     * 自定义返回修改Vo模板地址
     */
    public static final String RENEW_VO_PATH = "/templates/reNewVo.java.ftl";

    /**
     * 自定义返回分页查询Vo模板地址
     */
    public static final String PAGINATION_VO_PATH = "/templates/paginationVo.java.ftl";

    /**
     * 自定义返回列表查询Vo模板地址
     */
    public static final String QUERY_VO_PATH = "/templates/queryVo.java.ftl";

}
