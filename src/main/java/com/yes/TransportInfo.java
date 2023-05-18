package com.yes;

/**
 * Created by huangJin on 2023/5/17.
 */
public class TransportInfo {
    String className;

    String methodName;

    String jsonData;

    public TransportInfo(String className, String methodName, String jsonData) {
        this.className = className;
        this.methodName = methodName;
        this.jsonData = jsonData;
    }
}
