/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.core.setup.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.mybatis.dbperms.MybatisDbpermsProperties;
import org.apache.mybatis.dbperms.dto.DataPermission;
import org.apache.mybatis.dbperms.dto.DataPermissionPayload;
import org.apache.mybatis.dbperms.dto.DataSpecialPermission;
import org.apache.mybatis.dbperms.parser.DefaultTablePermissionAutowireHandler;
import org.apache.mybatis.dbperms.parser.ITablePermissionAutowireHandler;
import org.apache.shiro.biz.authz.principal.ShiroPrincipal;
import org.apache.shiro.biz.utils.SubjectUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;

import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.core.parser.ISqlParser;
import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.SqlExplainInterceptor;
import com.github.dozermapper.core.Mapper;

import net.jeebiz.admin.extras.core.service.IDataPermissionQueryService;
import net.jeebiz.boot.api.utils.Constants;

@Configuration
@MapperScan({"net.jeebiz.**.dao", "net.jeebiz.**repository"})
@EnableConfigurationProperties(MybatisDbpermsProperties.class)
public class MybatisPlusConfiguration {

	protected static Logger LOG = LoggerFactory.getLogger(MybatisPlusConfiguration.class);
	
	@Autowired
    private RedisTemplate<String, Object> redisTemplate;
	@Autowired
	private Mapper beanMapper; 
	
	/**
	 * mybatis-dbperms 数据权限插件
	 */
	@Bean
    public ITablePermissionAutowireHandler tablePermissionAutowireHandler(IDataPermissionQueryService dataPermissionQuery, MybatisDbpermsProperties properties) {
    	
    	BoundHashOperations<String, Object, Object> opsForHash = getRedisTemplate().boundHashOps(Constants.PERMS_CACHE_KEY);
        // opsForHash.expire(properties.getExpire(), properties.getUnit());
        
    	return new DefaultTablePermissionAutowireHandler((metaHandler, tableName) -> {
    		
    		// 获取当前登录用户主体对象
        	ShiroPrincipal principal = SubjectUtils.getPrincipal(ShiroPrincipal.class);
        	
        	// admin 不做数据权限处理
        	if(null == principal || principal.isAdmin()) {
        		LOG.debug("当前角色是超级管理员，不做数据权限限制！");
        		return Optional.empty();
        	}

    		LOG.debug("处理表{}数据权限！", tableName);
    		
    		DataPermissionPayload payload = null;
        	// 根据当前角色ID和用户ID,构建Hashkey
        	String permsHashKey = String.format(Constants.PERMS_CACHE_HASH, principal.getRoleid(), principal.getUserid());
        	// 检查缓存是否有数据
        	if (opsForHash.hasKey(permsHashKey)) {
        		payload = (DataPermissionPayload) opsForHash.get(permsHashKey);
        		if (null != payload) {
        			LOG.debug("load Permission from redis !");
        			return Optional.of(payload);
        		}
            	return Optional.empty();
            }
        	// 缓存中无数据，尝试从数据库查询
            if(null == payload) {
            	
            	payload = new DataPermissionPayload();
            	
            	// 根据当前角色和用户查询数据库对应的数据权限
            	List<DataPermission> permissions = dataPermissionQuery.getPermissions(principal.getRoleid(), principal.getUserid());
            	List<DataSpecialPermission> specialPermissions = dataPermissionQuery.getSpecialPermissions(principal.getRoleid(), principal.getUserid());
            	if (!CollectionUtils.isEmpty(permissions) || !CollectionUtils.isEmpty(specialPermissions)) {

                	payload.setPermissions(permissions);
                	payload.setSpecialPermissions(specialPermissions);
                    opsForHash.put(permsHashKey, payload);
                     
                	LOG.debug("load Data Permission from datasource !");
        			return Optional.of(payload);
            	} else {
                	opsForHash.put(permsHashKey, payload);
				}
            }
    		return Optional.empty();
    	});
    }

	/**
	 * 乐观锁插件
	 * https://mp.baomidou.com/guide/optimistic-locker-plugin.html#%E4%B8%BB%E8%A6%81%E9%80%82%E7%94%A8%E5%9C%BA%E6%99%AF
	 * 
	 * @return
	 */
	@Bean
	public OptimisticLockerInterceptor optimisticLockerInterceptor() {
		return new OptimisticLockerInterceptor();
	}

	/**
	 * mybatis-plus分页插件<br>
	 * 文档：http://mp.baomidou.com<br>
	 */
	@Bean
	public PaginationInterceptor paginationInterceptor() {
		PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
		paginationInterceptor.setOverflow(true);

		List<ISqlParser> sqlParserList = new ArrayList<>();
		// 攻击 SQL 阻断解析器、加入解析链
		/// sqlParserList.add(new BlockAttackSqlParser());
		paginationInterceptor.setSqlParserList(sqlParserList);

		/*
		 * 【测试多租户】 SQL 解析处理拦截器<br> 这里固定写成住户 1 实际情况你可以从cookie读取，因此数据看不到 【 麻花藤 】 这条记录（
		 * 注意观察 SQL ）<br>
		 */
		/*
		 * List<ISqlParser> sqlParserList = new ArrayList<>(); TenantSqlParser
		 * tenantSqlParser = new TenantSqlParser(); tenantSqlParser.setTenantHandler(new
		 * TenantHandler() {
		 * 
		 * @Override public Expression getTenantId() { return new LongValue(1L); }
		 * 
		 * @Override public String getTenantIdColumn() { return "tenant_id"; }
		 * 
		 * @Override public boolean doTableFilter(String tableName) { // 这里可以判断是否过滤表
		 * 
		 * if ("user".equals(tableName)) { return true; } return false; } });
		 * 
		 * 
		 * sqlParserList.add(tenantSqlParser);
		 * paginationInterceptor.setSqlParserList(sqlParserList);
		 */
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
	 * 注入sql注入器
	 */
	@Bean
	public ISqlInjector sqlInjector() {
		return new DefaultSqlInjector();
	}
	
	/**
	 * 注入主键生成器
	@Bean
	public IKeyGenerator keyGenerator() {
		return new H2KeyGenerator();
	}
	*/
	
	@Bean
	public SqlExplainInterceptor sqlExplainInterceptor() {
		// 启用执行分析插件
		SqlExplainInterceptor sqlExplainInterceptor = new SqlExplainInterceptor();
		return sqlExplainInterceptor;
	}

	/*
	 * oracle数据库配置JdbcTypeForNull
	 * 参考：https://gitee.com/baomidou/mybatisplus-boot-starter/issues/IHS8X
	 * 不需要这样配置了，参考 yml: mybatis-plus: confuguration dbc-type-for-null: 'null'
	 * 
	 * @Bean public ConfigurationCustomizer configurationCustomizer(){ return new
	 * MybatisPlusCustomizers(); }
	 * 
	 * class MybatisPlusCustomizers implements ConfigurationCustomizer {
	 * 
	 * @Override public void customize(org.apache.ibatis.session.Configuration
	 * configuration) { configuration.setJdbcTypeForNull(JdbcType.NULL); } }
	 */

    public RedisTemplate<String, Object> getRedisTemplate() {
		return redisTemplate;
	}
    
    public Mapper getBeanMapper() {
		return beanMapper;
	}
    
}