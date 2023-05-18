package com.yes;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yes.mapper.HJHSFConsumerInvocationBean;
import com.yes.mapper.HJHSFProviderInvocationBean;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by huangJin on 2023/5/17.
 */


public class HJHSFCommunication implements ApplicationContextAware {
    private ServerSocket socket;

    private ApplicationContext applicationContext;

    @Autowired
    private HJHSFWatch hjhsfWatch;



    @Autowired
    public HJHSFCommunication(HJHSFConfigServer hjhsfConfigServer) throws IOException {
        this.socket = new ServerSocket(StringUtils.hasText(String.valueOf(hjhsfConfigServer.getPort())) ? hjhsfConfigServer.getPort() : 8989);
        listen();
    }


    public void createService(String pathInfo){
        this.hjhsfWatch.createService(pathInfo);
    }

    public Set getMetaInfo(String serviceName){
        return this.hjhsfWatch.getMetaInfo(serviceName);
    }

    public void listen(){
        new Thread(() -> {
            while (true){
                Socket accept;
                try {
                    accept = this.socket.accept();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                new Thread(() -> {
                    InputStream inputStream = null;
                    OutputStream outputStream = null;
                    try {
                        inputStream = accept.getInputStream();
                        outputStream = accept.getOutputStream();
                        byte[] bytes = new byte[1024];
                        int read = inputStream.read(bytes);
                        Object o = parseRequest(new String(bytes, 0, read));
                        outputStream.write(JSON.toJSONString(o).getBytes());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    } catch (InvocationTargetException e) {
                        throw new RuntimeException(e);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    } finally {
                        try {
                            accept.close();
                            inputStream.close();
                            outputStream.close();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }).start();
            }
        }).start();
    }


    public Object parseRequest(String s) throws InvocationTargetException, IllegalAccessException {
        JSONObject jsonObject = JSON.parseObject(s);
        String className = (String)jsonObject.get("className");
        String methodName = (String)jsonObject.get("methodName");
        JSONObject parameters = (JSONObject)jsonObject.get("parameters");
        Object target = applicationContext.getBean(className);

        for (Method method : target.getClass().getMethods()) {
            if (method.getName().equals(methodName)){
                Object[] args = new Object[method.getParameters().length];
                for (int i = 0; i < method.getParameters().length; i++) {
                    args[i] = JSON.parseObject(parameters.get("parameter_" + i).toString(), method.getParameters()[i].getType());
                }
                return method.invoke(target, args);
            }
        }
        return null;

    }


    public String remoteCall(String methInfo, String jsonData) throws IOException {
        String[] ip_port = methInfo.split("|");
        Socket socket = new Socket(ip_port[0], Integer.parseInt(ip_port[1]));
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write(jsonData.getBytes());
        InputStream inputStream = socket.getInputStream();
        byte[] bytes = new byte[1024];
        int read = inputStream.read(bytes);
        return new String(bytes,0, read);
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
