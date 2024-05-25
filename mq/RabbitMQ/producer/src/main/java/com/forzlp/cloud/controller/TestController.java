package com.forzlp.cloud.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author 70ash
 * Date 2024/5/21 下午8:32
 * Description:
 */
@RestController
public class TestController {
    @GetMapping("/test")
    public String test() {
        return "sucess";
    }
}
