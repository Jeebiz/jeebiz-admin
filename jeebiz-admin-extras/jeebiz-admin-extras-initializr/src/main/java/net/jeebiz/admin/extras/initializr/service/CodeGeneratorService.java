package net.jeebiz.admin.extras.initializr.service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.ConstVal;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.FileOutConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import net.jeebiz.admin.extras.initializr.setup.CodeGeneratorProperties;
import net.jeebiz.admin.extras.initializr.setup.Constants;
import net.jeebiz.admin.extras.initializr.setup.CustomFreemarkerTemplateEngine;

/**
 * 代码生成具体实现类--目前只支持ORACLE
 * @author <a href="https://github.com/vindell">wandl</a>
 * @since 2020-04-29
 */
@SuppressWarnings("all")
public class CodeGeneratorService {

    //自动生成全局设置类
    private AutoGenerator autoGenerator = new AutoGenerator();

    //数据库配置
    private DataSourceConfig dataSourceConfig = new DataSourceConfig();

    // 全局配置
    private GlobalConfig globalConfig = new GlobalConfig();

    //包配置
    private PackageConfig packageConfig = new PackageConfig();

    //配置模板
    private TemplateConfig templateConfig = new TemplateConfig();

    //策略配置 数据库表配置
    private StrategyConfig strategyConfig = new StrategyConfig();

    //pathInfo Map对象
    private Map<String,String> pathInfo = new HashMap<>(6);

    CodeGeneratorProperties codeGeneratorProperties;

    /**
     * 代码生成服务类
     * @param projectPath 项目绝对地址
     * @param createName 生成类名称
     * @param moudlePackageName 模块包名
     * @param jdbcUrl 数据库地址
     * @param jdbcUsername 数据库用户名
     * @param jdbcPassword 数据库密码
     * @param driverClassName 数据库驱动包名称
     * @param include 生成表
     * @param tablePrefix 表前缀
     * @param author 作者
     * @param enableCache 是否启用缓存
     */
    public void generator(String projectPath,String createName,String moudlePackageName,String jdbcUrl,String jdbcUsername,String jdbcPassword,
                          String include,String tablePrefix,String author, boolean enableCache,boolean createExportMethod,
                          boolean createImportMethod) {
        //全局配置
        globalConfig(getAutoGenerator(),projectPath,author,enableCache);
        // 数据源配置
        dataSourceConfig(getAutoGenerator(),jdbcUrl,jdbcUsername,jdbcPassword);
        // 包配置
        packageConfig(getAutoGenerator(), projectPath ,moudlePackageName);
        // 策略配置 数据库表配置
        strategyConfig(getAutoGenerator(),include,tablePrefix);
        // 自定义配置
        injectionConfig(getAutoGenerator(),createName,projectPath,
                moudlePackageName,createExportMethod,createImportMethod);
        getAutoGenerator().execute();
    }

    /**
     * 全局配置
     *
     * @param autoGenerator 自动生成全局设置类
     */
    private void globalConfig(AutoGenerator autoGenerator,String projectPath,String author, boolean enableCache) {
        getGlobalConfig().setOutputDir(projectPath + Constants.JAVa_path)  //生成文件的输出目录
                .setFileOverride(Constants.FALSE)  //是否覆盖已有文件 默认值：false
                .setOpen(Constants.TRUE)  //是否打开输出目录 默认值：true
                .setEnableCache(enableCache)   //是否在xml中添加二级缓存配置 默认值：false
                .setAuthor(author) //开发人员
                .setKotlin(Constants.FALSE)  //开启 Kotlin 模式 默认值：false
                .setSwagger2(Constants.TRUE) //开启 swagger2 模式 默认值：false
                .setActiveRecord(Constants.FALSE)  // 开启 ActiveRecord 模式 默认值：false
                .setBaseResultMap(Constants.TRUE)  //开启 BaseResultMap 默认值：false
                .setBaseColumnList(Constants.TRUE)  //开启 baseColumnList 默认值：false
                .setDateType(DateType.ONLY_DATE) //时间类型对应策略 默认值：TIME_PACK
                .setEntityName(Constants.ENTITY_name)  //实体命名方式 默认值：null 例如：%sEntity 生成 EmailUserInfoEntity
                .setMapperName(Constants.MAPPEr_name)  //mapper 命名方式 默认值：null 例如：%sMapper 生成 UserMapper
                .setXmlName(Constants.XML_name)  //Mapper xml 命名方式 默认值：null 例如：%sMapper 生成 UserMapper.xml
                .setServiceName(Constants.SERVICE_name)  //service 命名方式  默认值：null 例如：%sBusiness 生成 UserBusiness
                .setServiceImplName(Constants.SERVICE_IMPL_name)  //service impl 命名方式 默认值：null 例如：%sBusinessImpl 生成 UserBusinessImpl
                .setControllerName(Constants.CONTROLLEr_name);  //controller 命名方式 默认值：null 例如：%sAction 生成 UserAction
        autoGenerator.setGlobalConfig(getGlobalConfig());
    }

