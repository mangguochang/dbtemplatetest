package com.pactera.proccess;

import com.alibaba.fastjson.JSONObject;
import com.pactera.excep_json.JsonResult;
import org.apache.camel.Exchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 *
 * @author simonMeng
 * @version 1.0
 * @date 2019/10/29
 **/
@Component("tokenValidProcess")
public class TokenValidProcess {
    private Logger logger= LoggerFactory.getLogger(TokenValidProcess.class);

    public void process(Exchange exchange) throws Exception {
        logger.info("[Token异常][TO]exchangeId=" + exchange.getExchangeId() + "|fromRouteId=" +exchange.getFromRouteId());
        Map<String,Object> headers=exchange.getIn().getHeaders();
        int tokenStatus=null!=headers.get("tokenStatus")?Integer.valueOf(headers.get("tokenStatus").toString()):-1;
        JSONObject jsonResult=null;
        switch (tokenStatus){
            case -1:
                jsonResult=JsonResult.failed("Token值不能为空，请检查！");
                logger.error("Token值不能为空，请检查！");
                break;
            case -2:
                jsonResult=JsonResult.failed("Token无效或者当前用户没有权限进行操作，请检查！");
                logger.error("Token无效或者当前用户没有权限进行操作，请检查！");
                break;
            default:
                jsonResult=JsonResult.failed("Token值不能为空，请检查！");
                logger.error("Token值不能为空，请检查！");
                break;
        }
        exchange.getIn().setBody(jsonResult);
    }
}
