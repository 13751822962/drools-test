package com.test;

import com.test.util.KieSessionHelper;
import com.test.vo.Account;
import com.test.vo.CashFlow;
import org.kie.api.runtime.KieSession;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @program: drools-test
 * @description: from用法
 * @author: yeshiyuan
 * @create: 2019-12-31 14:23
 **/
public class FromDemo {

    public static void main(String[] args) {
        KieSession kieSession = KieSessionHelper.getSession("fromDemo");

        Account account = new Account(1,"ysy");

        List<CashFlow> flows = new ArrayList<>();
        CashFlow cashFlow = new CashFlow(new Date(), 20.00, CashFlow.INCOME, 1);
        CashFlow cashFlow2 = new CashFlow(new Date(), 10.00, CashFlow.TRANSFER, 21);
        flows.add(cashFlow);
        flows.add(cashFlow2);
        account.setFlows(flows);

        kieSession.insert(account);
        kieSession.fireAllRules();
        kieSession.dispose();

    }

}
