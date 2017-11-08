**项目说明** 

SpringBoot版，方便SpringBoot开发者使用，该版本要求JDK1.8

<br> 

**项目结构** 
```
renren-security
├─doc  项目SQL语句
├─common  公共
├─config  配置文件
├─modules 模块
│  ├─gen 代码生成器
│  ├─job 定时任务
│  ├─oss 文件存储
│  └─sys 系统管理(核心)
├─RenrenApplication.java 项目启动类
│ 
├─resources 
│  ├─mapper     SQL文件
│  ├─template   代码生成器模板（可增加或修改相应模板）
│  ├─application.yml        全局配置文件
│  ├─application-dev.yml    开发环境配置文件
│  └─application-test.yml   测试环境配置文件
│  └─application-pro.yml    正式环境配置文件
│  └─generator.properties   代码生成器配置文件
│ 
├─webapp 
│  ├─statics   静态资源
│  ├─swagger   swagger ui
│  └─WEB-INF/views   系统页面
│     ├─modules      模块页面
│     ├─index.html   AdminLTE主题风格（默认主题）
│     └─index1.html  Layui主题风格
│

```

<br>

 **本地部署**
- 通过git下载源码
- 创建数据库renren_security，数据库编码为UTF-8
- 执行doc/db.sql文件，初始化数据【按需导入表结构及数据】
- 修改application-dev.yml，更新MySQL账号和密码
- Eclipse、IDEA运行RenrenApplication.java，则可启动项目
- 项目访问路径：http://localhost:8080
- 账号密码：admin/admin
- swagger文档路径：http://localhost:8080/swagger/index.html

<br>

 **分布式部署**
- 分布式部署，需要安装redis，并配置application.yml里的redis信息
- 需要配置【renren.redis.open=true】，表示开启redis缓存
- 需要配置【renren.shiro.redis=true】，表示把shiro session存到redis里

<br>
