package com.restapi.smart.api.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.Inet4Address;
import java.net.UnknownHostException;

/*
    아이피 가져오기
 */
@Component
public  final class Local {
    private static Local instance;
    private String ip = ""; //아이피
    @Value("${server.port}")
    private int port; //포트 정보는 /resources/application.properties에 "server.port"있음

    public Local() {
        try {
            ip = Inet4Address.getLocalHost().getHostAddress();// IPv4 아이피 출력.
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public String getIP() {
        return ip;
    }

    public int getPort() {
        return port;
    }

    public String getIP_PORT() {
        return ip + ":"+port; /*192.168.219.120:8081*/
    }

}
