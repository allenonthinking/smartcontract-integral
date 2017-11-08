**项目说明** 
- 采用Spring、MyBatis、Shiro框架，开发的一套权限系统
<br>

**具有如下特点** 
- 支持HTML、Freemarker、JSP视图，可与现有项目快速集成
- 灵活的权限控制，可控制到页面或按钮，满足绝大部分的权限需求
- 完善的部门管理及数据权限，通过注解实现数据权限的控制
- 完善的XSS防范及脚本过滤，彻底杜绝XSS攻击
- 支持分布式部署，session存储在redis中
- 友好的代码结构及注释，便于阅读及二次开发

<br>

**数据权限设计思想** 
- 管理员管理、角色管理、部门管理，可操作本部门及子部门数据
- 菜单管理、定时任务、参数管理、系统日志，没有数据权限
- 业务功能，按照用户数据权限，查询、操作数据【没有本部门数据权限，也能查询本人数据】

<br> 

**项目结构** 
```
allen-security
├─doc  项目SQL语句
├─common  公共
├─modules 模块
│  ├─gen 代码生成器
│  ├─job 定时任务
│  ├─oss 文件存储
│  └─sys 系统管理(核心)
│ 
├─resources 
│  ├─mapper   SQL文件
│  ├─template 代码生成器模板（可增加或修改相应模板）
│  ├─db.properties  数据库配置文件
│  ├─config.properties  其他配置文件
│  └─generator.properties  代码生成器配置文件
│ 
├─webapp 
│  ├─statics   静态资源
│  ├─swagger   swagger ui
│  └─WEB-INF/views   系统页面
│     ├─modules      模块页面
│     ├─index.html   AdminLTE主题风格（默认主题）
│     └─index1.html  Layui主题风格

```

<br>

 **技术选型：** 
- 核心框架：Spring Framework 4.3
- 安全框架：Apache Shiro 1.3
- 视图框架：Spring MVC 4.3
- 持久层框架：MyBatis 3.3
- 定时器：Quartz 2.2
- 数据库连接池：Druid 1.1
- 日志管理：SLF4J 1.7、Log4j
- 页面交互：Vue2.x

<br>

 **软件需求** 
- JDK1.7+
- MySQL5.5+
- Tomcat7.0+
- Maven3.0+

<br>

 **本地部署**
- 通过git下载源码
- 创建数据库allen_security，数据库编码为UTF-8
- 执行doc/db.sql文件，初始化数据【按需导入表结构及数据】
- 修改db.properties文件，更新MySQL账号和密码
- Eclipse、IDEA执行【clean package tomcat7:run】命令，即可运行项目
- 项目访问路径：http://localhost:8080/allen-security
- 账号密码：admin/admin
- swagger文档路径：http://localhost:8080/allen-security/swagger/index.html

<br>

 **分布式部署**
- 分布式部署，需要安装redis，并配置config.properties里的redis信息
- 需要配置【allen.redis.open=true】，表示开启redis缓存
- 需要配置【allen.shiro.redis=true】，表示把shiro session存到redis里


