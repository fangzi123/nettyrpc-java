# 扩展新协议
## 总体步骤：
* 实现AbstractProtocol类的相关方法。
* 实现ProtocolFactory类，为新协议提供Factory。
* 利用spi机制，在resources/META-INF/services目录下创建名为com.nettyrpc.protocol.ProtocolFactory的文件，
内容是Factory全路径类名。

## 扩展新的服务注册发现
* 实现NamingService接口。
* 实现NamingServiceFactory接口。
* 利用spi机制，在resources/META-INF/services目录下创建名为com.nettyrpc.naming.NamingServiceFactory的文件，
  内容是Factory全路径类名。

## 新增负载均衡实现
* 实现LoadBalanceStrategy接口。
* 实现LoadBalanceFactory接口。
* 利用spi机制，在resources/META-INF/services目录下创建名为com.nettyrpc.client.loadbalance.LoadBalanceFactory的文件，
  内容是Factory全路径类名。

## 新增拦截器
* 实现Interceptor接口
* 在初始化RpcClient或RpcServer时，传入interceptors参数中。

## 新增限流算法实现
* 实现CurrentLimiter接口。
* 创建CurrentLimitInterceptor实例。
* 在初始化RpcClient或RpcServer时，传入interceptors参数中。
