项目地址
https://github.com/youngzil/quickstart-sofa


https://github.com/sofastack
蚂蚁金融科技官网（https://tech.antfin.com/）
SOFA 文档: http://www.sofastack.tech/
SOFA 开源整体地址: https://github.com/alipay
https://gitee.com/alipay

http://www.sofastack.tech/
蚂蚁金服自主研发的分布式中间件（Scalable Open Financial Architecture，以下简称 SOFA ）启动开源计划，并开放多个组件


相关资源
SOFABoot: https://github.com/alipay/sofa-boot
SOFARPC: https://github.com/alipay/sofa-rpc
SOFARPC Ark Plugin: https://github.com/alipay/sofa-rpc-boot-projects
SOFAArk: https://github.com/alipay/sofa-ark
Ark Plugin 目录结构及其打包插件的使用 https://alipay.github.io/sofastack.github.io/docs/ark-plugin.html


蚂蚁通信框架实践
https://mp.weixin.qq.com/s/JRsbK1Un2av9GKmJ8DK7IQ

蚂蚁金服自主研发的分布式中间件（Scalable Open Financial Architecture，以下简称 SOFA 中间件），包含了构建金融级云原生架构所需的各个组件，包括微服务研发框架，RPC框架，服务注册中心，分布式定时任务，限流/熔断框架，动态配置推送，分布式链路追踪，Metrics监控度量，分布式高可用消息队列，分布式事务框架，分布式数据库代理层等组件，是一套分布式架构的完整的解决方案，也是在金融场景里锤炼出来的最佳实践。





SOFA 商业版地址：https://www.cloud.alipay.com/products/SOFA
SOFA 开源版地址：https://github.com/alipay
SOFAStack https://github.com/alipay
SOFATracer: https://github.com/alipay/sofa-tracer
SOFALookout：https://github.com/alipay/sofa-lookout
SOFABoot: https://github.com/alipay/sofa-boot
SOFARPC Node：https://github.com/alipay/sofa-rpc-node
SOFABolt Node：https://github.com/alipay/sofa-bolt-node
Eggjs：https://eggjs.org
SOFARPC: https://github.com/alipay/sofa-rpc
JarsLink：https://github.com/alibaba/jarslink
SOFAStack系列文章知乎专栏：https://zhuanlan.zhihu.com/sofastack
Egg：http://eggjs.org/
SOFABoot：https://github.com/alipay/sofa-boot
SOFARPC：https://github.com/alipay/sofa-rpc
SOFABolt：https://github.com/alipay/sofa-bolt



：sofa-ark 支持运行时动态加载 Jar 包，需要引入插件 Jarslink2.0。
详细请参考
https://github.com/alipay/sofa-jarslink



bolt 貌似不支持 grpc 或 http2，这块有 roadmap 还是依赖社区贡献呢？
A：sofa-bolt 是一个通讯框架，默认支持 bolt 协议，sofa-bolt 本身不会支持其它协议。
而在 SOFARPC 作为服务调用框架，则会集成多个协议以及多种序列化。现在已经支持 bolt+protobuf、http/2+protobuf 等等多种组合。 
更多使用示例参考：https://github.com/alipay/sofa-rpc/tree/master/example
gRPC 已经有对应的Issue，感兴趣的朋友可以一起参与共建。
https://github.com/alipay/sofa-rpc/issues/57



为什么数据传输，例如库到库的复制或者在线到离线同步需要通过消息队列进行中转，为什么不可以直接通过一个进程做点到点的复制？

A：这是一个很好的问题，
众所周知，一些数据库例如MySQL的原生复制就是通过IO Thread 远程进行日志拉取的，那么为什么要在这个过程中引入消息队列呢？

一个最明显的理由就是通过中间层的消息队列做到了源和目的库的解耦，即日志的拉取和重放互不影响，不会因为目的端数据库写入阻塞导致源端的日志不能清理，也不会因为源端的异常而造成重放程序的异常。其实解耦是消息队列一个最基本的特性，是其区别于RPC等其它分布式系统的一个关键要素，除了数据传输，在业务场景下也是用非常广泛；

