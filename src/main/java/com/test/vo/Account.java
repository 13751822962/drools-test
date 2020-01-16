package com.test.vo;

import java.util.List;

/**
 * @program: drools-test
 * @description:
 * @author: yeshiyuan
 * @create: 2019-12-30 10:02
 **/
public class Account {

    private Integer id;

    private String name;

    private List<CashFlow> flows;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Account() {
    }

    public List<CashFlow> getFlows() {
        return flows;
    }

    public void setFlows(List<CashFlow> flows) {
        this.flows = flows;
    }

    public Account(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}
