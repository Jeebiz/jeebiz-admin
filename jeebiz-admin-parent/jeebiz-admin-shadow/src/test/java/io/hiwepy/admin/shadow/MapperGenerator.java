package io.hiwepy.admin.shadow;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.TemplateType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

class MapperGenerator {

	/**
	 * 数据源配置
	 */
	private static final DataSourceConfig.Builder DATA_SOURCE_CONFIG = new DataSourceConfig.Builder(
			"jdbc:mysql://106.54.2.213:3306/jeebiz_admin?zeroDateTimeBehavior=convertToNull&useUnicode=true&characterEncoding=UTF-8&allowMultiQueries=true&serverTimezone=GMT%2B8",
			"jeebiz_admin", "5RcLHz6E3AS5LFDh");

	/**
	 * 执行 run
	 */
	public static void main(String[] args) throws SQLException {
		Map<OutputFile, String> map = new HashMap<>(4);
		map.put(OutputFile.mapperXml, "jeebiz-admin-extras-dict/src/main/resources/mapper/mysql");

		FastAutoGenerator.create(DATA_SOURCE_CONFIG)
				// 全局配置
				.globalConfig((scanner, builder) -> builder.author("wandl").fileOverride()
						.outputDir("jeebiz-admin-extras-dict/src/main/java"))
				// 包配置
				.packageConfig((scanner, builder) -> builder
						.parent("io.hiwepy.admin.extras.dict")
						.entity("dao.entities")
						.mapper("dao")
						.controller("web.mvc")
						.pathInfo(map))
				.templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
				.templateConfig((scanner, builder) -> {
					builder.serviceImpl()
					builder.disable(TemplateType.CONTROLLER, TemplateType.SERVICE, TemplateType.SERVICEIMPL);
				})
				// 策略配置
				.strategyConfig((scanner, builder) -> builder
						.addInclude(getTables(scanner.apply("请输入表名，多个英文逗号分隔？所有输入 all"))).entityBuilder().enableLombok()
						.formatFileName("%sEntity").enableChainModel().mapperBuilder().enableBaseColumnList()
						.enableBaseResultMap().formatMapperFileName("%sMapper").build())

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
