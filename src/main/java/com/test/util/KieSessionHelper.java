package com.test.util;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.StatelessKieSession;

/**
 * @program: drools-test
 * @description:
 * @author: yeshiyuan
 * @create: 2019-12-30 10:50
 **/
public class KieSessionHelper {

    public static KieContainer getKieContainer() {
        KieServices ks = KieServices.Factory.get();
        KieContainer kieContainer = ks.getKieClasspathContainer();
        return kieContainer;
    }

    public static KieSession getSession(KieContainer kieContainer, String sessionName) {
        KieSession kSession = kieContainer.newKieSession(sessionName);
        return kSession;
    }


    public static KieSession getSession(String sessionName) {
        KieSession kSession = getKieContainer().newKieSession(sessionName);
        return kSession;
    }

}
