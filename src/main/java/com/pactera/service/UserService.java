package com.pactera.service;

/**
 * @author simonMeng
 * @version 1.0
 * @date 2019/10/29
 **/
public interface UserService {
    public boolean checkUserAuthority(String token,String serviceId);
}
