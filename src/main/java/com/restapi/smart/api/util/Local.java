package com.restapi.smart.api.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.Inet4Address;
import java.net.UnknownHostException;


/**
 * @author jihye
 * -아이피 접속 정보
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
        return ip; /*192.168.219.120*/
    }

    public int getPort() {
        return port; /*8081*/
    }

    public String getIP_PORT() {
        System.out.println(ip + ":" + port);
        return ip + ":" + port; /*192.168.219.120:8081*/
    }

}
