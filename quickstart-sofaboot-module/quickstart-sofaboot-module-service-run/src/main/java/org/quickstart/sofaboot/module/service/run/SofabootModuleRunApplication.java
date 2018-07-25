package org.quickstart.sofaboot.module.service.run;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SofabootModuleRunApplication {

    // 访问 http://localhost:8080/hello-sofamodule ，可以看到 HelloController 成功调用到了 service-provide 发布的服务。

    public static void main(String[] args) {
        SpringApplication.run(SofabootModuleRunApplication.class, args);
    }
}
