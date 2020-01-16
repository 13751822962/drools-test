package com.test;

import com.test.util.KieSessionHelper;
import com.test.vo.Account;
import com.test.vo.CashFlow;
import org.kie.api.runtime.KieSession;

import java.util.Date;

/**
 * @program: drools-test
 * @description: 自定义accumulate demo
 * @author: yeshiyuan
 * @create: 2020-01-03 14:38
 **/
public class CustomAccumulateDemo {

    public static void main(String[] args) {
        KieSession kieSession = KieSessionHelper.getSession("customAccumulateDemo");

        Account account = new Account(1,"ysy");

        //List<CashFlow> flows = new ArrayList<>();
        CashFlow cashFlow = new CashFlow(new Date(), 20.00, CashFlow.INCOME, 1);
        CashFlow cashFlow3 = new CashFlow(new Date(), 13.00, CashFlow.INCOME, 1);
        CashFlow cashFlow4 = new CashFlow(new Date(), 19.00, CashFlow.INCOME, 1);
        kieSession.insert(cashFlow);
        kieSession.insert(cashFlow3);
        kieSession.insert(cashFlow4);


        kieSession.insert(account);
        kieSession.fireAllRules();
        kieSession.dispose();




    }

}
