package com.test;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

/**
 * @program: drools-test
 * @description: activation-group用法
 * @author: yeshiyuan
 * @create: 2019-12-26 10:21
 **/
public class ActivationGroupDemo {

    public static void main(String[] args) {
        KieServices kieServices = KieServices.get();
        KieContainer kieContainer = kieServices.getKieClasspathContainer();
        KieSession kieSession = kieContainer.newKieSession("activationGroupDemo");
        kieSession.getAgenda().getActivationGroup("foo");
        kieSession.fireAllRules();
    }

}
