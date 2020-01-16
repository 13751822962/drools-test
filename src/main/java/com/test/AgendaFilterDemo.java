package com.test;

import com.test.filter.RuleNameFilter;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

/**
 * @program: drools-test
 * @description: 指定过滤规则执行
 * @author: yeshiyuan
 * @create: 2019-12-26 11:12
 **/
public class AgendaFilterDemo {

    public static void main(String[] args) {
        KieServices kieServices = KieServices.get();
        KieContainer kieContainer = kieServices.getKieClasspathContainer();
        KieSession kieSession = kieContainer.newKieSession("activationGroupDemo");
        kieSession.fireAllRules(new RuleNameFilter("bb"));
        kieSession.dispose();
    }

}
