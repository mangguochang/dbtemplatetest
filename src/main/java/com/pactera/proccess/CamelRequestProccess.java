package com.pactera.proccess;

import com.pactera.common.TemplateUitls;
import com.pactera.jwt.JWTUtil;
import com.pactera.service.UserService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 请求参数进来需要转换的操作。
 * @author simonMeng
 * @version 1.0
 * @date 2019/10/18
 **/
@Component("camelRequestProccess")
public class CamelRequestProccess implements Processor {
    private Logger logger= LoggerFactory.getLogger(CamelRequestProccess.class);

    @Value("${service.action}")
    public String serviceAction;
    @Value("${spring.application.name}")
    public String appId;
    @Autowired
    public UserService userService;

    @Override
    public void process(Exchange exchange) throws Exception {
        long startTime=new Date().getTime();
        Object object=exchange.getIn().getBody();
        Map<String,Object> headers=exchange.getIn().getHeaders();
        String token=null!=headers.get(TemplateUitls.Token_Key)?headers.get(TemplateUitls.Token_Key).toString():
                null;
        int tokenStatus=0;
        if(null!=token&&token.length()>0){
            boolean isOk=userService.checkUserAuthority(token,appId);
            if(isOk){
                genentMethod(object,exchange);
                tokenStatus=TemplateUitls.Token_Success_Status_Ok;
            }else{
                logger.info("App-{}:Token is no authority or no valid!",appId);
                tokenStatus=TemplateUitls.Token_Fail_Status_Power_OR_Invalid;//无权访问或者
            }
        }else{
            logger.info("App-{}:Token is null!",appId);
            tokenStatus=TemplateUitls.Token_Fail_Status_Null;//无效Token
        }
        headers.put("tokenStatus",tokenStatus);
        headers.put("startTime",startTime);
        headers.put("serviceAction",serviceAction);
        exchange.getIn().setHeaders(headers);
    }
    private void genentMethod(Object object,Exchange exchange){
        if(null!=object&&object instanceof List){
            JSONArray jsonArray=JSONArray.fromObject(object.toString());
            exchange.getIn().setBody(jsonArray);
        }else if(null!=object&&object instanceof Map){
            JSONObject jsonObject=JSONObject.fromObject((Map)object);
            Iterator iterator= jsonObject.keySet().iterator();
            while (iterator.hasNext()){
                Object obj=iterator.next();
                logger.info("obj:"+obj.toString()+",value:"+jsonObject.get(obj.toString()));
            }
            exchange.getIn().setBody(jsonObject);
        }
    }
}
