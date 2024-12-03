package com.scm.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;




@Controller
@RequestMapping("/user")
public class UserController {    

    //user dashboard page
    @RequestMapping(value = "/dashboard",method = RequestMethod.GET)
    public String userDashboard() {
        return "user/dashboard";
    }

    //profile page mapping
    @RequestMapping(value = "/profile",method = RequestMethod.GET)
    public String userProfile() {
        return "user/profile";
    }
    

}
