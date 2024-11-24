package com.scm.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {

    @RequestMapping("/home")
    public String home(Model model){
        model.addAttribute("name", "Divyansh Singh Solanki");
        model.addAttribute("google", "https://google.com");
        return "home";
    }

    //about 
    @RequestMapping("/about")
    public String pageAbout(){
        System.out.println("About page Opened");
        return "about";
    }

    //services
    @RequestMapping("/services")
    public String pageService(){
        System.out.println("Service page Opened");
        return "services";
    }
}