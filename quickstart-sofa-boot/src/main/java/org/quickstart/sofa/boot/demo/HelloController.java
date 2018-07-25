package org.quickstart.sofa.boot.demo;

import java.io.IOException;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xuanbei 18/7/17
 */
@RestController
public class HelloController {

    // 访问 http://localhost:8080/hello ，可以看到 HelloController 成功调用到了 service-provide 发布的服务。

    @RequestMapping("/hello")
    public String hello() throws IOException {
        return "Hello SOFA Boot";
    }
}
