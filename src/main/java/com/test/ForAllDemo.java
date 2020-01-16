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
 * @description: for all 用法
 * @author: yeshiyuan
 * @create: 2019-12-30 10:00
 **/
public class ForAllDemo {


    public static void main(String[] args) throws Exception {
        KieSession kieSession = KieSessionHelper.getSession("forAllDemo");



        Account account = new Account(1,"ysy");
        kieSession.insert(account);
        CashFlow cashFlow = new CashFlow(new Date(), 20.00, CashFlow.INCOME, 1);
        kieSession.insert(cashFlow);


        CashFlow cashFlow2 = new CashFlow(new Date(), 10.00, CashFlow.TRANSFER, 21);
        kieSession.insert(cashFlow2);

       /* CashFlow cashFlow3 = new CashFlow(new Date(), 10.00, CashFlow.TRANSFER, 222);
        kieSession.insert(cashFlow3);*/
        kieSession.fireAllRules();
        kieSession.dispose();
    }


}
