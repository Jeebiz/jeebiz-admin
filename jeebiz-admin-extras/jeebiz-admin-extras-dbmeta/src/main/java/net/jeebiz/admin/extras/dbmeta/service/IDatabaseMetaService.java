/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.dbmeta.service;

import java.sql.SQLException;
import java.util.List;

import net.jeebiz.admin.extras.dbmeta.dao.entities.DatabaseMetaModel;
import net.jeebiz.admin.extras.dbmeta.web.vo.TableColumnVo;
import net.jeebiz.admin.extras.dbmeta.web.vo.TableVo;
import net.jeebiz.boot.api.service.IBaseService;
import schemacrawler.schemacrawler.SchemaCrawlerException;

public interface IDatabaseMetaService extends IBaseService<DatabaseMetaModel> {

	/**
	 * 刷新缓存中的数据库元信息
	 * @throws Exception
	 */
	void crawlTables();
	
	/**
	 * 获取所有的表名与注释
	 * 
	 * @return
	 * @throws SchemaCrawlerException
	 * @throws SQLException
	 */
	public List<TableVo> getTables() throws SchemaCrawlerException, SQLException;

	/**
	 * 获取所有的表名与注释
	 * 
	 * @return
	 * @throws SchemaCrawlerException
	 * @throws SQLException
	 */
	public List<TableVo> getViews() throws SchemaCrawlerException, SQLException;

	/**
	 * 获取表中对应的字段与注释
	 * 
	 * @param table
	 * @return
	 */
	public List<TableColumnVo> getColumns(String table) throws SchemaCrawlerException, SQLException;

}
