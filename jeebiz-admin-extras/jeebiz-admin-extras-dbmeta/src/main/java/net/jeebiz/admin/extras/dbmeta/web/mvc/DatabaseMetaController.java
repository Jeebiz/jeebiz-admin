/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.dbmeta.web.mvc;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import net.jeebiz.admin.extras.dbmeta.service.IDatabaseMetaService;
import net.jeebiz.admin.extras.dbmeta.web.dto.TableColumnDTO;
import net.jeebiz.admin.extras.dbmeta.web.dto.TableDTO;
import net.jeebiz.boot.api.ApiRestResponse;
import net.jeebiz.boot.api.web.BaseApiController;
import schemacrawler.schemacrawler.SchemaCrawlerException;

@Api(tags = "数据表元数据（Ok）")
@RestController
@RequestMapping("/dbmeta/")
public class DatabaseMetaController extends BaseApiController {

	@Autowired
	private IDatabaseMetaService databaseMetaService;

	/**
	 * 查询指定数据源下的数据表信息
	 * 
	 * @return 数据表信息
	 * @throws SchemaCrawlerException
	 * @throws SQLException
	 */
	@ApiOperation(value = "数据表信息", notes = "查询指定数据源下的数据表信息")
	@GetMapping("tables")
	@RequiresAuthentication
	@ResponseBody
	public ApiRestResponse<List<TableDTO>> tables() throws SchemaCrawlerException, SQLException {
		return ApiRestResponse.success(getDatabaseMetaService().getTables());
	}

	/**
	 * 查询指定数据源下指定的视图信息
	 * 
	 * @return 视图信息
	 * @throws SchemaCrawlerException
	 * @throws SQLException
	 */
	@ApiOperation(value = "视图信息", notes = "查询指定数据源下指定的视图信息")
	@GetMapping("views")
	@RequiresAuthentication
	@ResponseBody
	public ApiRestResponse<List<TableDTO>> views() throws SchemaCrawlerException, SQLException {
		return ApiRestResponse.success(getDatabaseMetaService().getViews());
	}

	/**
	 * 查询指定数据源、数据表对应的列信息
	 * 
	 * @param table 数据表名称
	 * @return 列信息
	 * @throws SchemaCrawlerException
	 * @throws SQLException
	 */
	@ApiOperation(value = "列信息数据", notes = "查询指定数据源、数据表对应的列信息")
	@ApiImplicitParams({ 
		@ApiImplicitParam(name = "table", value = "数据表名称", dataType = "String", required = true) 
	})
	@GetMapping("columns")
	@RequiresAuthentication
	@ResponseBody
	public ApiRestResponse<List<TableColumnDTO>> tableColumns(@RequestParam("table") String table) throws SchemaCrawlerException, SQLException {
		if (StringUtils.isEmpty(table)) {
			return ApiRestResponse.success(Lists.newArrayList());
		}
		return ApiRestResponse.success(getDatabaseMetaService().getColumns(table));
	}

	public IDatabaseMetaService getDatabaseMetaService() {
		return databaseMetaService;
	}

}
