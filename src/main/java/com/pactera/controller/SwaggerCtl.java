package com.pactera.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author simonMeng
 * @version 1.0
 * @date 2019/10/17
 **/
@Controller
public class SwaggerCtl {
    @RequestMapping("/")
    public String index(){
        return "redirect:/swagger-ui.html";
    }
    @RequestMapping("/swagger-ui.html")
    public String swaggerIndex(){
        return "index";
    }
}
