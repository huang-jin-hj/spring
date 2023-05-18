package com.yes;

import com.yes.scan.HJHSFScannerRegistrar;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Map;

/**
 * Created by huangJin on 2023/5/15.
 */
@SpringBootApplication
public class Application {


    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);

//        for (Method declaredMethod : B.class.getDeclaredMethods()) {
//            if (declaredMethod.getName().equals("test")){
//                for (Parameter parameter : declaredMethod.getParameters()) {
//                    System.out.println(parameter.getType());
//                }
//            }
//        }

//        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext();
//        RootBeanDefinition rootBeanDefinition = new RootBeanDefinition();
//        rootBeanDefinition.setBeanClass(Teacher.class);
//        rootBeanDefinition.getConstructorArgumentValues().addGenericArgumentValue(new Student());
//        annotationConfigApplicationContext.registerBeanDefinition("teacher", rootBeanDefinition);
//        annotationConfigApplicationContext.refresh();

//        ConfigurableApplicationContext run = SpringApplication.run(Application.class, args);
//        Map<String, B> beansOfType = run.getBeansOfType(B.class);
//        System.out.println(run.getBean("b"));

//        HJHSFAutoConfiguration bean = run.getBean(HJHSFAutoConfiguration.class);
//
//        System.out.println(bean.hjhsfConfigServer.getZkServer());
    }


    static interface A{
        default void test(){

        };
    }

    static class B implements A{
        public void test(String a, String b) {
            System.out.println(123);
        }
    }

}
