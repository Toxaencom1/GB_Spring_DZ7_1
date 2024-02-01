package com.taxah.springdz7.controllers;


import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class ResourceWebController {

    @GetMapping("/")
    public String homePage(Authentication a, Model model){
        if(a != null){
            model.addAttribute("username", a.getName());
            return "index";
        } else {
            model.addAttribute("username", "Stranger");
            return "index";
        }
    }

    @GetMapping("/public-data")
    public String getPublicResource() {
        return "public";
    }

    @GetMapping("/private-data")
    public String getPrivateResource() {
        return "private";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

}
