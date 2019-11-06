package com.pactera.service.impl;

import com.pactera.dao.UserRepssitry;
import com.pactera.jwt.JWTUtil;
import com.pactera.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author simonMeng
 * @version 1.0
 * @date 2019/10/29
 **/
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    public JWTUtil jwtUtil;
    @Autowired
    public UserRepssitry userRepssitry;
    @Override
    public boolean checkUserAuthority(String token,String serviceId) {
        //1.验证Token是否有效？
        String userName=jwtUtil.parseTaiTicket(token);
        int count=userRepssitry.findUserByName(userName);
        if(count>0){
            //2.验证Token否是有权使用？
            count=userRepssitry.checkCurrentUserAuthourity(userName,serviceId);
            if(count>0){
                return true;
            }
        }
        return false;
    }
}
