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
 * @description: collect demo
 * @author: yeshiyuan
 * @create: 2019-12-31 16:51
 **/
public class CollectDemo {

    public static void main(String[] args) {
        KieSession kieSession = KieSessionHelper.getSession("collectDemo");

        Account account = new Account(1,"ysy");

        //List<CashFlow> flows = new ArrayList<>();
        CashFlow cashFlow = new CashFlow(new Date(), 20.00, CashFlow.INCOME, 1);
        CashFlow cashFlow2 = new CashFlow(new Date(), 10.00, CashFlow.TRANSFER, 1);
        CashFlow cashFlow3 = new CashFlow(new Date(), 13.00, CashFlow.INCOME, 1);
        CashFlow cashFlow4 = new CashFlow(new Date(), 19.00, CashFlow.INCOME, 1);
        kieSession.insert(cashFlow);
        kieSession.insert(cashFlow2);
        kieSession.insert(cashFlow3);
        kieSession.insert(cashFlow4);


        kieSession.insert(account);
        kieSession.fireAllRules();
        kieSession.dispose();
    }

}
