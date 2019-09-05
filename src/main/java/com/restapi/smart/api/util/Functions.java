package com.restapi.smart.api.util;

import java.util.HashMap;
import java.util.Map;

import com.restapi.smart.api.persistance.CodeDAO;
import org.springframework.stereotype.Service;

@Service
public class Functions {
	private static Functions instance = new Functions();
	
	public static Functions getInstance() {
		if(instance == null) {
			instance = new Functions();
		}
		return instance;
	}
	//생성자 잠금
	private Functions() {
		
	}
	
	/**
	 * @author jihye
	 * 유니크한 코드 생성  EX)FD000049
	 * 매물코드, 쿠폰코드 이거로 다 가능
	 * @param codeColumn : 코드 컬럼명
	 * @param tblName    : 대상 테이블
	 * @param c_dao      : sql접근할 dao객체
	 * @return int
	 */
	public String mkUniquecode(String codeColumn, String tblName, CodeDAO c_dao) {
		String strCode = "";
		int length = 6;//기본 수 6
		
		if(codeColumn.equals("f_ocode")) {
			strCode = "FD";//FD
			length = 5;    //000001
			               //FD000001
		} else if(codeColumn.equals("p_ocode")) {
			strCode = "PK";//PK000001
			length = 6;    //000001
		}else if(codeColumn.equals("parking_code")) {
			strCode = "PT";//PT000001
			length = 6;    //000001
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("column", codeColumn); //f_ocode, r_code, p_code,,.
		map.put("tblName", tblName);

		int lastIdx = c_dao.getLastIdx(map);
		
		int diffLength = (int)(Math.log10(lastIdx)+1); //숫자의 길이를 구함 -cf) 184 => 3
		
		if(diffLength < length) {
			int diffcnt = length - diffLength;
			for(int j = 0; j < diffcnt; j++) {
				strCode += "0"; //코드 자리 맞추기
			}
		}
		
		//System.out.println("NEW 코드 : " + strCode + lastIdx);
		return strCode + lastIdx;
	}

}
