package com.scm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.scm.entities.User;
import com.scm.repositories.UserRepo;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepo userRepo;

    @GetMapping("verify-email")
    public String verifyEmail(
        @RequestParam("token")String token
    ){
        User user=userRepo.findByEmailToken(token).orElse(null);

       if(user!=null){
        if(user.getEmailToken().equals(token)){
            user.setEmailVerified(true);
            user.setEnabled(true);
            userRepo.save(user);
            return "success_page";
        }
        return "error_page";
       }
        return "error_page";
    }
}
