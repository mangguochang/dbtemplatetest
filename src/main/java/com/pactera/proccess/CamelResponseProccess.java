package com.pactera.proccess;

import com.pactera.excep_json.JsonResult;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 请求参数进来需要转换的操作。
 * @author simonMeng
 * @version 1.0
 * @date 2019/10/18
 **/
@Component("camelResponseProccess")
public class CamelResponseProccess implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        Object object=exchange.getIn().getBody();
        Map<String,Object> headers=exchange.getIn().getHeaders();
        JSONArray jsonArray=new JSONArray();
        if(null!=object&&object instanceof List){
            List list=(List)object;
            if(null!=list&&!list.isEmpty()){
                for (int i=0;i<list.size();i++){
                    Object obj=list.get(i);
                    Map map=(Map)obj;
                    JSONObject jsonObject=JSONObject.fromObject(map);
                    jsonArray.add(jsonObject);
                }
                exchange.getIn().setBody(JsonResult.success(jsonArray));
            }else {
                exchange.getIn().setBody(JsonResult.success(jsonArray));
            }
        }else{
            exchange.getIn().setBody(JsonResult.success(jsonArray));
        }
        long endTime=new Date().getTime();
        long startTime=Long.valueOf(null!=headers.get("startTime")?headers.get("startTime").toString():"0L");
        headers.put("time_consuming",(endTime-startTime));
        exchange.getIn().setHeaders(headers);

    }
}
