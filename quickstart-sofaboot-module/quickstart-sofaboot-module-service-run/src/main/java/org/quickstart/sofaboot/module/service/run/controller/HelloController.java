package org.quickstart.sofaboot.module.service.run.controller;

import java.io.IOException;

import org.quickstart.sofaboot.module.service.facade.SampleJvmService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alipay.sofa.runtime.api.annotation.SofaReference;

/**
 * @author xuanbei 18/7/17
 */
@RestController
public class HelloController {

    // 访问 http://localhost:8080/hello-sofamodule ，可以看到 HelloController 成功调用到了 service-provide 发布的服务。

    @SofaReference
    private SampleJvmService sampleService;

    @RequestMapping("/hello-sofamodule")
    public String hello() throws IOException {
        return sampleService.message();
    }
    
}
