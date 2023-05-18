package com.yes.scan;

import com.yes.HJHSF;
import com.yes.mapper.HJHSFConsumerInvocationBean;
import com.yes.mapper.HJHSFProviderInvocationBean;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.autoconfigure.AutoConfigurationPackages;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Set;

/**
 * Created by huangJin on 2023/5/16.
 */

public class MyHSFScan extends ClassPathBeanDefinitionScanner{


    public MyHSFScan(BeanDefinitionRegistry registry) {
        super(registry);
        registerDefaultFilters();
    }

    @Override
    protected void registerDefaultFilters() {
        this.addIncludeFilter(new AnnotationTypeFilter(HJHSF.class));
    }

    @Override
    protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
        return beanDefinition.getMetadata().isInterface();
    }

    public void doScan1(String... basePackages) {
        Set<BeanDefinitionHolder> beanDefinitionHolders = super.doScan(basePackages);
        for (BeanDefinitionHolder beanDefinitionHolder : beanDefinitionHolders) {
            GenericBeanDefinition definition = (GenericBeanDefinition) beanDefinitionHolder.getBeanDefinition();
            Class<?> beanClass = definition.getBeanClass();
            HJHSF annotation = beanClass.getAnnotation(HJHSF.class);
            if (StringUtils.hasText(annotation.serviceName())){
                HJHSF.Type type = annotation.type();
                definition.getConstructorArgumentValues().addGenericArgumentValue(beanClass);
                if (type == HJHSF.Type.PROVIDER){
                    definition.setBeanClass(HJHSFProviderInvocationBean.class);
                }else {
                    definition.setBeanClass(HJHSFConsumerInvocationBean.class);
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {
        System.out.println(AutoConfigurationPackages.class.getName());

//        MyHSFScan testScan = new MyHSFScan(new DefaultListableBeanFactory());
//        Set<BeanDefinitionHolder> beanDefinitionHolders = testScan.doScan("");
//        System.out.println(beanDefinitionHolders);

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
