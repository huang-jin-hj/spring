package com.yes.mapper;

import org.springframework.beans.factory.FactoryBean;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by huangJin on 2023/5/16.
 */
public class HJHSFInvocationBean implements InvocationHandler, FactoryBean {
    private Class hsfInterface;

    public HJHSFInvocationBean(Class hsfInterface) {
        this.hsfInterface = hsfInterface;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        return null;
    }

    @Override
    public Object getObject() throws Exception {
        return Proxy.newProxyInstance(hsfInterface.getClassLoader(), new Class[]{hsfInterface}, this);
    }

    @Override
    public Class<?> getObjectType() {
        return this.hsfInterface;
    }


}
