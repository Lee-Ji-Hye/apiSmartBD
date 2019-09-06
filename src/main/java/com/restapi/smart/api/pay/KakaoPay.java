package com.restapi.smart.api.pay;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

import com.restapi.smart.api.vo.FoodOrderInfoVO;
import com.restapi.smart.api.vo.KakaoPayApprovalVO;
import com.restapi.smart.api.vo.KakaoReadyRequestVO;
import com.restapi.smart.api.vo.KakaoSuccessRequestVO;
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
	private final String CID = "TC0ONETIME";
	
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
			headers.add("Authorization", "KakaoAK " + ADMIN_KEY); //우리가 추가로
	        headers.add("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE);
	        headers.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=UTF-8");
		}
	}
	
	public Map<String, Object> payTest(KakaoReadyRequestVO vo) {
		
		headerSetting();
		
		// 서버로 요청할 Body
    MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
    params.add("cid", CID);
    params.add("partner_order_id", vo.getPartner_order_id());
    params.add("partner_user_id", vo.getPartner_user_id());
    params.add("item_name", vo.getItem_name());
    params.add("quantity", String.valueOf(vo.getQuantity()));
    params.add("total_amount", String.valueOf(vo.getTotal_amount()));
    params.add("tax_free_amount", String.valueOf(vo.getTax_free_amount()));
    params.add("approval_url", vo.getApproval_url());
    params.add("cancel_url", vo.getCancel_url());
    params.add("fail_url", vo.getFail_url());


     HttpEntity<MultiValueMap<String, String>> body = new HttpEntity<MultiValueMap<String, String>>(params, headers);
     Map result = null;

    System.out.println("payTest : " + params);


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
	
	public KakaoPayApprovalVO kakaoPaySuccess(String pg_token, KakaoSuccessRequestVO order) {
		 
        // 서버로 요청할 Header
        headerSetting();

        // 서버로 요청할 Body
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("cid", CID);
        params.add("cid_secret", order.getCid_secret());
        params.add("tid", order.getTid());
        params.add("partner_order_id", order.getPartner_order_id());
        params.add("partner_user_id", order.getPartner_user_id());
        params.add("pg_token", order.getPg_token());
        params.add("payload", order.getPayload());
        params.add("total_amount", String.valueOf(order.getTotal_amount()));

        
        System.out.println("kakaoPaySuccess : " + params);
        

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