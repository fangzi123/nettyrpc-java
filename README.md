# 项目名称
nettyrpc-java 是 rpc的java版实现，支持 fastjson,protostuff 序列化协议。

# 核心功能点
* 支持 fastjson,protostuff 序列化协议。
* 支持 SpringBoot starter。
* 支持多种naming服务，比如Zookeeper、List。
    可以灵活扩展支持 etcd、eureka、nacos 、File、DNS、Consul等。
* 支持多种负载均衡策略，比如random、round robin。
* 支持interceptor功能，支持计数器、令牌桶等server端限流算法。
* rpc功能依赖Spring。不强依赖注册中心。
* 基于SPI机制可灵活扩展Protocol、NamingService和LoadBalance。

## 快速开始
### 开发环境
java 11 && netty 4 && protostuff 1.0.8

### 引入maven依赖
SpringBoot环境：
```xml
<dependency>
    <groupId>com.nettyrpc</groupId>
    <artifactId>nettyrpc-spring-boot-starter</artifactId>
    <version>1.2-SNAPSHOT</version>
</dependency>
```
### Server端使用
* [server端基本用法](https://github.com/fangzi123/nettyrpc-java/tree/dev/nettyrpc-example/server1/README.md)

### Client端使用
* [client端基本用法](https://github.com/fangzi123/nettyrpc-java/tree/dev/nettyrpc-example/client/README.md)

### 扩展
* [扩展Protocol、NamingService、LoadBalance](https://github.com/baidu/brpc-java/blob/master/docs/cn/extension.md)
