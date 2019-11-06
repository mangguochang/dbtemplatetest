package com.pactera.excep_json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author simonMeng
 * @version 1.0
 * @date 2019/10/28
 **/
/*@ApiModel(value = "jsonResult")
@XmlRootElement(name="jsonResult")*/
public class JsonResult implements Serializable {
  /*  @ApiModelProperty("code")
    @XmlElement(name = "code")*/
    private int code;   //返回码 非0即失败
   /* @XmlElement(name = "msg")*/
    private String msg; //消息提示
   /* @XmlElement(name = "data")*/
    private Object data; //返回的数据
    public JsonResult(){};
    public JsonResult(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static JSONObject success() {
        return success(new JSONObject());
    }
    public static JSONObject success(Object data) {
        return (JSONObject)JSONObject.toJSON(new JsonResult(0, "SuccessFully", data));
    }

    public static JSONObject failed() {
        return failed("Fail!");
    }
    public static JSONObject failed(String msg) {
        return failed(-1, msg);
    }
    public static JSONObject failed(int code, String msg) {
        JsonResult result=new JsonResult(code, msg, new JsonResult());
        /*String jsonStr=;*/
        return (JSONObject)JSONObject.toJSON(result);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Object getData() {
        return data;
    }
}
