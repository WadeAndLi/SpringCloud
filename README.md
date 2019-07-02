SpringCloud
1.Eureka: 实现服务器之间的通信，是一个注册中心，通过心跳来注册启动的服务器，可以配置默认注册的地点。 有Client 端和 server端, 可以配置端口号，client端可以注册到指定的服务端.

2.Ribbon 实现负载均衡，底层实用轮询的算法，通过累加计数来进行服务器调用。

3.hystrix 实现服务器降级和熔断，主要是三个状态 close，open, half open，用来进行服务器的熔断和再连。

4.feign 实现服务器端的端口隐藏和方法的注解，使用注解和接口的形式。 其中feign 已经集成了ribbon 所以不需要再引入.

5.Rull 实现微服务的网关作用，主要用来拦截请求，转发请求，实现负载均衡等作用, 路由制主要是使用Filter实现 Zull Filter中主要内置了4个类，用来进行请求拦截: filterType: 过滤类型： pre, routing, post, error filterOrder: 过滤顺序 shouldFilter: 要不要进行过滤 run: 过滤器逻辑

hystrix 的超时时常要是 ribborn 连接和读取超时时长的两倍多

当然 我们也不能只用一台Zuul服务器，当我们的系统需要做集群的时候，就需要在zoo的外层再加上一次负载均衡 nginx, nginx可以利用ip漂移

Spring-cloud-config: 全局的配置中心，可以实现开发环境和生产环境的切换，可以和GIT 一起来使用

config server会先去拉取 git上的配置信息，然后每个微服务会去拉取config-server的信息
主动进行推送 Spring-cloud-bus 实现消息推送， 内部使用rabbitMQ，Kafka来进行实现的, 首先先去拉取git上的配置，然后通过spring cloud bus 去向每个server发送通知， 让服务器自动拉取配置, 不需要重启，可以自动生效。(需要配置) Spring-cloud-config 和 Spring-cloud-bus 是一套，用于配置动态更新
Spring-cloud-hystrix-dashboard: 容错统计，形成一个图形化界面 Spring-cloud-sleuth: 链路追踪, 结合Zipkin

CDN服务， 将静态资源放到CDN服务器上，以前响应静态资源的时候，需要经过中间商然后进行转发，现在可以直接通过CDN获取我们的静态资源。

虚拟机
-. Nginx配置 1.使用了windows 自带的Hyper虚拟机，使用CentOS7操作系统. 2.如果想使用域名 而且不带端口访问前端页面，首先要做的就是使用nginx配置转发. 3.下载安装好虚拟机后，一定要记得使用静态IP，而且提前做好联网，不然会有很多问题. 4.使用fileZilla进行文件的上传和修改 5.下载好Nginx的安装包，然后进行解压和修改配置文件。 6.配置IP 和域名： 1）对于windows的配置，我们需要在host的文件下配置 域名和访问的IP (IP为虚拟机的IP， 可用Ip addr来查看) 2) nginx.conf 文件，配置端口号为80的拦截服务器 1.拦截我们前端静态页面的服务器 (端口名为manage.fly.com) 端口号 9001 2.拦截我们后端的服务器，拦截Zuul网关，(端口名为 api.fly.com) 端口号 10010 3.拦截的IP 注意填写成windows的IP，可以使用ipconfig来获取 3) 在虚拟机Ping一下看是否可以ping通 4) 开启前端静态页面的服务器 5) 输入域名 manage.fly.com

二. 配置Java通用异常类 2.配置通用异常类 1)首先 确定使用ResponseEntity<Classs<?>> 来做返回值 2)成功 直接可以返回 ResponseEntity.ok(body) 3)如果失败，自定义一个异常类，接受一个枚举(包括状态码和信息) 4)定义一个枚举 5)定义一个接收这个异常类的类，注解为 @ControllerAdvice， 里面定义一个方法做返回值，注解为@ExceptionHandler(异常类.class) 6)定义一个PO，返回标准的错误返回值 （可以通过枚举类来进行构造） 7)异常返回方法使用这个类做返回值的body.

三. FastDfs的配置 1.下载 fastDFS的压缩文件，下载fastDFS集成Nginx的文件 2. 解压文件，然后进入 tracker的文件中，进行配置路径。 3. 进入storaged文件中进行配置 4. 开启服务 (记得IP是虚拟机的IP） 5，测试 6. Java client端 1) 引入依赖 fastDFS的依赖 2) 使用依赖this.fileStorageClient.uploadFile (InputStream var1, long var2, String var4, Set var5)

四. 搭建Elacsticsearch微服务.
  1. 配置Eureka, 引入Feign的依赖 (配置要@EnableFeignClients)

五.引入Feign, feidn是一个实现客户端和服务端接口通信的技术,可以很好的规范REST风格的API编程规范，由于我们可能其他的服务也会使用相同的接口，我们通常建议将接口放入一个interface中，这样有利于代码的复用，我们可以把一些公共的服务接口放入到里面，然后如果其他的服务使用的时候，可以在自己的服务中注册feignclient然后集成这个接口就好了，这样很好的维持了代码的复用。
