# 微服务练习项目，基于Dubbo、Thrift、SpringBoot
## user-thrift-service-api thrift定义用户API服务
### 通过thrift生成java代码
1. 编写user_service.thrift
2. 执行gen-code.bat生成java代码

## user-thrift-service 用户服务
### 对用户进行保存，查询等操作

## user-edge-service 用户前端服务（边缘服务）
### 处理前端请求，登录，注册

## user-edge-service-client 单点服务
### 单点过滤

## message-thrift-service-api thrift定义消息服务
### 通过thrift生成python代码

## message-thrift-py-service Python编写后端服务
### 发送短信、邮件

## course-dubbo-service-api 课程API

## course-dubbo-service 课程后端服务
### 对课程数据操作，实现api接口，为dubbo服务端

## course-edge-service 课程前端服务（边缘服务）
### 对课程数据操作，接收前端请求
***
# 环境
- Spring Boot 2.0.4.RELEASE
- JDK 1.8
- zookeeper 3.4.10
- redis 3.0
***
# 服务启动
1. 启动Zookeeper、redis、mysql
2. 启动消息服务：message-thrift-py-service/message/message_service.py
3. 启动用户服务：user-thrift-service、user-edge-service
4. 启动课程服务：course-dubbo-service、course-edge-service
5. 访问：http://localhost:8083/course/getCourseList






