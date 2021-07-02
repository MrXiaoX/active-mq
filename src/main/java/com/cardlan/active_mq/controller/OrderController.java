package com.cardlan.active_mq.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    @GetMapping("/order")
    public String gerOrder(){
        return "orderListInfo";
    }
}
