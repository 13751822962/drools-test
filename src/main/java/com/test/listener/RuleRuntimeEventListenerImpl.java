package com.test.listener;

import org.kie.api.event.rule.ObjectDeletedEvent;
import org.kie.api.event.rule.ObjectInsertedEvent;
import org.kie.api.event.rule.ObjectUpdatedEvent;
import org.kie.api.event.rule.RuleRuntimeEventListener;

/**
 * @program: drools-test
 * @description: RuleRuntimeEventListener实现
 * @author: yeshiyuan
 * @create: 2019-12-26 19:50
 **/
public class RuleRuntimeEventListenerImpl implements RuleRuntimeEventListener {

    public void objectInserted(ObjectInsertedEvent event) {
        System.out.println("Object inserted: \n\t"
                + event.getObject().toString());
    }
    public void objectUpdated(ObjectUpdatedEvent event) {
        System.out.println("Object was updated: \n\t"
                + "new Content :" + event.getObject().toString());
    }
    public void objectDeleted(ObjectDeletedEvent event) {
        System.out.println("Object retracted: \n\t"
                + event.getOldObject().toString());
    }
}
