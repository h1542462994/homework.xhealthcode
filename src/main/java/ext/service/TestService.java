package ext.service;

import ext.declare.ITestService;

public class TestService implements ITestService {
    @Override
    public String hello() {
        return "hello";
    }
}
