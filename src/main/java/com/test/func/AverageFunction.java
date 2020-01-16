package com.test.func;

import org.drools.core.base.accumulators.AverageAccumulateFunction;
import org.kie.api.runtime.rule.AccumulateFunction;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @program: drools-test
 * @description: 自定义accumulate的average实现
 * @author: yeshiyuan
 * @create: 2020-01-03 14:19
 **/
public class AverageFunction implements AccumulateFunction<AverageAccumulateFunction.AverageData> {

    @Override
    public AverageAccumulateFunction.AverageData createContext() {
        return new AverageAccumulateFunction.AverageData();
    }

    @Override
    public void init(AverageAccumulateFunction.AverageData averageData) throws Exception {
        averageData.count = 0;
        averageData.total = 0D;
    }

    @Override
    public void accumulate(AverageAccumulateFunction.AverageData averageData, Object o) {
        if (o != null) {
            averageData.count++;
            averageData.total += ((Number) o).doubleValue();
        }
    }

    @Override
    public void reverse(AverageAccumulateFunction.AverageData averageData, Object o) throws Exception {
        if (o != null) {
            averageData.count++;
            averageData.total -= ((Number) o).doubleValue();
        }
    }

    @Override
    public Object getResult(AverageAccumulateFunction.AverageData averageData) throws Exception {
        if (averageData.count == 0 || averageData.total == 0) {
            return 0;
        } else {
            return new BigDecimal(averageData.total).divide(new BigDecimal(averageData.count), 2, RoundingMode.UP).doubleValue();
        }
    }

    @Override
    public boolean supportsReverse() {
        return true;
    }

    @Override
    public Class<?> getResultType() {
        return Number.class;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {

    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {

    }
}
