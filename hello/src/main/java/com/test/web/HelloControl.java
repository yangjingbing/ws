package com.test.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author yjb
 * @date 2022/1/14 14:38
 */
@Controller
public class HelloControl {
//    @GetMapping("/api")
    @RequestMapping("/pai")
    @ResponseBody
    public String Home(){
        return  "hello";
    }
}
