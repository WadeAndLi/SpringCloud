MQ: message queue 消息队列
目前最流行的是2种方式：
1.AMQP 提供统一消息服务的应用层标准的高级消息协议队列,是应用协议的一个开放标准.
跨语言和跨平台
2.JMS Java message service，由sun公司提供的消息标准, 为java的应用层提供统一的消息
操作，包括create, send, receive. 他比较统一，但是只能限制使用java语言.
常见的 MQ 产品：
1. RabbitMQ AMQP产品
2. ActiveMQ JMS产品 apache
3. RocketMQ JMS产品 阿里开发 交给apache
4. Kafka 分布式消息系统 高吞吐量 可能稳定性不好 有可能会有数据丢失
Spring cloud 支持2种MQ, kafka和RabbitMQ
Erlang 是一种面向并发的语言

RabbitMQ 共有六种模型：
1. 简单消息模型 P： producer 提供方 -> queue 队列 -> consumer 消费方
    生产者发送消息到队列，消费方接收队列的消息
    
    Connection: 建立的连接
    Channel: 一个连接可以建立多个通道去做不同的事
    使用channel声明一个队列
    
    生产者:                   消费者：
    1.建立连接                1.建立连接  
    2.创建通道                2.创建通道
    3.声明一个队列            3.声明一个队列
    4.定义消息内容            4.定义一个DefaultConsumer然后重写里面的方法 handleDelivery
    5.publish消息             5.方法内处理接受的内容
                             6.监听队列，是否进行消息确认.
    如何避免消息的丢失:
    1. 使用确认模式.
     1)自动ACK channel.basicConsume(QUEUE_NAME, true, consumer); 
     2)手动ACK channel.basicAck(envelope.getDeliveryTag(), false);
               channel.basicConsume(QUEUE_NAME, false, consumer); 
2. Work消息模型
    P -> queue -> consumer 1
               -> consumer 2
    一个消息被多个消费者接收
    如果同时有50条消息进入，如果没有其他接收延迟，一般都会收到25条，但是如果一个执行很快
    一个比较慢，就会出现快的一直在等着慢的，这样是不合理的，我们可以通过
    channel.basicQos(1); 设置每个消费者只能处理一个消息，这样快的处理的就比较多，也可以解决消息的堆积.
    
真实的开发中，生产者是不能直接向队列进行发送消息的，甚至不知道消息是否会被传送到任何队列
相反的，生产者只能向交换机发送消息，交换器做一件十分简单的事，他一方面接受生产者产生的消息,
另一方便吧消息推送给不同的队列，发送队列的方式也不同.
3. Publish/Subscribe模式 Fanout模式 (广播)
    p -> exchange -> queue -> c1
                  -> queue -> c2
    生产者:
    channel.exchangeDeclare(EXCHANGE_NAME, "fanout"); //声明类型为 fanout
    channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes()); //发送到交换机
    消费者:
    // 绑定队列到交换机
    channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, ""); //交换机名称要是我们定义的 
    
4. Publish/Subscribe模式 Directive模式(转发)
   用户可以定义一个key为 delete然后消费者绑定Delete这个queue的就会获取到值
   生产者:
   channel.basicPublish(EXCHANGE_NAME, "delete", null, message.getBytes());
   消费者:
   channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "delete");
5. Publish/Subscribe模式 Topic模式(主题)
   他是在 Directive模式的基础上延申了，可以匹配通配符.
   #匹配一个或多个字符  *匹配一个词
   item.* 可以匹配 item.insert, item.(任何) 更加灵活
   
ACK：消费者确认机制 Durable: 交换机保证不会消息丢失 
生产者消息确认机制：confirm模式.
channel.confirmSelect() 开始confirm模式
channel.waitForConfirmOrDie() 确认等待成功或失败

   