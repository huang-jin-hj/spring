package com.yes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by huangJin on 2023/5/15.
 */
@SpringBootApplication
public class Application {
    public static void main(String[] args) throws Exception {
//        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext();
//        RootBeanDefinition rootBeanDefinition = new RootBeanDefinition();
//        rootBeanDefinition.setBeanClass(Teacher.class);
//        rootBeanDefinition.getConstructorArgumentValues().addGenericArgumentValue(new Student());
//        annotationConfigApplicationContext.registerBeanDefinition("teacher", rootBeanDefinition);
//        annotationConfigApplicationContext.refresh();


        ConfigurableApplicationContext run = SpringApplication.run(Application.class, args);
        Test bean = run.getBean(Test.class);

//        HJHSFAutoConfiguration bean = run.getBean(HJHSFAutoConfiguration.class);
//
//        System.out.println(bean.hjhsfConfigServer.getZkServer());
    }

}
