package com.test;

import com.test.listener.AgendaEventListenerimpl;
import com.test.listener.RuleRuntimeEventListenerImpl;
import org.kie.api.KieServices;
import org.kie.api.event.rule.DebugAgendaEventListener;
import org.kie.api.event.rule.DebugRuleRuntimeEventListener;
import org.kie.api.logger.KieRuntimeLogger;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;

/**
 * @program: drools-test
 * @description: no-loop demo
 * @author: yeshiyuan
 * @create: 2019-12-26 15:31
 **/
public class LoopDemo {

    public static void main(String[] args) {
        LoopDemo demo = new LoopDemo();
        final B b = demo.new B();
        b.setStatus(StatusEnum.START);
        b.setName("sad");

        KieServices kieServices = KieServices.get();
        KieContainer kieContainer = kieServices.getKieClasspathContainer();
        KieSession kieSession = kieContainer.newKieSession("loopDemo");
       // kieSession.addEventListener(new DebugAgendaEventListener());
        //kieSession.addEventListener(new DebugRuleRuntimeEventListener());
        KieRuntimeLogger logger = kieServices.getLoggers().newFileLogger( kieSession, "./loopDemo" );


        kieSession.addEventListener(new AgendaEventListenerimpl());
        kieSession.addEventListener(new RuleRuntimeEventListenerImpl());

        FactHandle factHandle = kieSession.insert(b);
        System.out.println("first fire");
        kieSession.fireAllRules();
        System.out.println("second fire");
        kieSession.update(factHandle, b);
        kieSession.fireAllRules();
        kieSession.dispose();

    }

    public class B {

        private StatusEnum status;

        private String name;

        public StatusEnum getStatus() {
            return status;
        }

        public void setStatus(StatusEnum status) {
            this.status = status;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "B{" +
                    "status=" + status +
                    ", name='" + name + '\'' +
                    '}';
        }
    }


    public enum StatusEnum {
        STOP(0),
        START(1),
        RESTART(2);

        private int code;

        StatusEnum(int code) {
            this.code = code;
        }
    }
}
