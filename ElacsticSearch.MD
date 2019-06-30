ElasticSearch:
  ES是一款分布式的高效率的搜索引擎，可以处理海量的数据，可以处理近实时的搜索，读写速度很快，遵顼rest 风格.
  和MYSQL相关的概念：
   索引(indices) ---------------------- Database (数据库)
   类型(type) --------------------------Table 数据表
   文档(document) ----------------------Row (行)
   字段(field) -------------------------Column （列)
  一些常用概念：
   1. 索引库 (indices) 是index的负数，表示许多的索引
   2. mmapping (映射) 字段的数据类型，属性，是否索引，是否存储等特性.
   
  和集群相关的概念：
   1. 每个索引可以进行分片(shard) (将整个数据分到不同的片区中) 每个片区中还有备份 (replica)
  ES 关键字类型：
  
  建索引：发送PUT请求，
  
    PUT fly
    {
      "settings": {
        "number_of_shards": 3, //分片的数量
        "number_of_replicas": 2 //备份的数量
      }
    }
    
  建立类型: (Type)
      PUT /fly/_mapping/goods
      {
        "properties": { //属性名
          "title": {
            "type": "text", //类型
            "analyzer": "ik_max_word" //分词器
          },
          "images": {
            "type": "keyword",
            "index": false
          },
          "price": {
            "type": "float"
          }
        }
      }

  一. ES Type:
  
   string （text) (keyword)  text可以分词, keyWord不可以分词
    字符串类型，es中最常用的类型，官方文档 

   Numeric
    数值类型，注意numeric并不是一个类型，它包括多种类型，比如：long,integer,short,byte,double,float，每种的存储空间都是不一样的，一般默认推荐integer和float。官方文档参考
    
   date
    日期类型，该类型可以接受一些常见的日期表达方式，官方文档参考。
    
   IP
    这个类型可以用来标识IPV4的地址，参考官方文档
    
   boolean
    布尔类型，所有的类型都可以标识布尔类型，参考官方文档

    False: 表示该值的有:false, "false", "off", "no", "0", "" (empty string), 0, 0.0
    True: 所有非False的都是true
  
   Object (complex data)
   当存入的数据是对象的时候比如：
   {girl: {name: "123", age: 34}} 
   会解析成: girl.name, girl.age.
  
  二. index
    影响字段的索引情况
      true: 会被索引，可以用来搜索
      false: 不会被索引.
      
  三. store
    是否将数据进行额外存储
    默认值是false.
    为什么设置为 false, 因为ES在创建文档索引时，会将文档中的原始数据进行备份，保存一个叫做
   _source 的属性中, 而且我们可以选择哪些要显示，哪些不可以被显示.
       
  四. 激励因子
    
  插入数据:
    可以插入 type定义好的数据，也可以进行智能推断进行映射，但是映射的时候，如果是字符串，
    他会自动给你生成2个字段，一个是text类型可以分词的，一个是keyword不可以分词的.
    
  查询:
    1. bool 查询. 2.模糊查询 等
    
    1) Match:
        GET /fly/_search
        {
          "query": {
            "match": {
              "title": { //字段名
                "query": "大米", //查询关键字
                "operator": "and" //查询方式
              }
            }
          }
        }
    2) Multi_match:
       GET /fly/_search
       {
         "query": {
           "multi_match": {
             "query": "3666", //查询关键字
             "fields": ["title", "price", "stock"] //需要查询的字段
           }
         }
       }
    3) Term (词条匹配)
      一般使用除了字符串 以外的其他字符，可以用term.
      GET /fly/_search
      {
        "query": {
          "term": {
            "title": {
              "value": "大米"
            }
          }
        }
      }
    4) 结果过滤 (可以搜索出你需要的数据，使用_source)
        GET /fly/_search
        {
          "_source": {
            "includes": "title", 
            "excludes": "price"
          }, 
          "query": {
            "match": {
              "title": "手机"
            }
          }
        }
    5) 模糊搜索 
        GET /fly/_search
        {
          "query": {
            "fuzzy": {
              "title": "appla"
            }
          }
        } 可以搜到 apple 手机
        
    6）range (范围查询)
        GET /fly/_search
        {
          "query": {
            "range": {
              "price": {
                "gte": 1000,
                "lte": 3000
              }
            }
          }
        }
     (7) Bool查询
       Must: 一定.
       should: 或者
       Must_Not: 一定不
       Filter: 一般把过滤条件放在这里，不影响分数.
       
       GET /fly/_search
       {
         "query": {
           "bool": {
             "must": [
               {
                 "match": {
                   "title": "大米"
                 }
               }
             ],
             "filter": {
               "range": {
                 "price": {
                     "gte": 3000
                 }
               }
             }
           }
         }
       },
       
       GET /fly/_search
       {
         "query": {
           "bool": {
             "must": [
               {
                 "match": {
                   "title": "大米"
                 }
               },
               {
                 "range": {
                   "price": {
                     "gte": 3000
                   }
                 }
               }
             ]
           }
         }
       }
    两种得到的结果是一样的，但是分数是不一样的.
    
    8) 排序，分页
      排序: sort 字段，ASC DESC。
      分页: form, size.
      
    9) 聚合 (ES 很强大的工具)
      主要包含两种： 1. bucket (桶） 2. 度量
      1.bucket. 
       1) 根据日期 如果给定一个阶梯，会自动分为一组
       2) 根据数值阶梯分组 和日期类似
       3) 根据词条分组 词条内容完全匹配的是一组
       
       GET /fly/_search
       {
         "size": 0, 
         "aggs": {
           "most_expensive": {
             "terms": {
               "field": "price"
             }
           }
         }
       } //根据价格进行分桶
       
      2. 度量.
       类似于mysql中的聚合函数.
  ES Java整合:
    1. 引入spring data ES 的依赖
    2. 使用ES template 来创建.
    3. 创建一个PO，然后使用注解来完成字段映射的配置.
  
  ES 增删改查
    1. 引入 ElasticsearchRepository
    2. 引入一个接口继承
    3. 可以使用方法进行增删改查.
    4. 可以自定义查询语法 (非常只能, 可以去接口根据提示的方法名进行查询，必须要遵循查询名)