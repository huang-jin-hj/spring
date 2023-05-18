package com.yes.mapper;

import com.yes.HJHSF;
import com.yes.HJHSFCommunication;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by huangJin on 2023/5/17.
 */
public class HJHSFConsumerInvocationBean implements InvocationHandler, FactoryBean {
    private Class<?> hsfInterface;

    @Autowired
    private HJHSFCommunication hjhsfCommunication;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String className = method.getDeclaringClass().getName();
        String methodName = method.getName();
        HJHSF annotationHjhsf = hsfInterface.getAnnotation(HJHSF.class);
        String serviceName = annotationHjhsf.serviceName();



        return null;
    }

    @Override
    public Object getObject() throws Exception {
        return Proxy.newProxyInstance(hsfInterface.getClassLoader(), new Class[]{hsfInterface}, this);
    }

    @Override
    public Class<?> getObjectType() {
        return null;
    }
}
