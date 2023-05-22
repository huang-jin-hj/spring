package com.yes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 * Created by huangJin on 2023/5/15.
 */
@RestController
public class MyController {




    @RequestMapping("/hello")
    public String hello(){
        return "hello";
    }


    @RequestMapping("ipAddress")
    public String ipAddress(){
        String result = "";
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface ni = interfaces.nextElement();
                Enumeration<InetAddress> addresses = ni.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress addr = addresses.nextElement();
                    if (!addr.isLinkLocalAddress() && !addr.isLoopbackAddress() && addr.isSiteLocalAddress()) {
                        System.out.println("IP地址为：" + addr.getHostAddress());
                        result += addr.getHostAddress() + ";";
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("获取IP地址出错：" + e.getMessage());
        }

        return result;
    }

    @RequestMapping("ipAddress1")
    public String ipAddress1(){
        String result = "";
        try {
            InetAddress addr = InetAddress.getLocalHost();
            String ip = addr.getHostAddress();
            System.out.println("IP地址为：" + ip);
            result = ip;
        } catch (Exception e) {
            System.out.println("获取IP地址出错：" + e.getMessage());
        }

        return result;
    }

}
