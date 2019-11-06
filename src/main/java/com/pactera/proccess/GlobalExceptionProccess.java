package com.pactera.proccess;

import com.pactera.excep_json.JsonResult;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Camel 全局异常拦截的操作
 * @author simonMeng
 * @version 1.0
 * @date 2019/10/28
 **/
@Component("globalExceptionProccess")
public class GlobalExceptionProccess implements Processor {
    private Logger logger= LoggerFactory.getLogger(GlobalExceptionProccess.class);
    @Override
    public void process(Exchange exchange) throws Exception {
        logger.info("[异常发现][TO]exchangeId=" + exchange.getExchangeId() + "|fromRouteId=" +exchange.getFromRouteId());
        /*String body = exchange.getIn().getBody(String.class);*/
        Exception e = exchange.getProperty(Exchange.EXCEPTION_CAUGHT,
                Exception.class);
        exchange.getIn().setBody(JsonResult.failed(e.getMessage()));
    }
}
