package com.pactera.proccess;

import org.apache.camel.Body;


/**
 * 数据格式转换的类
 * @author simonMeng
 * @version 1.0
 * @date 2019/10/23
 **/
public class MyDataformat {
    public String process(@Body String body) {
        return (null!=body?body:null);
    }
}
