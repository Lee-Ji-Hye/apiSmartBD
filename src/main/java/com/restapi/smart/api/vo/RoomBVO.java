package com.restapi.smart.api.vo;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.sql.Timestamp;

import lombok.Data;

@Data
public class RoomBVO {

	private String b_area1;        //서울시
	private String b_area2;        //금천구
	private String b_address;      //주소
	private String b_year;         //준공년도
	private String b_landarea;     //대지면적
	private String b_buildarea;    //건축면적
	private String b_buildscale;   //건축규모

    private String r_code;         //매물 코드
    private String b_code;         //건물 코드
    private String r_img;          //매물 사진
    private String r_name;         //매물명
    private String r_type;         //거래 타입
    private int r_price;           //매물 가격
    private int r_deposit;         //보증금
    private int r_premium;         //권리금(상가)
    private int r_ofer_fee;        //관리비
    private int r_floor;           //해당층수
    private int r_area;            //면적
    private String r_indi_space;   //독립공간(회의실,탕비실 등) 유무
    private String r_able_date;    //입주가능일
    private String r_toilet;       //화장실
    private String r_desc;         //상세설명
    private String r_pmemo;        //비공개메모 (선택)
    private Timestamp regidate;    //등록일
    private String r_delete;       //공개 여부
    private String userid;         //관리자 아이디
    private String r_kind;         //매물 종류
    
	//건물 주소 |*DB없음 조인해서 가져옴*|
	private double b_lat;          //위도
	private double b_lon;          //경도

	private int r_cnt;             //매물 수량
	
	//임대 관리자 정보
	private String name;           //이름
	private String email;          //이메일
	private String hp;             //핸드폰

	private String comp_seq;       //업체 코드

	String ip = null;
	
	public RoomBVO() {
		try {
			ip = Inet4Address.getLocalHost().getHostAddress(); //IPv4 아이피 출력.
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}    
	}
 
	 /***************************************************
	  * GETTER && SETTER
	  * lombok사용시 getter, setter 별도로 정의 필요 없음
	  * 다만, 재정의 하여 사용할 부분이 필요하다면 오버라이딩하여 사용할 것
	  ***************************************************/
	
	public void setR_img(String r_img) {
		//this.f_mainimg = "http://172.30.1.46:8089/smart/resources/images/food/"+f_mainimg;
		this.r_img = "http://"+ip+":80/smart/resources/images/room/"+r_img;
	}
}
