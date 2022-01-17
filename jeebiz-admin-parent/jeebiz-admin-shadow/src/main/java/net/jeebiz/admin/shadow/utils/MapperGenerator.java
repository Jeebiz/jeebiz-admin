package net.jeebiz.admin.shadow.utils;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.TemplateType;

import java.sql.SQLException;
import java.util.*;

public final class MapperGenerator {

    /**
     * 数据源配置
     */
    private static final DataSourceConfig.Builder DATA_SOURCE_CONFIG = new DataSourceConfig
            .Builder("jdbc:mysql://106.54.2.213:3306/jeebiz_admin?useSSL=false",
            "jeebiz_admin",
            "5RcLHz6E3AS5LFDh");

    /**
     * 执行 run
     */
    public static void main(String[] args) throws SQLException {
        Map<OutputFile, String> map = new HashMap<>(4);
        map.put(OutputFile.mapperXml, "jeebiz-admin-shadow/src/main/resources/mapper");

        FastAutoGenerator.create(DATA_SOURCE_CONFIG)
                // 全局配置
                .globalConfig((scanner, builder) -> builder.author("wandl").fileOverride().outputDir("jeebiz-admin-parent/jeebiz-admin-shadow/src/main/java"))
                // 包配置
                .packageConfig((scanner, builder) -> builder.parent("net.jeebiz.admin.shadow.dao")
                        .entity("entity")
                        .mapper("dao")
                        .pathInfo(map)
                )
                .templateConfig((scanner, builder) -> builder.disable(TemplateType.CONTROLLER, TemplateType.SERVICE, TemplateType.SERVICEIMPL))
                // 策略配置
                .strategyConfig((scanner, builder) -> builder.addInclude(getTables(scanner.apply("请输入表名，多个英文逗号分隔？所有输入 all")))
                        .entityBuilder().enableLombok().formatFileName("%sEntity").enableChainModel()
                        .mapperBuilder().enableBaseColumnList().enableBaseResultMap().formatMapperFileName("%sDAO")
                        .build())

                .execute();
    }

    /**
     * 处理 all 情况
     *
     * @param tables
     * @return
     */
    private static List<String> getTables(String tables) {
        return "all".equals(tables) ? Collections.emptyList() : Arrays.asList(tables.split(","));
    }
}

