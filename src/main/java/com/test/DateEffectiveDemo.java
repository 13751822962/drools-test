package com.test;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

/**
 * @program: drools-test
 * @description: date-effective使用demo
 * @author: yeshiyuan
 * @create: 2019-12-26 11:25
 **/
public class DateEffectiveDemo {

    public static void main(String[] args) {
        System.setProperty("drools.dateformat", "yyyy-MM-dd"); // 默认是dd-MMM-yyyy，通过此代码把规则设置为yyyy-MM-dd
        KieServices kieServices = KieServices.get();
        KieContainer kieContainer = kieServices.getKieClasspathContainer();
        KieSession kieSession = kieContainer.newKieSession("dateEffectiveDemo");
        kieSession.fireAllRules();
        kieSession.dispose();
    }

}
