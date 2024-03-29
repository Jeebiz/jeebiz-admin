## 项目简介：

> [danger] jeebiz-admin-shadow 是各业务模块整合运行的服务整体！

- 1、继承 Spring Boot 的 各种特性

- 2、集成主流技术：Mybatis-Plus、J2cache、Redis、Spring Security、Swagger、RocketMQ

- 3、自带通用实现：认证授权模块、人脸识别服务、文件存储服务、工作流服务、消息通知服务、安全审计服务、数据字典服务、代码生成服务等；

- 4、应用自动初始化：基于Flyway实现数据库脚本版本管理和脚本自动升级能力；从技术上解决项目启动前必须先初始化数据库和不断的增量升级过程中可能出现的数据库异常；

- 5、脚本化部署：自动构建可运行的脚本化部署程序；

- 6、Docker镜像支持：支持基于Dockerfile手动构建Docker镜像到Docker Harbor；借助开源软件开源软件 Jenkins + Harbor + Rancher ，实现基于Docker的运维支撑；

## 项目部署

默认项目构建使用Maven插件构建出2种可脚本部署的服务，以 jeebiz-admin-shadow 服务为例，结构如下：

### 1、Java Service Wrapper 服务

```shell
|--jeebiz-admin-shadow
|----bin 
|------jeebiz-boot                       #服务启停shell脚本
|------jeebiz-boot.bat                   #服务启停bat脚本
|------wrapper-linux-x86-32             #linux x86 32位环境服务包装文件
|------wrapper-linux-x86-64             #linux x86 64位环境服务包装文件
|------wrapper-windows-x86-32.exe       #windows x86 32位环境服务包装文件
|------wrapper-windows-x86-64.exe       #windows x86 64位环境服务包装文件
|----conf
|------application.yaml                 #应用配置yaml文件
|------bootstrap.properties             #应用配置properties文件
|------wrapper.conf                     #应用wrapper配置，可在此文件调整JVM参数
|----lib
|------jeebiz-admin-shadow-1.0.1-ce-SNAPSHOT.jar    #应用程序jar文件
|------libwrapper-linux-x86-32.so               #linux x86 32位环境服务包装动态库
|------libwrapper-linux-x86-64.so               #linux x86 64位环境服务包装动态库
|------wrapper.jar                              #应用程序包装jar文件
|------wrapper-windows-x86-32.dll               #windows x86 32位环境服务包装动态库
|------wrapper-windows-x86-64.dll               #windows x86 64位环境服务包装动态库
|----logs       #日志目录，默认日志将输出到这里
|----tmp        #临时目录
```

在bin目录下执行 ./jeebiz-boot 可输出使用提示：

```
Usage: ./jeebiz-boot { console | start | stop | restart | status | dump }
```
|  命令 |  说明 |
| ------------ | ------------ |
| ./jeebiz-boot console  |  以控制台模式启动服务，日志直接输出到控制台  |
| ./jeebiz-boot start  |  以后台服务模式启动服务  |
| ./jeebiz-boot stop  | 停止服务进程  |
| ./jeebiz-boot restart  | 以后台服务模式重启服务  |
| ./jeebiz-boot status  | 查看当前服务运行状态  |
| ./jeebiz-boot dump  | 输出 dump  |

### 2、Shell 服务

```shell
|--jeebiz-admin-shadow
|----bin 
|------jeebiz-boot                       #服务启停shell脚本
|------jeebiz-boot.bat                   #服务启停bat脚本
|----conf
|------application.yaml                 #应用配置yaml文件
|------bootstrap.properties             #应用配置properties文件
|----lib
|------jeebiz-admin-shadow-1.0.1-ce-SNAPSHOT.jar    #应用程序jar文件
|----logs       #日志目录，默认日志将输出到这里
|----tmp        #临时目录
```