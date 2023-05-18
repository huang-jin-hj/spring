package com.yes.mapper;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yes.HJHSF;
import com.yes.HJHSFCommunication;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.util.ClassUtils;

import java.beans.Introspector;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Random;
import java.util.Set;

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
        Set<String> metaInfo = this.hjhsfCommunication.getMetaInfo(serviceName);
        String next = metaInfo.iterator().next();
        //永远获取第一个 先不加负载均衡
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("className", buildDefaultBeanName(className));
        jsonObject.put("methodName", methodName);
        JSONObject jsonObjectParameters = new JSONObject();
        for (int i = 0; i < args.length; i++) {
            jsonObjectParameters.put("parameter_" + i, args[i]);
        }
        jsonObject.put("parameters", jsonObjectParameters);

        Class<?> returnType = method.getReturnType();


        String s = hjhsfCommunication.remoteCall(next, JSONObject.toJSONString(jsonObject));
        return JSON.parseObject(s, returnType);
    }

    @Override
    public Object getObject() throws Exception {
        return Proxy.newProxyInstance(hsfInterface.getClassLoader(), new Class[]{hsfInterface}, this);
    }

    @Override
    public Class<?> getObjectType() {
        return null;
    }


    private static String buildDefaultBeanName(String className) {
        String shortClassName = ClassUtils.getShortName(className);
        return Introspector.decapitalize(shortClassName);
    }

    public static void main(String[] args) {
    }
}
