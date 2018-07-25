package org.quickstart.sofaboot.module.service.run;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.quickstart.sofaboot.module.service.facade.SampleJvmService;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.alipay.sofa.runtime.api.annotation.SofaReference;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SofaBootWithModulesTest {

    @SofaReference
    private SampleJvmService sampleJvmService;

    @SofaReference(uniqueId = "annotationImpl")
    private SampleJvmService sampleJvmServiceAnnotationImpl;

    @SofaReference(uniqueId = "serviceClientImpl")
    private SampleJvmService sampleJvmServiceClientImpl;

    @Test
    public void test() {
        Assert.assertEquals("Hello, jvm service xml implementation.", sampleJvmService.message());
        Assert.assertEquals("Hello, jvm service annotation implementation.", sampleJvmServiceAnnotationImpl.message());
        Assert.assertEquals("Hello, jvm service service client implementation.", sampleJvmServiceClientImpl.message());
    }

    @Test
    public void contextLoads() {}
}
