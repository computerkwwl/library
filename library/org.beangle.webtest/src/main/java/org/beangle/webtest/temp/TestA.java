package org.beangle.webtest.temp;

import org.testng.ITestContext;
import org.testng.annotations.Test;

public class TestA extends TestBase {

    public TestA() {
    }

    @Test
    public void test1(ITestContext context) {
        System.out.println(getSelenium(context));
    }
    
    @Test
    public void test2(ITestContext context) {
        System.out.println(getSelenium(context));
    }
}
