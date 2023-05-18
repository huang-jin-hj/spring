package com.yes.scan;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.autoconfigure.AutoConfigurationPackages;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.StandardAnnotationMetadata;

import java.util.List;

/**
 * Created by huangJin on 2023/5/17.
 */

public class HJHSFScannerRegistrar implements ImportBeanDefinitionRegistrar {
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        MyHSFScan myHSFScan = new MyHSFScan(registry);
        List<String> luJin = AutoConfigurationPackages.get((BeanFactory) registry);
        myHSFScan.doScan1(String.valueOf(luJin));
    }
}
