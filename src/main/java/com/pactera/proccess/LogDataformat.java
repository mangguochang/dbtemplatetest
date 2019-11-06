package com.pactera.proccess;

import org.apache.camel.Body;
import org.apache.camel.Exchange;
import org.apache.camel.Header;
import org.apache.camel.Headers;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;


/**
 * 日志数据格式转换的类
 * @author simonMeng
 * @version 1.0
 * @date 2019/10/23
 **/
@Component("logDataformat")
public class LogDataformat {
    @Value("${spring.application.name}")
    public String applicationName;
    public void process(Exchange exchange,@Body String body) {
        Map<String,Object> objectMap=exchange.getIn().getHeaders();
        exchange.getIn().setBody(body);
        objectMap.put("appId",applicationName);
        exchange.getIn().setHeaders(objectMap);
    }
}
