
#Jeebiz 项目体系结构

|--jeebiz-admin		: Jeebiz 应用快速开发平台（基于Spring Boot、Spring Cloud）[付费授权]
	|--jeebiz-admin-dependencies	:  通用依赖
	|--jeebiz-admin-doc				:  文档资料
	|--jeebiz-admin-extras			:  功能模块
		|--jeebiz-admin-extras-actuator	 		:  平台内置的监控模块[会员独享]
		|--jeebiz-admin-extras-basedata    		:  平台内置的基础数据模块
		|--jeebiz-admin-extras-antisamy    		:  平台内置的基于Antisamy的XSS防护模块[会员独享]
		|--jeebiz-admin-extras-authc-oltu		:  平台内置的基于shiro + oltu 实现的资源授权模块[会员独享]
		|--jeebiz-admin-extras-authc-uaa		:  平台内置的基于shiro + oltu + jwt 实现的资源授权模块[会员独享]
		|--jeebiz-admin-extras-authz-dbperms	:  基于数据库驱动实现的数据权限模块[独立付费授权]
		|--jeebiz-admin-extras-authz-rbac1		:  基于RBAC模型的功能权限模块，解决多角色授权模块[独立付费授权]
		|--jeebiz-admin-extras-authz-rbac2		:  基于RBAC模型的功能权限模块，解决多角色、用户组授权模块[独立付费授权]
		|--jeebiz-admin-extras-authz-rbac3		:  基于RBAC模型的功能权限模块，解决多角色、用户组、组织机构授权模块[独立付费授权]
		|--jeebiz-admin-extras-authz-rbacx		:  基于RBAC模型的功能权限模块，解决各类复杂授权模块[独立付费授权]
		|--jeebiz-admin-extras-dataset    		:  基于数据源的API快速构建模块[独立付费授权]
		|--jeebiz-admin-extras-datasource  		:  基于数据源的动态数据源模块[独立付费授权]
		|--jeebiz-admin-extras-editor    		:  平台内置的UEditor存储服务模块，可对接第三方云服务[会员独享]
		|--jeebiz-admin-extras-fetion    		:  基于飞信的短信服务模块[独立付费授权]
		|--jeebiz-admin-extras-formio   		:  基于Formio的表单快速构建模块[独立付费授权]
		|--jeebiz-admin-extras-http-proxy  		:  基于Http协议的第三方接口整合模块,解决统一授权、提高整合效率[独立付费授权]
		|--jeebiz-admin-extras-i18n	    		:  平台内置的国际化模块
		|--jeebiz-admin-extras-inform    		:  平台内置的消息通知模块
		|--jeebiz-admin-extras-layim    		:  基于LayIM的在线客服模块[独立付费授权]
		|--jeebiz-admin-extras-logbiz    		:  平台内置的安全审计模块[会员独享]
		|--jeebiz-admin-extras-myapp    		:  平台内置的我的应用块
		|--jeebiz-admin-extras-search			:  基于横向、纵向维度的数据统计分析检索模块[独立付费授权]
		|--jeebiz-admin-extras-sessions    		:  平台内置的会话管理模块[会员独享]
		|--jeebiz-admin-extras-soap-proxy  		:  基于Soap协议的第三方接口整合模块,解决统一授权、提高整合效率[独立付费授权]
		|--jeebiz-admin-extras-timeline    		:  平台内置的时间线模块
		|--jeebiz-admin-extras-webui    		:  基于界面配置的普通CRUD功能配置模块[独立付费授权]
		|...
	|--jeebiz-admin-parent		:  
		|--jeebiz-admin-ui-adminlte		:  基于AdminLTE实现的管理界面		
		|--jeebiz-admin-ui-element  	:  基于Element-UI实现的管理界面	（前后端分离）
		|--jeebiz-admin-ui-iview		:  基于iView-UI实现的管理界面（前后端分离）
		|--jeebiz-admin-ui-layui		:  基于LayUI实现的管理界面（前后端分离）
		|--jeebiz-admin-ui-layui-sts	:  基于LayUI实现的管理界面
		|--jeebiz-admin-ui-mui			:  基于Mui实现的移动端管理界面
	|--jeebiz-admin-plugins
		|--jeebiz-admin-plugin-api		:  基于Pf4j插件定义的通用接口和对象，使平台具备插件扩展能力			
|--jeebiz-authc 	: Jeebiz 认证服务
	|--jeebiz-cas[付费授权]
	|--jeebiz-jwt[付费授权]
	|--jeebiz-ldap[开源免费]
	|--jeebiz-oauth2[付费授权]
	|--jeebiz-oidc[付费授权]
	|--jeebiz-openid[付费授权]
	|...
