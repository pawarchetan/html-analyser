package de.scout24.analyser.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomePageLanding {
    @GetMapping(value = "/")
    public String landingPage(){
        return "index";
    }
}
