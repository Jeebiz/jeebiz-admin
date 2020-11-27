/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.dbmeta.service.impl;

import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import net.jeebiz.admin.extras.dbmeta.dao.IDatabaseMetaDao;
import net.jeebiz.admin.extras.dbmeta.dao.entities.DatabaseMetaModel;
import net.jeebiz.admin.extras.dbmeta.service.IDatabaseMetaService;
import net.jeebiz.admin.extras.dbmeta.setup.DataSourceCrawlTemplate;
import net.jeebiz.admin.extras.dbmeta.setup.TableColumnComparator;
import net.jeebiz.admin.extras.dbmeta.setup.TableComparator;
import net.jeebiz.admin.extras.dbmeta.web.dto.TableColumnDTO;
import net.jeebiz.admin.extras.dbmeta.web.dto.TableDTO;
import net.jeebiz.boot.api.dao.entities.PairModel;
import net.jeebiz.boot.api.service.BaseServiceImpl;
import schemacrawler.schema.Column;
import schemacrawler.schema.Table;
import schemacrawler.schema.View;
import schemacrawler.schemacrawler.SchemaCrawlerException;

@Service
public class DatabaseMetaServiceImpl extends BaseServiceImpl<DatabaseMetaModel, IDatabaseMetaDao>
		implements IDatabaseMetaService {
	
	private Comparator<TableDTO> tableComparator = new TableComparator();
	private Comparator<TableColumnDTO> columnComparator = new TableColumnComparator();

	@Autowired
	private DataSourceCrawlTemplate dataSourceCrawlTemplate;
	
	@Override
	public void crawlTables()  {
		new Thread() {
			@Override
			public void run() {
				try {
					List<PairModel> dsList = getDao().getPairValues("");
			    	for(PairModel model : dsList) {
			    		getDataSourceCrawlTemplate().removeTables(model.getKey());
			    		getDataSourceCrawlTemplate().crawlTables(model.getKey());
			    	}
				} catch (SchemaCrawlerException | SQLException e) {
				}
			};
		}.start();
    }

	@Override
	public List<TableDTO> getTables() throws SchemaCrawlerException, SQLException {
		List<TableDTO> tables = Lists.newArrayList();
		try {
			for (final Table table : getDataSourceCrawlTemplate().crawlTables("default")) {
				if (!(table instanceof View)) {
					tables.add(new TableDTO(table.getName(), table.getRemarks()));
				}
			}
			tables.sort(tableComparator);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tables;
	}

	@Override
	public List<TableDTO> getViews() throws SchemaCrawlerException, SQLException {
		List<TableDTO> tables = Lists.newArrayList();
		try {
			for (final Table table : getDataSourceCrawlTemplate().crawlTables("default")) {
				if (table instanceof View) {
					tables.add(new TableDTO(table.getName(), table.getRemarks()));
				}
			}
			tables.sort(tableComparator);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tables;
	}

	@Override
	public List<TableColumnDTO> getColumns(String tablename) throws SchemaCrawlerException, SQLException {
		List<TableColumnDTO> columns = Lists.newArrayList();
		try {
			for (final Table table : getDataSourceCrawlTemplate().crawlTables("default")) {
				if (table.getName().equalsIgnoreCase(tablename)) {
					for (Column column : table.getColumns()) {

						TableColumnDTO columnDTO = new TableColumnDTO(column.getName(), column.getRemarks(),
								column.getOrdinalPosition(), column.getType().getName(), column.getSize(),
								column.getDefaultValue());

						columns.add(columnDTO);
					}
				}
			}
			columns.sort(columnComparator);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return columns;
	}

	public DataSourceCrawlTemplate getDataSourceCrawlTemplate() {
		return dataSourceCrawlTemplate;
	}
	
}
