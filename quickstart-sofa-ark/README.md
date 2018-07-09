https://github.com/alipay/sofa-ark


https://github.com/alipay/sofa-boot#22-%E6%8F%90%E4%BE%9B%E7%B1%BB%E9%9A%94%E7%A6%BB%E7%9A%84%E8%83%BD%E5%8A%9B
为了解决 Spring Boot 下的类依赖冲突的问题，SOFABoot 基于 SOFAArk 提供了 Spring Boot 上的类隔离的能力，在一个 SOFABoot 的系统中，只要引入 SOFAArk 相关的依赖，就可以将 SOFA 中间件相关的类和应用相关的类的 ClassLoader 进行隔离，防止出现类冲突。当然，用户也可以基于 SOFAArk，将其他的中间件、第三方的依赖和应用的类进行隔离。

SOFABoot 是蚂蚁金服中间件团队开源的基于 Spring Boot 的一个开发框架，其在 Spring Boot 基础能力之上，增加了类隔离能力。蚂蚁金服内部丰富的实践场景表明，类隔离能力对解决类冲突、版本管控有其特殊的优势。

SOFABoot 的类隔离能力由单独的组件 SOFAArk 实现，相比业界遵循 OSGi（https://www.osgi.org/） 规范的 Equinox 或者 Felix，SOFAArk 专注于类隔离，简化了类加载模型，是一款更加轻量的类隔离框架。






在蚂蚁内部，中间件都是打包成一个个 ark，和业务系统做隔离的。否则，中间件的升级会比较痛苦，即使你在尽可能小心地控制你的依赖的情况下，还是可能一个包和业务依赖的一个包完全是冲突的，这种情况下，就要花比较多的时间去解决这种依赖的冲突。

这两个差别其实还挺大的，原则上来说是完全两个东西，解决的也是不同的问题。 jarslink是着重是把业务开发的多个java模块使用不同的类加载器加载，多个模块之间没有类导入导出的概念，类加载模型是主要还是遵循传统的双亲加载机制。另外和spring强绑定，每个模块拥有独立的spring上下文，共享同一个spring父上下文，模块之间通过父spring上下文通信。







