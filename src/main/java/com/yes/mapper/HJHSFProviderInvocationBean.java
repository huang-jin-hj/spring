package com.yes.mapper;

import com.yes.HJHSF;
import com.yes.HJHSFCommunication;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by huangJin on 2023/5/16.
 */
public class HJHSFProviderInvocationBean implements InvocationHandler, FactoryBean, ApplicationContextAware, InitializingBean {
    private Class<?> hsfInterface;

    private ApplicationContext applicationContext;

    @Autowired
    private HJHSFCommunication hjhsfCommunication;

    private Object targetBean;

    public HJHSFProviderInvocationBean(Class hsfInterface) {
        this.hsfInterface = hsfInterface;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (this.targetBean == null){
            synchronized (this){
                if (this.targetBean == null){
                    this.targetBean = this.applicationContext.getBean(this.hsfInterface);
                }
            }
        }
        return method.invoke(targetBean, args);
    }

    @Override
    public Object getObject() throws Exception {
        return Proxy.newProxyInstance(hsfInterface.getClassLoader(), new Class[]{hsfInterface}, this);
    }

    @Override
    public Class<?> getObjectType() {
        return this.hsfInterface;
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        HJHSF annotation = this.hsfInterface.getAnnotation(HJHSF.class);
        String serviceName = annotation.serviceName();
//        hjhsfCommunication.

    }
}
