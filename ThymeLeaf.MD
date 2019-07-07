当我们在使用Freemarker或者Thymeleaf的模板引擎的时候，现在是在Tomcat上运行的，
每一次都是后端先去数据库查询数据，然后将数据渲染到模板上，最后再生成一个HTML的文件。
这种情况下通常有几个缺点：1.数据和页面在一起渲染然后加载，这样的话页面渲染的时间会
增加。2.增加了Tomcat服务器的压力，不便于服务器的工作，我们通常会使用页面静态化来实现
我们的方案.

什么是页面静态化？
页面静态化就是动态生成的HTML静态保存，以后用户请求到来，直接访问静态的页面，不需要经过
服务器的渲染。 我们前端的静态HTML页面可以部署在nginx中，大大提高并发能力，减小tomcat的压力

如何实现静态化？
我们可以不通过把模板写入response中，可以通过写入到本地实现静态化.

Thymeleaf 有3个概念：
1.context 运行上下文.
2.templateResolver 模板解析器.
3.templateEngine 模板引擎

context 运行上下文：用来保存模型数据，当模板引擎渲染的时候，会先去运行上下文拿到需要的数据
当和springBoot结合的时候，model数据会到context中。

templateResolver 模板解析器: 读取和配置模板文件.
templateEngine 用来解析模板引擎，结合context和templateResolver 一起从而把数据解析出来.
调用的是 templateEngine.process("模板名", context, write);

