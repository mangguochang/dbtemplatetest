package com.pactera.controller;

import com.pactera.jwt.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 线上生成Token的方法
 * @author simonMeng
 * @version 1.0
 * @date 2019/10/29
 **/
@RestController
public class UserTokenCtl {
    @Autowired
    public JWTUtil jwtUtil;

    /***
     * 生成jwt Token的方法
     * @param username
     * @return
     */
    @PostMapping("/jwt/{username}")
    public Map<String,Object> getUserToken(@PathVariable("username")String username){
        Map<String,Object> objectMap=new HashMap<>();
        String token=jwtUtil.getTaiTicket(username);
        objectMap.put("userName",username);
        objectMap.put("token",token);
        return objectMap;
    }
    /***
     * 生成jwt Token的方法
     * @param token
     * @return
     */
    @PostMapping("/jwt/getUserInfo")
    public Map<String,Object> getUserInfo(@RequestHeader("token") String token){
        Map<String,Object> objectMap=new HashMap<>();
        String username=jwtUtil.parseTaiTicket(token);
        objectMap.put("userName",username);
        objectMap.put("token",token);
        return objectMap;
    }
}
