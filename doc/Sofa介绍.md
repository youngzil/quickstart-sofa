所有的 SOFA 中间件中的组件组合起来可以发挥更大的能力，但是每一个组件都是可以被替换的，比如用户可以选择用 Dubbo 来替换 SOFARPC，或者跟 SOFARPC 对接互通；可以选择 Zookeeper 来作为服务注册发现，也可以选择 SOFA 的服务注册中心来做服务发现；分布式链路追踪组件遵守 OpenTracing 的规范，可以直接和 Zipkin 进行对接等等；Metrics组件会遵循 Metrics2.0 标准，适配 Prometheus 体系等等。



SOFABoot 是蚂蚁金服基于 Spring Boot 构建一个研发框架，整体架构上类似于蚂蚁金服之前开源的Egg框架，遵守微内核，可插拔的理念，我们以标准 Spring Boot Starter的方式，扩展了很多企业级特性，以解决大规模团队开发云原生微服务系统中会遇到的问题，如类隔离，ReadinessCheck，日志隔离等等能力，后续会开放更多内部实践过的特性，如 Spring 上下文隔离，合并部署，动态模块，Tracing、Metrics、Streaming、测试框架等。



SOFARPC 是一个高效，可靠，可扩展的 RPC 的框架，是蚂蚁金服服务化架构的基石。SOFARPC 最早源于阿里内部的 HSF，经过了蚂蚁金服内部多年的发展，在协议，网络，路由，可扩展性等层面都进行了大量的改造和优化的工作，适配了更多金融级的场景。


SOFATracer 是一个用于分布式系统调用跟踪的中间件，通过统一的 traceId 将调用链路中的各种网络调用信息以日志或者上报的方式记录下来，以达到透视化网络调用的目的。这些日志可用于故障的快速发现，数据统计，服务治理等。
为了解决在实施大规模微服务架构时的链路跟踪问题，SOFATracer 基于 OpenTracing（http://opentracing.io） 规范并扩展其能力，包括基于Disruptor 高性能无锁循环队列的异步落地磁盘的日志打印能力，自定义日志格式，日志自清除和滚动能力，基于SLF4J MDC 的扩展能力，统一的配置能力等。同时 SOFATracer 也对接了开源生态，可以选择将 Tracer 数据对接到 Zipkin 等开源产品。



SOFALookout 是一个利用多维度的 Metrics 对目标系统进行度量和监控的中间件。
Lookout 的多维度 Metrics 参考 Metrics 2.0（http://metrics20.org/spec）标准，提供一整套 Metrics 的处理，包括数据埋点、收集、加工、存储与查询等。SOFALookout 包括客户端与服务器端服务两部分，本次先开源客户端部分，服务端部分代码在整理中。 
SOFALookout 客户端提供了一套 Metrics API 标准，通过它可以方便地对 Java 应用的 Metrics 进行埋点统计。为了方便使用，SOFALookout 客户端默认提供一些扩展模块，它们提供 JVM，OS 等基本 Metrics 信息的统计，遵循该扩展机制，我们可以自定义或集成更多的 Metrics 数据。
另外，SOFALookout 客户端除了支持向 SOFALookout 服务端上报数据外，还支持与社区主流的相关产品，包括Dropwizard,（SpringBoot）Actuator以及 Prometheus 等进行集成和数据适配。



JarsLink 是蚂蚁金服内部使用的一个基于 JAVA 的模块化开发框架，它提供在运行时动态加载模块（一个JAR包）、卸载模块和模块间调用的 API。
目前 Jarslink 2.0 在紧张开发之中，Jarslink2.0是在 Jarslink1.0 基础之上，结合 SOFABoot类隔离框架，提供了更加通用的应用(模块)隔离和通信的实现方案，敬请期待！









