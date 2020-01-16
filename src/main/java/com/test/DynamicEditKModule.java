package com.test;

import org.drools.core.impl.InternalKnowledgeBase;
import org.drools.core.impl.KnowledgeBaseFactory;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.model.KieBaseModel;
import org.kie.api.builder.model.KieModuleModel;
import org.kie.api.event.rule.DebugAgendaEventListener;
import org.kie.api.event.rule.DebugRuleRuntimeEventListener;
import org.kie.api.io.Resource;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.builder.KnowledgeBuilder;
import org.kie.internal.builder.KnowledgeBuilderError;
import org.kie.internal.builder.KnowledgeBuilderErrors;
import org.kie.internal.builder.KnowledgeBuilderFactory;
import org.kie.internal.io.ResourceFactory;

/**
 * @program: drools-test
 * @description: 动态往kmodule.xml添加配置
 * @author: yeshiyuan
 * @create: 2019-12-25 15:13
 **/
public class DynamicEditKModule {

    public static void main(String[] args) {
        //1.获取一个KieServices
        KieServices kieServices = KieServices.Factory.get();
        //2.创建kiemodule xml对应的class
        KieModuleModel kieModuleModel = kieServices.newKieModuleModel();
        //3.创建KieFileSystem虚拟文件系统
        KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
        //4.添加具体的KieBase标签
        KieBaseModel kieBaseModel = kieModuleModel.newKieBaseModel("xx").addPackage("com.ysy");
        //<KieBase></KieBase>标签添加KieSession属性
        kieBaseModel.newKieSessionModel("testRule");
        //5.添加kiemodule.xml文件到虚拟文件系统
        String xml = kieModuleModel.toXML();
        System.out.println(xml);
        kieFileSystem.writeKModuleXML(xml);
        //6.把规则文件加载到虚拟文件系统
        Resource resource =  ResourceFactory.newClassPathResource("com/ysy/xx.drl");
        //这里是把规则文件添加到虚拟系统，第一个参数是文件在虚拟系统中的路径
        kieFileSystem.write(resource);

        //7.构建所有的KieBase并把所有的KieBase添加到仓库里
        kieServices.newKieBuilder(kieFileSystem).buildAll();
        KieContainer kieContainer = kieServices.newKieContainer(kieServices.getRepository().getDefaultReleaseId());//创建kie容器


        //8.从容器中获取一个会话，这里和a处添加的是一个key，否则找不到 找不到任何一个会报异常
        KieSession kieSession = kieContainer.newKieSession("testRule");
        kieSession.addEventListener(new DebugAgendaEventListener());
        kieSession.addEventListener(new DebugRuleRuntimeEventListener());
        kieSession.fireAllRules();
        kieSession.dispose();

        dynamicAddRule();
    }

    public static void dynamicAddRule() {
        //动态编辑规则
        StringBuilder stringBuilder = new StringBuilder()
                .append("package com.yy\n")
                .append("rule \"hh\"\r\n")
                .append("then {\n")
                .append("System.out.println(\"this is dynamic add rule!!!\");\n")
                .append("}\nend");
        Resource resource = ResourceFactory.newByteArrayResource(stringBuilder.toString().getBytes());
        KnowledgeBuilder kb = KnowledgeBuilderFactory.newKnowledgeBuilder();
        kb.add(resource, ResourceType.DRL);
        KnowledgeBuilderErrors errors = kb.getErrors();
        for (KnowledgeBuilderError error : errors) {
            System.out.println(error);
        }



        InternalKnowledgeBase kieBase = KnowledgeBaseFactory.newKnowledgeBase();
        kieBase.addPackages(kb.getKnowledgePackages());



        KieSession kieSession = kieBase.newKieSession();
        kieSession.fireAllRules();
        kieSession.dispose();
    }

}