|--jeebiz-autotest 	: Jeebiz 自动化测试[付费授权]
|--jeebiz-boot		: Jeebiz Spring Boot 开发整合（基于Spring Boot 2.x ）[开源免费]	
	|--jeebiz-boot-project		:  核心模块，整合常用依赖、通用实现		
		|--jeebiz-boot-dependencies	:  通用依赖配置和插件配置
		|--jeebiz-boot-modules
			|--jeebiz-boot-api				:  基础接口和通用对象
			|--jeebiz-boot-authz-feature 	:  功能菜单模块
			|--jeebiz-boot-authz-rbac0  	:  Rbac权限管理模块			
			|--jeebiz-boot-autoconfigure    :  功能自动初始化模块
		|--jeebiz-boot-parent
		|--jeebiz-boot-starters
			|--...
	|--jeebiz-boot-samples		:  项目示例，基于Druid、HikariCP两种数据源的多种示例
		|--jeebiz-boot-sample-druid
		|--jeebiz-boot-sample-druid-activemq
		|--jeebiz-boot-sample-druid-amqp
		|--jeebiz-boot-sample-druid-rocketmq
		|--jeebiz-boot-sample-druid-shiro
		|--jeebiz-boot-sample-druid-war
		|--jeebiz-boot-sample-hikaricp
		|--jeebiz-boot-sample-hikaricp-activemq
		|--jeebiz-boot-sample-hikaricp-amqp
		|--jeebiz-boot-sample-hikaricp-rocketmq
		|--jeebiz-boot-sample-hikaricp-shiro
		|--jeebiz-boot-sample-hikaricp-war
		|--jeebiz-boot-sample-flowable
		|--jeebiz-boot-sample-flowable-app
		|--...		
|--jeebiz-cloud		: Jeebiz 微服务开发整合（基于Spring Cloud）[开源免费]
	|--jeebiz-cloud-api
	|--jeebiz-cloud-dependencies
	|--jeebiz-cloud-parent
	|--jeebiz-cloud-projects
		|--jeebiz-cloud-admin-consul			:  Jeebiz 基于Spring Boot Admin的微服务监控（Consul）
		|--jeebiz-cloud-admin-eureka			:  Jeebiz 基于Spring Boot Admin的微服务监控（Eureka）
		|--jeebiz-cloud-admin-zookeeper			:  Jeebiz 基于Spring Boot Admin的微服务监控（Zookeeper）
		|--jeebiz-cloud-gateway-consul			:  Jeebiz 基于Spring Cloud的微服务网关（Consul）
		|--jeebiz-cloud-gateway-eureka			:  Jeebiz 基于Spring Cloud的微服务网关（Eureka）
		|--jeebiz-cloud-gateway-zookeeper		:  Jeebiz 基于Spring Cloud的微服务网关（Zookeeper）
		|--jeebiz-cloud-gateway-zuul-consul		:  Jeebiz 基于Spring Cloud + Zuul的微服务网关（Consul）
		|--jeebiz-cloud-gateway-zuul-eureka		:  Jeebiz 基于Spring Cloud + Zuul的微服务网关（Eureka）
		|--jeebiz-cloud-gateway-zuul-zookeeper	:  Jeebiz 基于Spring Cloud + Zuul的微服务网关（Zookeeper）
		|--jeebiz-cloud-hystrix-consul			:  Jeebiz 基于Hystrix的服务熔断监控（Consul）
		|--jeebiz-cloud-hystrix-eureka			:  Jeebiz 基于Hystrix的服务熔断监控（Eureka）
		|--jeebiz-cloud-hystrix-zookeeper		:  Jeebiz 基于Hystrix的服务熔断监控（Zookeeper）
		|--jeebiz-cloud-oauth2-consul			:  Jeebiz 基于Oauth2.x的服务授权实现（Consul）
		|--jeebiz-cloud-oauth2-eureka			:  Jeebiz 基于Oauth2.x的服务授权实现（Eureka）
		|--jeebiz-cloud-oauth2-zookeeper		:  Jeebiz 基于Oauth2.x的服务授权实现（Zookeeper）
		|--jeebiz-cloud-registry-consul			:  Jeebiz 服务注册中心（Consul）
		|--jeebiz-cloud-registry-eureka			:  Jeebiz 服务注册中心（Eureka）
		|--jeebiz-cloud-registry-zookeeper		:  Jeebiz 服务注册中心（Zookeeper）
		|--jeebiz-cloud-scca-consul				:  Jeebiz 服务配置中心（Consul）
		|--jeebiz-cloud-scca-eureka				:  Jeebiz 服务配置中心（Eureka）
|--jeebiz-desktop	: Jeebiz 桌面应用快速开发平台
	|--jeebiz-desktop-ui
	|--jeebiz-desktop-plugin
	|--jeebiz-desktop-admin
	|--jeebiz-desktop-projects		: 
		|--jeebiz-im	
|--jeebiz-devkit	: Jeebiz 快速开发工具集
|--jeebiz-projects	: Jeebiz 应用开发平台实现的通用项目
	|--jeebiz-community	 		:  Jeebiz 社区论坛
	|--jeebiz-crawler	 		:  网络爬虫服务: 侧重数据的来源、清洗、存储
	|--jeebiz-docx				:  在线文档转换服务
	|--jeebiz-initializr 		:  基于Spring initializr 项目二次开发的快速项目构建服务
	|--jeebiz-flowable	 		:Jeebiz 工作流平台
	|--jeebiz-ivcp				:  网络视频电话、会议: 智能语音呼叫平台
	|--jeebiz-okapi-basic		:  数据开放平台基础版
	|--jeebiz-okapi-pro			:  数据开放平台专业版
	|--jeebiz-okapi-ultimate	:  数据开放平台高级版	
	|--jeebiz-datasync			:  数据同步服务
	|--jeebiz-okserv			:  微服务构建平台
	|--jeebiz-opencv			:  图片识别、人脸识别等
	|--jeebiz-portal			:  Jeebiz 服务平台
	|--jeebiz-apps				:  Jeebiz 应用商城
	|--...
|--jeebiz-ui		: Jeebiz UI依赖资源整合
|--jeebiz-weixin	: Jeebiz 微信快速开发平台
|--jeebiz-wxlite	: Jeebiz 小程序快速开发平台

		

		
		