第二个原因我想是起到削峰填谷，用作数据缓冲，避免突增的流量对于上下游系统的影响；

第三就是化简和收敛拓扑，试想如果是点对点的复制，一旦节点很多话整个拓扑链路会异常复杂，特别是存在多个异构数据源的时候，就需要处理多项式级的拓扑复杂度，而通过消息队列的统一消息格式收敛后，就呈现出星形拓扑结构，其中的每个节点只需要处理与消息队列的关联(投递或者消费)；

第四就是消息队列作为基础设施(Infrastructure)，可以在网络层面做非常多深入的优化，例如国内乃至洲际长传链路，存在非常多网络层不稳定因素，例如丢包，带宽被抢占等等，消息队列可以专门进行优化，而普通的应用很难兼顾到这一层；

第五就是在存储层面，消息队列通过多种方式例如存储消息到数据库、文件系统、基于paxos等的副本或者分布式存储等等来保证消息的一致性和高可用，来解决应用层希望数据0丢失的需求；

第六是数据传输系统往往要重新散列，不是简单按照数据库的维度消费，方便下游使用，使用消息队列可以支持数据按照新的维度进行散列。

最后还有一点就是我们认为消息队列会越来越向“懂数据”的方向发展，可以逐渐基于索引和消息本身提供一些简单可靠的查询计算功能，让一些原本需要大量读取数据的简单计算，例如统计数据条数这样的功能，借助于 AntQ Streams 流式计算框架，直接在消息队列中运行，极大的提高性能降低了计算成本。

蚂蚁自研的消息队列产品AntQ，在前面说的几个方向上都进行了很多的研究和尝试，在保证数据事务一致性、幂等性、高吞吐和低延迟方面都做了很多有价值的工作，目前已经成为整个蚂蚁以及金融云上业务结构，分布式事务支持，数据传输和计算核心的基础设施。





SOFARPC 支持蓝绿部署吗？

A：目前开源版本没有，要支持的只要扩展 Router 这个扩展点就好了。因为蓝绿分组的这个打标信息，需要在框架层识别，你们可以根据自己的蓝绿方案，来扩展一个 Router，进行路由分组就好了



SOFAMosn 的短期和长期定位是什么？

A：在 0.1.0 版本SOFAMosn 的定位是作为服务容器的 sidecar，SOFAMosn 提供丰富的编程接口和扩展机制，可以通过代码集成的方式来扩展使用，也可以通过容器化或r pm 的方式配置使用，同时我们对接了 istio，并会在 0.2.0 版本去完善对接的功能点，做到整体支持，并且我们会推进多核优化等方向，满足 SOFAMosn 作为 router 和i ngress 的需求，最终的目标是将 SOFAMosn 打造成一个满足云原生需求，能与 k8s，istio 等技术体系无缝集成的4、7层负载均衡。

关于使用方式大家可以在我们的 Github 文档中需求帮助，有任何问题可以提 issue 或加入我们的微信群。后续的发布可以关注 Github，或本公众号及时了解最新的功能更新

SOFAMosn Github 地址：
https://github.com/alipay/sofa-mosn



SOFAMosn：https://github.com/alipay/sofa-mosn
实现作为 Proxy 的基本功能；
对齐 Envoy 的核心能力；
支持 XDS API V0.4 版本，实现与 Pilot 对接；
支持 SOFARPC，HTTP/1.1，HTTP/2.0；

SOFABolt 1.5.0-SNAPSHOT 发布：
SOFABolt：https://github.com/alipay/sofa-bolt
支持单实例级别的用户属性设置，可以覆盖系统设置和默认值，防止单进程多实例互相影响；
同时基于功能1，实现了实例级别的 netty write buffer water mark 的设置，以及重连、自动断链等开关的生效优先级；

SOFABoot 2.4.4 发布：
SOFABoot：https://github.com/alipay/sofa-boot
修复健康检查配置问题；
修复模块化开发问题；

