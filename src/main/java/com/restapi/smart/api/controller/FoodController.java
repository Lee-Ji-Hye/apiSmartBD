package com.restapi.smart.api.controller;

import com.restapi.smart.api.service.FoodService;
import com.restapi.smart.api.vo.FoodMenuVO;
import com.restapi.smart.api.vo.FoodStoreVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

/**
 * @author jihye
 *
 * 어노테이션 설명
 *  -@RestController : @Controller + @ResponseBody
 *  -@RequestMapping : 요청에 대해 어떤 Controller, 어떤 메소드가 처리할지를 맵핑하기 위한 어노테이션
 */
@Slf4j
@RestController
@RequestMapping("api")
public class FoodController {
    @Autowired
    FoodService f_service;

    @GetMapping("foods")
    public String foodTesr() {

        return "정상동작chk";
    }

    // 앱 > 음식 상세보기 (메뉴, 업체정보 return)
    @GetMapping(value="food/getFoodMenuList")
    public HashMap<String, Object> getFoodDetailInfo(HttpServletRequest req) {

        List<FoodMenuVO> menuRes = f_service.getMenuList(req);//메뉴정보
        HashMap<String, Object> map = new HashMap<String, Object>();

        if(menuRes != null) {
            map.put("menus", menuRes);
            map.put("responseCode", 100);
            map.put("responseMsg", "성공");
        } else {
            map.put("menus", null);
            map.put("responseCode", 999);
            map.put("responseMsg", "데이터 없음");
        }

        return map;
    }

    @GetMapping(value="food/getStoreList")
    public HashMap<String, Object> getStoreList(HttpServletRequest req) {

        List<FoodStoreVO> result = f_service.getFoodStoreList(req);

        HashMap<String, Object> map = new HashMap<String, Object>();
        if(result.size() > 0) {
            map.put("stores", result);
            map.put("responseCode", 100);
            map.put("responseMsg", "성공");
        } else {
            map.put("stores", null);
            map.put("responseCode", 999);
            map.put("responseMsg", "데이터 없음");
        }
        return map;
    }

}
