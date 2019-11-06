package com.pactera.common;

/**
 * @author simonMeng
 * @version 1.0
 * @date 2019/10/29
 **/
public class TemplateUitls {

    //Token Name
    public static final String Token_Key="token";
    //Token有效且有权
    public static final int Token_Success_Status_Ok=0;
    //Token无效
    public static final int Token_Fail_Status_Null=-1;
    //Token无效或者没有权限
    public static final int Token_Fail_Status_Power_OR_Invalid=-2;
}
