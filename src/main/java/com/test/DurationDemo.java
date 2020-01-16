package com.test;

import com.test.filter.RuleNameFilter;
import org.kie.api.KieServices;
import org.kie.api.event.rule.DebugAgendaEventListener;
import org.kie.api.event.rule.DebugRuleRuntimeEventListener;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

/**
 * @program: drools-test
 * @description:
 * @author: yeshiyuan
 * @create: 2019-12-26 13:47
 **/
public class DurationDemo {

    public static void main(String[] args) {
        KieServices kieServices = KieServices.get();
        KieContainer kieContainer = kieServices.getKieClasspathContainer();
        KieSession kieSession = kieContainer.newKieSession("durationDemo");
        kieSession.addEventListener(new DebugAgendaEventListener());
        kieSession.addEventListener(new DebugRuleRuntimeEventListener());
        kieSession.fireAllRules();
        //kieSession.dispose();
    }
}
