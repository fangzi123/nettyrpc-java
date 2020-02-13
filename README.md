# 项目名称
【nettyrpc-java】
nettyrpc-java 是 rpc的java版实现，支持 kryo、protostuff、fastjson序列化协议。

# 核心功能点
* 支持 TCP+protostuff/fastjson/kryo协议。
* 支持 SpringBoot starter。
* 支持多种naming服务，比如List、File、nacos、Zookeeper。
    可以灵活扩展支持 etcd、eureka、DNS、Consul等。
* 支持多种负载均衡策略，比如random、round robin。
* 支持interceptor功能，支持计数器、令牌桶等server端限流算法。
* rpc功能可独立使用，不是必须依赖Spring和注册中心功能。
* 基于SPI机制可灵活扩展Protocol、NamingService和LoadBalance。
### Design:
![design](https://images2015.cnblogs.com/blog/434101/201603/434101-20160316102651631-1816064105.png)

## 快速开始
### 开发环境
java 11 && netty 4 && protostuff 1.0.8

### 引入maven依赖
SpringBoot环境：
```xml
<dependency>
    <groupId>com.nettyrpc</groupId>
    <artifactId>nettyrpc-spring-boot-starter</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```
注册发现中心(list与file不需要)：

nacos：
```xml
<dependency>
    <groupId>com.wangff</groupId>
    <artifactId>nettyrpc-naming-nacos</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```
zookeeper：
```xml
<dependency>
    <groupId>com.wangff</groupId>
    <artifactId>nettyrpc-naming-zookeeper</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```
### Server端使用
* [server端基本用法](https://github.com/fangzi123/nettyrpc-java/tree/dev/nettyrpc-example/server/README.md)

### Client端使用
* [client端基本用法](https://github.com/fangzi123/nettyrpc-java/tree/dev/nettyrpc-example/client/README.md)

### 扩展
* [扩展Protocol、NamingService、LoadBalance](https://github.com/fangzi123/nettyrpc-java/tree/dev/docs/extension.md)

### 参考
* https://www.cnblogs.com/luxiaoxun/p/5272384.html
* https://github.com/luxiaoxun/NettyRpc
* https://github.com/baidu/brpc-java
