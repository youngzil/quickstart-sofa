/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package org.quickstart.sofa.ark.springboot.controller;

import java.io.IOException;

import org.quickstart.sofa.ark.hessian3.plugin.Hessian3Service;
import org.quickstart.sofa.ark.hessian4.plugin.Hessian4Service;
import org.quickstart.sofa.ark.pojo.plugin.SamplePoJo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author qilong.zql
 */
@RestController
public class HelloController {

    @RequestMapping("/hello-hessian3")
    public String hessian3() throws IOException {
        SamplePoJo samplePoJo = new SamplePoJo("Hello, hessian3.");
        Hessian3Service hessian3Service = new Hessian3Service();
        byte[] bytes = hessian3Service.serialize(samplePoJo);
        Object pojo = hessian3Service.deserialize(bytes);
        return pojo.toString();
    }

    @RequestMapping("/hello-hessian4")
    public String hessian4() throws IOException {
        SamplePoJo samplePoJo = new SamplePoJo("Hello, hessian4.");
        Hessian4Service hessian4Service = new Hessian4Service();
        byte[] bytes = hessian4Service.serialize(samplePoJo);
        Object pojo = hessian4Service.deserialize(bytes);
        return pojo.toString();
    }

}