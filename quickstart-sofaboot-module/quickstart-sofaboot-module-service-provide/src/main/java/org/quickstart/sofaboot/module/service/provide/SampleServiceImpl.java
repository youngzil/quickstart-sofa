package org.quickstart.sofaboot.module.service.provide;

import org.quickstart.sofaboot.module.service.facade.SampleService;

/**
 * @author xuanbei 18/7/17
 */
public class SampleServiceImpl implements SampleService {

    private String message;

    public String message() {
        return message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
