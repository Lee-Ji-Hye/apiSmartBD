package com.restapi.smart.api.pay;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

import com.restapi.smart.api.vo.FoodOrderInfoVO;
import com.restapi.smart.api.vo.KakaoPayApprovalVO;
import com.restapi.smart.api.vo.KakaoReadyRequestVO;
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
		
	}
	
	private void headerSetting() {
		if(headers == null) {
			headers = new HttpHeaders();
			headers.add("Authorization", "KakaoAK " + ADMIN_KEY);
	        headers.add("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE);
	        headers.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=UTF-8");
		}
	}
	
	public Map<String, Object> payTest(KakaoReadyRequestVO vo) {
		
		headerSetting();
		
		// 서버로 요청할 Body
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("cid", "TC0ONETIME");
        params.add("partner_order_id", vo.getPartner_order_id());
        params.add("partner_user_id", "gorany");
        params.add("item_name", "음식예약");
        params.add("quantity", "1");
        params.add("total_amount", "300");
        params.add("tax_free_amount", "100");
        params.add("approval_url", "http://localhost:8089/kakao/kakaoPaySuccess");
        params.add("cancel_url", "http://localhost:8089/kakao/kakaoPayCancel");
        params.add("fail_url", "http://localhost:8089/kakao/kakaoPaySuccessFail");
 
         HttpEntity<MultiValueMap<String, String>> body = new HttpEntity<MultiValueMap<String, String>>(params, headers);
         Map result = null;
         
         System.out.println(params);
         
        try {
            
        	result = network.postForObject(new URI(KAKAO_URL + "/v1/payment/ready"), body, Map.class);

        } catch (RestClientException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
		return result;
        
	}
	
	public KakaoPayApprovalVO kakaoPaySuccess(String pg_token, FoodOrderInfoVO order) {
		 
        // 서버로 요청할 Header
        headerSetting();
 
        // 서버로 요청할 Body
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("cid", "TC0ONETIME");
        params.add("cid_secret", "");
        params.add("tid", order.getTid());
        params.add("partner_order_id", order.getF_ocode());
        params.add("partner_user_id", "gorany");
        params.add("pg_token", pg_token);
        params.add("payload", "");
        params.add("total_amount", "300");
        
        System.out.println(params);
        
        HttpEntity<MultiValueMap<String, String>> body = new HttpEntity<MultiValueMap<String, String>>(params, headers);
        
        try {
        	
            KakaoPayApprovalVO vo= network.postForObject(new URI(KAKAO_URL + "/v1/payment/approve"), body, KakaoPayApprovalVO.class);
            log.info("sssss~~~~~~~~~~~~~" + vo);

            return vo;
        
        } catch (RestClientException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return null;
    }

}
