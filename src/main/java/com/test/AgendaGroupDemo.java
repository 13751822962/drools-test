package com.test;

import org.kie.api.KieServices;
import org.kie.api.event.rule.DebugAgendaEventListener;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

/**
 * @program: drools-test
 * @description: 使用agendaGroup例子
 * @author: yeshiyuan
 * @create: 2019-12-26 09:41
 **/
public class AgendaGroupDemo {

    public static void main(String[] args) {
        KieServices kieServices = KieServices.get();
        KieContainer kieContainer = kieServices.getKieClasspathContainer();
        KieSession kieSession = kieContainer.newKieSession("agendaGroupDemo");
        //kieSession.addEventListener(new DebugAgendaEventListener());
        kieSession.getAgenda().getAgendaGroup("group1").setFocus();
        kieSession.getAgenda().getAgendaGroup("group2").setFocus(); //先执行group2
        kieSession.fireAllRules();
        kieSession.dispose();
    }

}
