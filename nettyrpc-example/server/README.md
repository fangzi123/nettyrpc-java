# 项目名称
server
## 快速开始
### 开发环境
springboot2.1.5
### 引入maven依赖
SpringBoot环境：
```xml
<dependency>
    <groupId>com.nettyrpc</groupId>
    <artifactId>nettyrpc-spring-boot-starter</artifactId>
    <version>1.2-SNAPSHOT</version>
</dependency>
```
注册发现中心：
<!--nacos-->
```xml
<dependency>
    <groupId>com.wangff</groupId>
    <artifactId>nettyrpc-naming-nacos</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```
### application.yml
```yaml
nettyrpc:
  server:
      enable: true
  registryAddress: nacos://127.0.0.1:8848
  serviceId: nettyrpc-server
  protocolType: 1  # PROTOSTUFF = 1; JSON = 2;
  serverHost: 127.0.0.1
  serverPort: 18080
  tokenBucketSize: 500
  tokenInputRate: 500
```