    /**
     * 数据源配置
     *
     * @param autoGenerator
     */
    private void dataSourceConfig(AutoGenerator autoGenerator,String jdbcUrl,String jdbcUsername,String jdbcPassword) {
        getDataSourceConfig().setDbType(DbType.ORACLE) // 数据库类型 该类内置了常用的数据库类型【必须】
                .setDriverName(codeGeneratorProperties.getDriverName()) //驱动名称
                .setUrl(jdbcUrl) //驱动连接的URL
                .setUsername(jdbcUsername)
                .setPassword(jdbcPassword);
        autoGenerator.setDataSource(getDataSourceConfig());
    }

    /**
     * 包配置
     *
     * @param autoGenerator
     */
    private void packageConfig(AutoGenerator autoGenerator,String projectPath,String moudlePackageName) {
        String outputDir = projectPath + Constants.JAVa_path;
        String outputXmlDir = projectPath + Constants.RESOURCE_PATH;
        getPackageConfig().setParent(codeGeneratorProperties.getParentPackageName()) //父包名。如果为空，将下面子包名必须写全部， 否则就只需写子包名
                .setModuleName(moudlePackageName)
                .setEntity(Constants.ENTITY_PACKAGE_name) //Entity包名
                .setService(Constants.SERVICE_PACKAGE_name) //Service包名
                .setServiceImpl(Constants.SERVICE_IMPL_PACKAGE_name) //Service Impl包名
                .setMapper(Constants.MAPPER_PACKAGE_name) //Mapper包名
                .setXml(Constants.XML_PACKAGE_name)  //Mapper XML包名
                .setController(Constants.CONTROLLER_PACKAGE_name); //Controller包名

        // DONE 修改设置mapper.xml生成类位置
        pathInfo.put(ConstVal.ENTITY_PATH,  outputDir + processPackage(getPackageConfig().getParent() + Constants.DOT +getPackageConfig().getEntity()));
        pathInfo.put(ConstVal.MAPPER_PATH,  outputDir + processPackage(getPackageConfig().getParent() + Constants.DOT  +getPackageConfig().getMapper()));
        pathInfo.put(ConstVal.XML_PATH, outputXmlDir + processPackage(getPackageConfig().getParent() + Constants.DOT  +getPackageConfig().getXml()));
        pathInfo.put(ConstVal.SERVICE_PATH, outputDir + processPackage(getPackageConfig().getParent() + Constants.DOT  +getPackageConfig().getService()));
        pathInfo.put(ConstVal.SERVICE_IMPL_PATH,  outputDir + processPackage(getPackageConfig().getParent() + Constants.DOT  +getPackageConfig().getServiceImpl()));
        pathInfo.put(ConstVal.CONTROLLER_PATH,  outputDir + processPackage(getPackageConfig().getParent() + Constants.DOT  +getPackageConfig().getController()));
        getPackageConfig().setPathInfo(pathInfo);
        autoGenerator.setPackageInfo(getPackageConfig());
    }

    /**
     * 策略配置 数据库表配置
     *
     * @param autoGenerator
     */
    private void strategyConfig(AutoGenerator autoGenerator,String include,String tablePrefix) {
        getStrategyConfig().setCapitalMode(Constants.TRUE)  //是否大写命名
                .setSkipView(Constants.TRUE) // 是否跳过视图
                .setNaming(NamingStrategy.underline_to_camel) //数据库表映射到实体的命名策略
                .setColumnNaming(NamingStrategy.underline_to_camel) //数据库表字段映射到实体的命名策略, 未指定按照 naming 执行
                .setTablePrefix(tablePrefix) //表前缀
                .setInclude(include)  // 需要包含的表名，允许正则表达式（与exclude二选一配置）
                .setEntityColumnConstant(Constants.FALSE) //【实体】是否生成字段常量
                .setEntityBuilderModel(Constants.FALSE)  //【实体】是否为构建者模型（默认 false）
                .setEntityLombokModel(Constants.TRUE) //【实体】是否为lombok模型（默认 false）
                .setEntityBooleanColumnRemoveIsPrefix(Constants.TRUE) //Boolean类型字段是否移除is前缀（默认 false）
                .setRestControllerStyle(Constants.TRUE) //生成 @RestController 控制器
                .setSuperEntityClass(codeGeneratorProperties.getSuperEntityClass()) //自定义继承的Entity类全称，带包名
                .setSuperMapperClass(codeGeneratorProperties.getSuperMapperClass()) //自定义继承的Mapper类全称，带包名
                .setSuperServiceClass(codeGeneratorProperties.getSuperServiceClass()) //自定义继承的Service类全称，带包名
                .setSuperServiceImplClass(codeGeneratorProperties.getSuperServiceImplClass()) //自定义继承的ServiceImpl类全称，带包名
                .setSuperControllerClass(codeGeneratorProperties.getSuperControllerClass());  //自定义继承的Controller类全称，带包名
        getStrategyConfig().setControllerMappingHyphenStyle(Constants.TRUE); //驼峰转连字符
        autoGenerator.setStrategy(getStrategyConfig());
    }