SOFATracer 2.1.2 今晚发布：
SOFATracer：https://github.com/alipay/sofa-tracer
修复并发导致的性能问题，以及依赖优化和配置优化；




1. 序列化后面支持json序列化怎么理解？网络传输必须是二进制。难道序列化后，传输的时候再序列化一次吗

A：说到这个，不得不先介绍下RPC里几个协议的概念，分别是：
- 底层通讯协议：常见的例如 TCP UDP
- 数据层协议：SOFABolt、HTTP、HTTP/2
- 序列化协议：Hessian、Protobuf、Jackson

RPC传输的二进制数据都是由这三部分组成的，一般底层通讯协议和数据层协议使用者不用关心，使用者只需要关心业务数据的序列化协议即可。
例如这里提到的「序列化支持 JSON」，意思就是将方法的请求和返回值序列化成 JSON 字符串。PRC 真正传输的二进制数据还是由这三部分组成的。




服务端 stub 反序列化后进行本地调用，可以理解为 java 反射调用，这句话怎么理解呢？我的理解是 rpc 服务发布后，应用启动的时候会把 rpc 服务注册到根上下文，key 为制定的，那么客户端发起服务调用的时候，服务端 stub 反序列化后，拿到接口，方法，参数等信息后，构造 key 直接去根上下文找对应的 bean 就可以了。请解答一下疑问

A：你的理解是正确的，这里文章没有描述得特别清楚，确实是按照接口+协议作为 key，然后 Stub 作为 value。这个 Stub 是我们包装后的 bean，拿到之后，要根据协议中的方法名、入参等，找对这个 bean 的方法，因为是通用的处理逻辑。
所以开始进行反射调用。



请教下文中说的xa为什么要求串行化隔离级别？XA 协议通过每个 RM（Resource Manager，资源管理器）的本地事务隔离性来保证全局隔离，并且需要通过串行化隔离级别来保证分布式事务一致性。

A：文章里有图示说明，在没有实现分布式 MVCC 的情况。可能 RM1 的本地事务提交了，RM2 还没提交，这样就会读取到RM1已提交的结果 和 RM2上未提交的结果，数据就不一致了。如果使用了串行化隔离级别，RM2 对于相同数据的读取就会被阻塞，直到RM2 的本地事务提交。从而保证读取到的是全局分布式事务结束以后的结果



Q：
maven 实现的模块化， 不光是相互调用， 也可以做成分层结构，  这样比较简单。 如果service 之间的调用也用 SOFA ，会不会太重？  毕竟是在一个微服务里面

A：这个需要看情况，如果业务真地非常简单的话，可以一个 Spring 上下文就搞定了。如果真的有一定的复杂度，为了更好的区分模块，可以通过 SOFA 这样的模块化的方式，我的分享中贴的是 XML 的方式，其实 SOFA 也提供了注解的方式发布服务，引用服务，代码写起来其实非常简单。



Q:
是否可以在 SOFABoot 模块中定义 Controller 组件？

A：SOFABoot 模块一般用于封装对外发布服务接口的具体实现，属于业务层，Controller 属于展现层内容，我们不建议也不支持在 SOFABoot 模块中定义 Controller 组件，Controller 组件相关定义建议直接放在 Root Application Context。




SOFAArk 类隔离机制和 SOFA 的上下文隔离的关系以及如何结合使用的？

A：SOFAArk 的类隔离和 Sofa 上下文隔离其实没有太大的关系。SOFAArk 定位是一个通用的类隔离框架，通过 Ark Plugin 和 Ark Biz 两个模型做到指定依赖的隔离，不依赖 Spring 框架。 Sofa 上下文隔离是指在 SOFABoot 框架内，两个模块使用两个独立的 Spring 上下文，不过仍然是相同的 ClassLoader 加载，至于模块上下文隔离的优势可以参考 http://www.sofastack.tech/posts/2018-07-25-01 . 关于 SOFAArk 和 SOFA  上下文隔离如何结合使用，两个特性不冲突，可以同时使用。


















