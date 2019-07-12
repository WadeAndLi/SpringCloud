NOSQL not only SQL.

常见的NOSQL存储
MongoDB, redis,

Redis 的特点：
1. 多种数据类型存储
----字符串类型
----散列类型
----列表类型
----集合类型
----有序集合类型
2.内存存储与持久化
----内存的读写速度远远快于硬盘
----自身有持久化功能
3.功能丰富
----可以用做缓存，队列，消息的订阅和发布
----支持键的生存时间
----按照一定规则删除响应的键
4.简单稳定
----相比SQL语言更加简单
----不同语言的客户端丰富
----基于C语言开发的，代码很少

Memcache 多线程
Redis 单线程
Redis 支持数据持久化 数据更安全
Memcache 数据存在内存中， 不能持久化

一些常用命令：
1. set key value
2. get key value
3. keys *
4. select index()
5. EXPIRE name seconds 设置过期时间
6. TTL name 查看过期时间 -2表示过期 -1表示永远存在

字符串指令:
1.单个值最多存 512MB
2.INCR key 自增长加一
3.DECR key 减一

Hash结构
类似于 HashMap<String,String>
HSET name:wade key value
HSET name:wade key value
HGET name:wade key
HGETALL name:wade
HKEYS name:wade 获取name:wade种所有的key
HVALS 获取所有的value


