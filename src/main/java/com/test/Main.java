package com.test;

import org.drools.core.common.DefaultFactHandle;
import org.kie.api.KieServices;
import org.kie.api.event.rule.DebugAgendaEventListener;
import org.kie.api.event.rule.DebugRuleRuntimeEventListener;
import org.kie.api.logger.KieRuntimeLogger;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;
import org.kie.api.runtime.rule.StatefulRuleSession;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: drools-test
 * @description:
 * @author: yeshiyuan
 * @create: 2019-12-25 09:14
 **/
public class Main {

    public static void main(String[] args) {
        KieServices ks = KieServices.get();
        KieContainer kc = ks.getKieClasspathContainer();
        execute(ks, kc);
    }


    public static void execute(KieServices ks, KieContainer kc) {
        KieSession session = kc.newKieSession("test");
        session.setGlobal("list", new ArrayList<>());
        session.addEventListener(new DebugAgendaEventListener());
        session.addEventListener(new DebugRuleRuntimeEventListener());

        KieRuntimeLogger logger = ks.getLoggers().newFileLogger( session, "./helloworld" );

        final Message message = new Message();
        message.setMessage("yeshiyuan shuai" );
        message.setStatus( Message.TEST );
        DefaultFactHandle factHandle =(DefaultFactHandle) session.insert( message );
        System.out.println(factHandle);


        // and fire the rules
        session.fireAllRules();
        //插入同一对象，FactHandle返回相同的
        FactHandle factHandle1 = session.insert(message);
        System.out.println(factHandle1);
        // Close logger
        logger.close();

        // and then dispose the session
        session.dispose();

    }


    public static class Message {
        public static final int HELLO   = 0;
        public static final int GOODBYE = 1;
        public static final int TEST = 2;

        private String          message;

        private int             status;

        public Message() {

        }

        public String getMessage() {
            return this.message;
        }

        public void setMessage(final String message) {
            this.message = message;
        }

        public int getStatus() {
            return this.status;
        }

        public void setStatus(final int status) {
            this.status = status;
        }

        public static Message doSomething(Message message) {
            return message;
        }

        public boolean isSomething(String msg,
                                   List<Object> list) {
            list.add( this );
            return this.message.equals( msg );
        }
    }

}
