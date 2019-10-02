package com.restapi.smart.api.vo;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class RoomContractDetailVO {
    private String b_area1;        //서울시
    private String b_area2;        //금천구
    private String b_address;      //주소
    private String b_year;         //준공년도
    private String b_landarea;     //대지면적
    private String b_buildarea;    //건축면적
    private String b_buildscale;   //건축규모

    private int r_blockcode;       //매물 블록체인 코드
    private String r_type;         //거래 타입
    private int r_price;           //매물 가격
    private int r_deposit;         //보증금
    private int r_premium;         //권리금(상가)

    private String rt_code;        //계약 코드
    private String rt_hash;        //계약 해쉬코드
    private String r_code;         //매물 코드
    private String rt_state;       //계약 상태
    private String userid;         //사용자 아이디
    private String rt_mobile;      //임차인 휴대폰
    private String rt_email;       //임차인 이메일
    private int rt_date1;          //계약 날짜(insert)
    private String rt_date;        //계약 날짜(select)
    private String rt_date2;       //계약 만기일
    private String rt_deposit;     //계약 보증금
    private String staff_id;       //관리자 아이디
    private int r_floor;           //해당층수
    private Timestamp regidate;    //등록일
    private String r_kind;         //매물 종류
    private String rt_price;       //계약 월세
    private Timestamp rt_regidate; //계약 날짜(sysdate)

    //임대 관리자 정보
    private String name;           //이름
    private String email;          //이메일
    private String hp;             //핸드폰

    private String comp_seq;
    private String b_code;
}