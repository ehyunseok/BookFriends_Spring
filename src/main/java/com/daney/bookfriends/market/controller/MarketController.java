package com.daney.bookfriends.market.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/market")
public class MarketController {

// 수정해야함
    @GetMapping
    public String marketMain() {
        return "market/market";
    }

}
