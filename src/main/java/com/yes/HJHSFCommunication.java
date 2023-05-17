package com.yes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by huangJin on 2023/5/17.
 */


public class HJHSFCommunication {
    private ServerSocket socket;

    @Autowired
    public HJHSFCommunication(HJHSFConfigServer hjhsfConfigServer) throws IOException {
        this.socket = new ServerSocket(StringUtils.hasText(String.valueOf(hjhsfConfigServer.getPort())) ? hjhsfConfigServer.getPort() : 8989);
    }

    public void listen(){
        new Thread(() -> {
            while (true){
                try {
                    Socket accept = this.socket.accept();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }



}
