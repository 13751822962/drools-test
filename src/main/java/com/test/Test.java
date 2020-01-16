package com.test;

import org.drools.core.RuleBaseConfiguration;
import org.drools.core.marshalling.impl.RuleBaseNodes;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @program: drools-test
 * @description:
 * @author: yeshiyuan
 * @create: 2020-01-02 17:15
 **/
public class Test {

    public static void main(String[] args) {
        A a1 = new A("ysy", 1);
        A a2 = new A("test", 1);
        ConcurrentHashMap<Integer, A> concurrentHashMap = new ConcurrentHashMap<>(2);
        concurrentHashMap.put(a1.getId(), a1);
        concurrentHashMap.put(a2.getId(), a2);

        A a =  concurrentHashMap.get(a1.getId());
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(1000);
                        System.out.println(a.toString());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        new Thread(()->{
            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                A aa = concurrentHashMap.get(a1.getId());
                aa.setName(UUID.randomUUID().toString());
                concurrentHashMap.put(aa.getId(), aa);
            }
        }).start();
    }

    public static class A {
        private String name;

        private Integer id;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public A(String name, Integer id) {
            this.name = name;
            this.id = id;
        }

        @Override
        public String toString() {
            return "A{" +
                    "name='" + name + '\'' +
                    ", id=" + id +
                    '}';
        }
    }

}
