package com.test;

import com.test.util.FileUtil;
import com.test.vo.Account;
import com.test.vo.CashFlow;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.Message;
import org.kie.api.builder.Results;
import org.kie.api.builder.model.KieBaseModel;
import org.kie.api.builder.model.KieModuleModel;
import org.kie.api.io.Resource;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.io.ResourceFactory;

import java.io.File;
import java.io.FileInputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @program: drools-test
 * @description: 动态增删改查规则测试
 * @author: yeshiyuan
 * @create: 2020-01-14 15:21
 **/

public class CURDRuleTest {

    private static KieServices kieServices;

    private static KieModuleModel kieModuleModel;

    private static KieFileSystem kieFileSystem;

    private static KieContainer kieContainer;

    private static String drlPath = "D:\\project_workspace\\drools-test\\src\\main\\resources";

    private final static String RULE_FILE_PRE = "src/main/resources/";

    private final static String RULE_PACKAGE_PRE = "com.ysy.";

    public static void main(String[] args) {

        init();
        triggerRule("curd-test");

        System.out.println("修改规则curd-test");
        String newRule = "package com.test.dynamicEdit;\n" +
                "dialect  \"java\"\n" +
                "\n" +
                "import com.test.vo.Account;\n" +
                "import com.test.vo.CashFlow;\n" +
                "import java.util.ArrayList;\n" +
                "\n" +
                "rule \"test\"\n" +
                "    when\n" +
                "         $a: Account()\n" +
                "         $flows : ArrayList(size > 0) from collect(CashFlow(accountNo == $a.id, type == CashFlow.INCOME))\n" +
                "    then\n" +
                "        double income = 0;\n" +
                "        for (Object flow : $flows) {\n" +
                "             CashFlow cashFlow = (CashFlow)flow;\n" +
                "             income += cashFlow.getAmount();\n" +
                "             System.out.println(cashFlow.getMvtDate() + \" income:\" + cashFlow.getAmount());\n" +
                "        }\n" +
                "        System.out.println($a.getName() + \"收入：\" + income + \"￥\");\n" +
                "end";
        updateRule("curd-test", newRule);
        triggerRule("curd-test");
    }

    public static void init() {
        kieServices = KieServices.Factory.get();
        kieFileSystem = kieServices.newKieFileSystem();
        kieModuleModel = kieServices.newKieModuleModel();
        buildAllRule(drlPath);

    }

