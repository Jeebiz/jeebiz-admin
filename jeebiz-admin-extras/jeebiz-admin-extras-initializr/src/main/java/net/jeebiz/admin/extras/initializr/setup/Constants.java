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
     * 自定义返回列表DTO模板地址
     */
    public static final String RESULT_DTO_PATH = "/templates/resultDTO.java.ftl";

    /**
     * 自定义返回新增DTO模板地址
     */
    public static final String NEW_DTO_PATH = "/templates/newDTO.java.ftl";

    /**
     * 自定义返回修改DTO模板地址
     */
    public static final String RENEW_DTO_PATH = "/templates/reNewDTO.java.ftl";

    /**
     * 自定义返回分页查询DTO模板地址
     */
    public static final String PAGINATION_DTO_PATH = "/templates/paginationDTO.java.ftl";

    /**
     * 自定义返回列表查询DTO模板地址
     */
    public static final String QUERY_DTO_PATH = "/templates/queryDTO.java.ftl";

}