    /**
     * 注入配置，通过该配置，可注入自定义参数等操作以实现个性化操作
     *
     * @param autoGenerator
     */
    private void injectionConfig(AutoGenerator autoGenerator,String createName,String projectPath,
                                 String moudlePackageName,boolean createExportMethod,boolean createImportMethod) {
        String showName = captureName(createName);
        String outputDir = projectPath + Constants.JAVa_path;
        String DTOPackageName = codeGeneratorProperties.getParentPackageName() + Constants.DOT +moudlePackageName + ".web.dto";
        String DTORealPath = outputDir + processPackage(codeGeneratorProperties.getParentPackageName())
                + File.separator +processPackage(moudlePackageName) + "/web/dto";
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {  //注入自定义 Map 对象(注意需要setMap放进去)
                Map<String, Object> map = new HashMap<>();
                //注入传输层返回对象包名
                map.put("DTOPackageName", DTOPackageName);
                map.put("createName", createName);
                map.put("createExportMethod", createExportMethod);
                map.put("createImportMethod", createImportMethod);
                map.put("entityName", String.format(getGlobalConfig().getEntityName(),showName));
                map.put("mapperName", String.format(getGlobalConfig().getMapperName(),showName));
                map.put("serviceName", String.format(getGlobalConfig().getServiceName(),showName));
                map.put("serviceImplName", String.format(getGlobalConfig().getServiceImplName(),showName));
                map.put("controllerName", String.format(getGlobalConfig().getControllerName(),showName));
                this.setMap(map);
            }
        };
        // 如果模板引擎是 freemarker
        /*
            自定义输出文件  DONE 增加DTO层生成
            配置 FileOutConfig 指定模板文件、输出文件达到自定义文件生成目的
         */
        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        //添加返回结果DTO生成
        focList.add(new FileOutConfig(Constants.RESULT_DTO_PATH) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名
                return DTORealPath + File.separator + showName + "ResultDTO" + StringPool.DOT_JAVA;
            }
        });
        focList.add(new FileOutConfig(Constants.NEW_DTO_PATH) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名
                return DTORealPath + File.separator + showName + "NewDTO" + StringPool.DOT_JAVA;
            }
        });
        focList.add(new FileOutConfig(Constants.RENEW_DTO_PATH) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名
                return DTORealPath + File.separator + showName + "ReNewDTO" + StringPool.DOT_JAVA;
            }
        });
        focList.add(new FileOutConfig(Constants.PAGINATION_DTO_PATH) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名
                return DTORealPath + File.separator + showName + "PaginationDTO" + StringPool.DOT_JAVA;
            }
        });
        focList.add(new FileOutConfig(Constants.QUERY_DTO_PATH) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名
                return DTORealPath + File.separator + showName + "QueryDTO" + StringPool.DOT_JAVA;
            }
        });
        cfg.setFileOutConfigList(focList);
        autoGenerator.setCfg(cfg);
        //使用freemarder模板 DONE 修改生成类名称
        CustomFreemarkerTemplateEngine customFreemarkerTemplateEngine = new CustomFreemarkerTemplateEngine();
        customFreemarkerTemplateEngine.setName(showName);
        autoGenerator.setTemplateEngine(customFreemarkerTemplateEngine);
    }

    /**
     * 将字符串的首字母转大写
     * @param str 需要转换的字符串
     * @return
     */
    private String captureName(String str) {
        // 效率高的方法
        char[] chars = str.toCharArray();
        if (chars[0] >= 'a' && chars[0] <= 'z') {
            chars[0] = (char)(chars[0] - 32);
        }
        return new String(chars);
    }

    /**
     * 处理包名称
     * @param packageName
     * @return
     */
    private String processPackage(String packageName) {
        if (StringUtils.isBlank(packageName)) {
            packageName = System.getProperty("java.io.tmpdir");
        }
        if (!StringUtils.endsWith(packageName, File.separator)) {
            packageName = packageName + File.separator;
        }
        packageName = packageName.replaceAll("\\.", "\\" + File.separator);
        return packageName;
    }

    public AutoGenerator getAutoGenerator() {
        return autoGenerator;
    }

    public DataSourceConfig getDataSourceConfig() {
        return dataSourceConfig;
    }

    public GlobalConfig getGlobalConfig() {
        return globalConfig;
    }

    public PackageConfig getPackageConfig() {
        return packageConfig;
    }

    public TemplateConfig getTemplateConfig() {
        return templateConfig;
    }

    public StrategyConfig getStrategyConfig() {
        return strategyConfig;
    }

    public CodeGeneratorProperties getCodeGeneratorProperties() {
        return codeGeneratorProperties;
    }

    public void setCodeGeneratorProperties(CodeGeneratorProperties codeGeneratorProperties) {
        this.codeGeneratorProperties = codeGeneratorProperties;
    }
}