    public static void buildAllRule(String initDrlPath) {
        List<File> files = FileUtil.getFiles(initDrlPath);
        for (File file : files) {
            if(file.getName().endsWith(".drl")) {
                System.out.println("build file with filename : " + file.getName());
                String sessionName = file.getName().split("\\.")[0];
                String path = file.getPath().replace("\\", "/");
                //System.out.println("path: " + path + ",index:" + (path.indexOf(RULE_FILE_PRE) + RULE_FILE_PRE.length()) + ",end:" + (path.indexOf(sessionName) - 1));
                String packages = path.substring(path.indexOf(RULE_FILE_PRE) + RULE_FILE_PRE.length(), path.lastIndexOf(sessionName) - 1);


                packages = packages.replace("/", ".");
                KieBaseModel kieBaseModel = kieModuleModel.newKieBaseModel( sessionName).addPackage(packages);
                System.out.println("packages = " + packages);
                kieBaseModel.newKieSessionModel(sessionName);
                try {
                    //Resource resource = ResourceFactory.newFileResource(file);
                    //kieFileSystem.write(resource);
                    System.out.println("path :" + getRuleFilePath(sessionName));
                    kieFileSystem.write(getRuleFilePath(sessionName), FileUtil.readInputStream(new FileInputStream(file)));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        //这边主要目的是定义keyModel让不同的package对应不同的session，这样可以只触发某个session下的规则
        String xml = kieModuleModel.toXML();
        System.out.println(xml);
        kieFileSystem.writeKModuleXML(xml);
        KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem);
        //装载至库
        kieBuilder.buildAll();
        if (kieBuilder.getResults().hasMessages(Message.Level.ERROR)) {
            System.out.println("build rules error :" +  kieBuilder.getResults().toString());
        }
        System.out.println("build rule end");
        //Map<String, KieBaseModel> modelMap = kieModuleModel.getKieBaseModels();
        kieContainer = kieServices.newKieContainer(kieServices.getRepository().getDefaultReleaseId());


    }



   /* public static void test() {
        final String sessionName = "dynamicEditDemo";

        KieServices kieServices = KieServices.Factory.get();
        KieModuleModel kieModuleModel = kieServices.newKieModuleModel();
        KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
        Map<String, KieBaseModel> map = kieModuleModel.getKieBaseModels();
        if (!map.containsKey(sessionName)) {
            KieBaseModel kieBaseModel = kieModuleModel.newKieBaseModel(sessionName).addPackage("com.test.dynamicEdit");
            kieBaseModel.newKieSessionModel(sessionName);
            String xml = kieModuleModel.toXML();
            System.out.println(xml);
            kieFileSystem.writeKModuleXML(xml);
            Resource resource = ResourceFactory.newClassPathResource("com/test/dynamicEdit/test.drl");
            //这里是把规则文件添加到虚拟系统，第一个参数是文件在虚拟系统中的路径
            kieFileSystem.write(resource);
        }
        //7.构建所有的KieBase并把所有的KieBase添加到仓库里
        KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem).buildAll();
        Results results = kieBuilder.getResults();
        if (results.hasMessages(Message.Level.ERROR)) {
            System.out.println(results.getMessages());
            throw new IllegalStateException("### errors ###");
        }

        map = kieModuleModel.getKieBaseModels();

        Long start = System.currentTimeMillis();
        KieContainer kieContainer = kieServices.newKieContainer(kieServices.getRepository().getDefaultReleaseId());//创建kie容器
        System.out.println("初始化KieContainer耗费：" + (System.currentTimeMillis() - start) + "ms");
        KieSession kieSession = kieContainer.newKieSession(sessionName);

        triggerRule(sessionName);

        System.out.println("使用新的规则引擎");
        //修改配置文件
        //kieModuleModel.removeKieBaseModel(sessionName);
        kieFileSystem.delete("src/main/resources/com/test/dynamicEdit/test.drl");

        //kieFileSystem = kieServices.newKieFileSystem();
        //updateRule(kieFileSystem);
        KieBuilder newKieBuilder = kieServices.newKieBuilder(kieFileSystem).buildAll();
        Results results2 = newKieBuilder.getResults();
        if (results2.hasMessages(Message.Level.ERROR)) {
            System.out.println(results2.getMessages());
            throw new IllegalStateException("### errors ###");
        }
        start = System.currentTimeMillis();
        KieContainer newKieContainer = kieServices.newKieContainer(kieServices.getRepository().getDefaultReleaseId());//创建kie容器
        System.out.println("初始化KieContainer耗费：" + (System.currentTimeMillis() - start) + "ms");
        KieSession newKieSession = newKieContainer.newKieSession(sessionName);
        triggerRule(sessionName);
    }*/





    /**
      * @despriction：添加规则
      * @author  yeshiyuan
      * @created 2020/1/15 14:28
      * @params [fileName：文件名, ruleContent:规则体]
      * @return void
      */
    public static void addRule(String fileName,String packages, String ruleContent) {
        System.out.println("******** add rule *******");
        Map<String, KieBaseModel> map = kieModuleModel.getKieBaseModels();
        if (map.containsKey(fileName)) {
            throw new RuntimeException("this rule (" + fileName + ") has exists, please delete before add");
        }
        KieBaseModel kieBaseModel = kieModuleModel.newKieBaseModel(fileName).addPackage(packages);
        kieBaseModel.newKieSessionModel(fileName);
        String xml = kieModuleModel.toXML();
        kieFileSystem.writeKModuleXML(xml);
        kieFileSystem.write(getRuleFilePath(fileName), ruleContent);
        rebuild(kieServices, kieFileSystem);
    }

    /**
      * @despriction：删除规则
      * @author  yeshiyuan
      * @created 2020/1/15 14:45
      * @params [fileName]
      * @return void
      */
    public static void deleteRule(String fileName) {
        System.out.println("******** delete rule *******");
        kieModuleModel.removeKieBaseModel(fileName);
        String xml = kieModuleModel.toXML();
        kieFileSystem.writeKModuleXML(xml);
        kieFileSystem.delete(getRuleFilePath(fileName));
        rebuild(kieServices, kieFileSystem);
    }

    /**
      * @despriction：修改规则
      * @author  yeshiyuan
      * @created 2020/1/15 14:52
      * @params [fileName, ruleContent]
      * @return void
      */
    public static void updateRule(String fileName, String ruleContent) {
        System.out.println("******** update rule *******");
        Map<String, KieBaseModel> modelMap = kieModuleModel.getKieBaseModels();
        if (!modelMap.containsKey(fileName)) {
            throw new RuntimeException("this rule (" + fileName + ") not exists, please add");
        }
        String ruleFilePath = getRuleFilePath(fileName);
        kieFileSystem.delete(ruleFilePath);
        kieFileSystem.write(ruleFilePath, ruleContent);
        rebuild(kieServices, kieFileSystem);
    }

    public static String getRuleFilePath(String fileName) {
        return RULE_FILE_PRE + RULE_PACKAGE_PRE.replace(".", "/")  + fileName + "/" + fileName + ".drl";
    }

    public static void rebuild(KieServices kieServices, KieFileSystem kieFileSystem) {
        KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem).buildAll();
        if (kieBuilder.getResults().hasMessages(Message.Level.ERROR)) {
            System.out.println(kieBuilder.getResults().getMessages());
            throw new IllegalStateException("### add rule errors ###");
        }
        kieContainer = kieServices.newKieContainer(kieServices.getRepository().getDefaultReleaseId());
    }

    public static void triggerRule(String sessionName) {
        KieSession kieSession = kieContainer.newKieSession(sessionName);
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
    }

}
