package com.test.listener;

import org.kie.api.event.rule.*;

/**
 * @program: drools-test
 * @description: AgendaEventListener实现
 * @author: yeshiyuan
 * @create: 2019-12-26 19:47
 **/
public class AgendaEventListenerimpl implements AgendaEventListener {

    @Override
    public void matchCreated(MatchCreatedEvent event) {
        System.out.println("The rule "
                + event.getMatch().getRule().getName()
                + " can be fired in agenda");
    }

    @Override
    public void matchCancelled(MatchCancelledEvent event) {
        System.out.println("The rule "
                + event.getMatch().getRule().getName()
                + " cannot be fired in agenda");
    }

    @Override
    public void beforeMatchFired(BeforeMatchFiredEvent event) {
        System.out.println("The rule "
                + event.getMatch().getRule().getName()
                + " will be fired");
    }

    @Override
    public void afterMatchFired(AfterMatchFiredEvent event) {
        System.out.println("The rule "
                + event.getMatch().getRule().getName()
                + " has be fired");
    }

    @Override
    public void agendaGroupPopped(AgendaGroupPoppedEvent agendaGroupPoppedEvent) {

    }

    @Override
    public void agendaGroupPushed(AgendaGroupPushedEvent agendaGroupPushedEvent) {

    }

    @Override
    public void beforeRuleFlowGroupActivated(RuleFlowGroupActivatedEvent ruleFlowGroupActivatedEvent) {

    }

    @Override
    public void afterRuleFlowGroupActivated(RuleFlowGroupActivatedEvent ruleFlowGroupActivatedEvent) {

    }

    @Override
    public void beforeRuleFlowGroupDeactivated(RuleFlowGroupDeactivatedEvent ruleFlowGroupDeactivatedEvent) {

    }

    @Override
    public void afterRuleFlowGroupDeactivated(RuleFlowGroupDeactivatedEvent ruleFlowGroupDeactivatedEvent) {

    }
}
