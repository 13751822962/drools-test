package com.test.filter;

import org.kie.api.runtime.rule.AgendaFilter;
import org.kie.api.runtime.rule.Match;

/**
 * @program: drools-test
 * @description: 规则名称过滤
 * @author: yeshiyuan
 * @create: 2019-12-26 11:10
 **/
public class RuleNameFilter implements AgendaFilter {

    private String ruleName;

    public RuleNameFilter(String ruleName) {
        this.ruleName = ruleName;
    }

    @Override
    public boolean accept(Match match) {
        return match.getRule().getName().equals(ruleName);
    }
}
