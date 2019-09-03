package com.restapi.smart.api.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import lombok.extern.java.Log;

/**
 *
 * @author jihye
 *
 */
@Log
public class KakaoPay {

    private final String KAKAO_URL = "https://kapi.kakao.com";
    private final String ADMIN_KEY = "88a3b5fe88dd9bb524787db04e79159c";

    //통신 환경 설정
    private RestTemplate network;
    private HttpHeaders headers;

    public KakaoPay() {
        //통신 객체 생성
        network = new RestTemplate();
        headers = new HttpHeaders();
    }

    private void headerSetting() {
        headers.add("Authorization", "KakaoAK " + ADMIN_KEY);
        headers.add("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE);
        headers.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=UTF-8");
    }

    public Map payTest() {

        headerSetting();

        // 서버로 요청할 Body
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("cid", "TC0ONETIME");
        params.add("partner_order_id", "1001");
        params.add("partner_user_id", "gorany");
        params.add("item_name", "갤럭시S9");
        params.add("quantity", "1");
        params.add("total_amount", "300");
        params.add("tax_free_amount", "100");
        params.add("approval_url", "http://localhost:8089/kakao/kakaoPaySuccess");
        params.add("cancel_url", "http://localhost:8089/kakao/kakaoPayCancel");
        params.add("fail_url", "http://localhost:8089/kakao/kakaoPaySuccessFail");

        HttpEntity<MultiValueMap<String, String>> body = new HttpEntity<MultiValueMap<String, String>>(params, headers);
        Map result = null;

        try {

            result = network.postForObject(new URI(KAKAO_URL + "/v1/payment/ready"), body, Map.class);

            //log.info("응답 : " + result.get("next_redirect_app_url"));

            //return kakaoPayReadyVO.getNext_redirect_pc_url();

        } catch (RestClientException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return result;

    }

}