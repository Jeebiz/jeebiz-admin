package net.jeebiz.admin.shadow.setup.config;

import javax.sql.DataSource;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import com.baomidou.mybatisplus.core.incrementer.IKeyGenerator;
import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.incrementer.H2KeyGenerator;
import com.baomidou.mybatisplus.extension.injector.LogicSqlInjector;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;

@Configuration
@AutoConfigureAfter( name = {
	"com.baomidou.mybatisplus.spring.boot.starter.MybatisPlusAutoConfiguration"
})
@MapperScan({"net.jeebiz.**.dao"})  
public class MybatisPlusConfig {
	
	
	// 其中 dataSource 框架会自动为我们注入
    @Bean
    public PlatformTransactionManager txManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
    
    
   /**
    * mybatis-plus SQL执行效率插件【生产环境可以关闭】
    */
   @Bean
   public PerformanceInterceptor performanceInterceptor() {
       return new PerformanceInterceptor();
   }

   /**
    * mybatis-plus分页插件<br>
    * 文档：http://mp.baomidou.com<br>
    */
   @Bean
   public PaginationInterceptor paginationInterceptor() {
       PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
       /*
        * 【测试多租户】 SQL 解析处理拦截器<br>
        * 这里固定写成住户 1 实际情况你可以从cookie读取，因此数据看不到 【 麻花藤 】 这条记录（ 注意观察 SQL ）<br>
        */
    /*   List<ISqlParser> sqlParserList = new ArrayList<>();
       TenantSqlParser tenantSqlParser = new TenantSqlParser();
       tenantSqlParser.setTenantHandler(new TenantHandler() {
    	   
           @Override
           public Expression getTenantId() {
               return new LongValue(1L);
           }

           @Override
           public String getTenantIdColumn() {
               return "tenant_id";
           }

           @Override
           public boolean doTableFilter(String tableName) {
               // 这里可以判断是否过滤表
               
               if ("user".equals(tableName)) {
                   return true;
               }
               return false;
           }
       });


       sqlParserList.add(tenantSqlParser);
       paginationInterceptor.setSqlParserList(sqlParserList);*/
       // 以下过滤方式与 @SqlParser(filter = true) 注解等效
//       paginationInterceptor.setSqlParserFilter(new ISqlParserFilter() {
//           @Override
//           public boolean doFilter(MetaObject metaObject) {
//               MappedStatement ms = PluginUtils.getMappedStatement(metaObject);
//               // 过滤自定义查询此时无租户信息约束【 麻花藤 】出现
//               if ("com.baomidou.springboot.mapper.UserMapper.selectListBySQL".equals(ms.getId())) {
//                   return true;
//               }
//               return false;
//           }
//       });
       return paginationInterceptor;
   }

   /**
    * 注入主键生成器
    */
   @Bean
   public IKeyGenerator keyGenerator(){
       return new H2KeyGenerator();
   }

   /**
    * 注入sql注入器
    */
   @Bean
   public ISqlInjector sqlInjector(){
       return new LogicSqlInjector();
   }

   
   /*
    * oracle数据库配置JdbcTypeForNull
    * 参考：https://gitee.com/baomidou/mybatisplus-boot-starter/issues/IHS8X
    不需要这样配置了，参考 yml:
    mybatis-plus:
      confuguration
        dbc-type-for-null: 'null' 
   @Bean
   public ConfigurationCustomizer configurationCustomizer(){
       return new MybatisPlusCustomizers();
   }

   class MybatisPlusCustomizers implements ConfigurationCustomizer {

       @Override
       public void customize(org.apache.ibatis.session.Configuration configuration) {
           configuration.setJdbcTypeForNull(JdbcType.NULL);
       }
   }
   */
}