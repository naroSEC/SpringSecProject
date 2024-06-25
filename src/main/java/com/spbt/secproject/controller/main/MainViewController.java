package com.spbt.secproject.controller.main;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainViewController {

    @GetMapping(value = "/main/index")
    public String index() {

        return "/main/index";
    }

}
