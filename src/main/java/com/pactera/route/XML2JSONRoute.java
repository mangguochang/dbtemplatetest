package com.pactera.route;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.xmljson.XmlJsonDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author simonMeng
 * @version 1.0
 * @date 2019/10/16
 **/
@Component
public class XML2JSONRoute extends RouteBuilder {
    @Autowired
    private XmlJsonDataFormat xmlJsonFormat;
    @Override
    public void configure() throws Exception {
        // xml to json数据格式的转换
        from("direct:marshalEmployeexml2json")
                .marshal().xmljson()
                .to("bean:com.pactera.proccess.MyDataformat?method=process")
                .to("log:?level=INFO&showBody=true");

        // json to xml数据格式转换
        from("direct:unMarshalEmployeejson2xml")
                .unmarshal(xmlJsonFormat)
                .to("log:?level=INFO&showBody=true");
    }
}
