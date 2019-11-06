package com.pactera.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

/**
 * @author simonMeng
 * @version 1.0
 * @date 2019/10/29
 **/
@Mapper
@Component
public interface UserRepssitry {

    @Select("select count(1) from sys_user where login_name=#{userName}")
    public int findUserByName(@Param("userName")String userName);

    @Select("select count(1) from sys_user where login_name=#{userName}")
    public int checkCurrentUserAuthourity(@Param("userName")String userName,@Param("serviceId")String serviceId);
}
