package com.yes.scan;

import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;

import java.util.Set;

/**
 * Created by huangJin on 2023/5/16.
 */

public class MyHSFScan extends ClassPathBeanDefinitionScanner {
    public MyHSFScan(BeanDefinitionRegistry registry) {
        super(registry, false);
        registerDefaultFilters();
    }

    @Override
    protected void registerDefaultFilters() {
        this.addIncludeFilter((metadataReader, metadataReaderFactory) -> true);
    }

    @Override
    protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
        return beanDefinition.getMetadata().isInterface();
    }

    public static void main(String[] args) throws Exception {
        MyHSFScan testScan = new MyHSFScan(new DefaultListableBeanFactory());
        Set<BeanDefinitionHolder> beanDefinitionHolders = testScan.doScan("com.yes.mapper");
        System.out.println(beanDefinitionHolders);

//        PathMatchingResourcePatternResolver pathMatchingResourcePatternResolver = new PathMatchingResourcePatternResolver();
//         MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(pathMatchingResourcePatternResolver);
//
//        Resource[] resources = pathMatchingResourcePatternResolver.getResources("classpath*:com/yes/mapper/**/*.class");
//
//        MetadataReader metadataReader = metadataReaderFactory.getMetadataReader(resources[0]);
//
//        ScannedGenericBeanDefinition scannedGenericBeanDefinition = new ScannedGenericBeanDefinition(metadataReader);
//
//        AnnotationMetadata metadata = scannedGenericBeanDefinition.getMetadata();
    }
}
