package net.jeebiz.admin.extras.dbmeta.setup;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import javax.sql.DataSource;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import schemacrawler.inclusionrule.IncludeAll;
import schemacrawler.inclusionrule.InclusionRule;
import schemacrawler.inclusionrule.RegularExpressionInclusionRule;
import schemacrawler.schema.Catalog;
import schemacrawler.schema.Table;
import schemacrawler.schemacrawler.SchemaCrawlerException;
import schemacrawler.schemacrawler.SchemaCrawlerOptions;
import schemacrawler.schemacrawler.SchemaInfoLevel;
import schemacrawler.schemacrawler.SchemaInfoLevelBuilder;
import schemacrawler.spring.boot.utils.SchemaCrawlerOptionBuilder;
import schemacrawler.utility.SchemaCrawlerUtility;

public class DataSourceCrawlTemplate {

	private DataSource dataSource;
	private Cache<String, Catalog> catalogCache = CacheBuilder.newBuilder().expireAfterAccess(1, TimeUnit.HOURS)
			.build();
	
	public DataSourceCrawlTemplate(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public SchemaCrawlerOptions tables(InclusionRule schemaInclusionRule) {
		
		// Set what details are required in the schema - this affects the time taken to crawl the schema
		SchemaInfoLevel schemaInfoLevel = SchemaInfoLevelBuilder.standard()
			/*.setRetrieveAdditionalColumnAttributes(true)
			.setRetrieveAdditionalTableAttributes(true)
			.setRetrieveColumnDataTypes(true)
			.setRetrieveIndexes(false)
			.setRetrieveRoutineColumns(true)
			.setRetrieveTableColumns(true)
			.setRetrieveUserDefinedColumnDataTypes(true)
			.setRetrieveViewInformation(true)
			.toOptions()*/;
		final SchemaCrawlerOptions options = SchemaCrawlerOptionBuilder
				.tablecolumns(schemaInclusionRule, "TABLE", "VIEW" )
				//.includeAllRoutines()
				//.includeTables(new IncludeAll())
				//.includeColumns(new IncludeAll())
				//.tableNamePattern("%")
				//.tableTypes(Arrays.asList("TABLE","VIEW"))
				//.withSchemaInfoLevel(schemaInfoLevel)
				.toOptions();
		
		return options;
	}
	
	public void removeTables(String uid) {
		if(catalogCache.getIfPresent(uid) != null) {
			catalogCache.invalidate(uid);
		}
	}
		
	public Collection<Table> crawlTables(String uid) throws SchemaCrawlerException, SQLException {
		
		try {

			Catalog catalog = catalogCache.get(uid, new Callable<Catalog>() {

				@Override
				public Catalog call() throws Exception {

					Connection connection = dataSource.getConnection();
					
					Catalog catalog = SchemaCrawlerUtility.getCatalog(connection, tables(new RegularExpressionInclusionRule(connection.getSchema() + ".*")));

					return catalog;
				}
			});
			return catalog.getTables();
		} catch (ExecutionException e) {
			throw new SchemaCrawlerException(e.getMessage(), e);
		}
	}
	
}
