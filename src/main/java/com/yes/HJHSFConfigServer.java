package com.yes;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by huangJin on 2023/5/16.
 */
@ConfigurationProperties(prefix = "hjhsf")
public class HJHSFConfigServer {
    private String zkServer;

    public String getZkServer() {
        return zkServer;
    }

    public void setZkServer(String zkServer) {
        this.zkServer = zkServer;
    }
}
