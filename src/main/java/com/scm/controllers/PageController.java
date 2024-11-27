package com.scm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.scm.entities.User;
import com.scm.forms.UserForm;
import com.scm.service.UserService;

@Controller
public class PageController {

    @Autowired
    UserService userService;

    // Home maping
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

    //contact
    @RequestMapping("/contact")
    public String contact(){
        return "contact";
    }

    //register
    @RequestMapping("/register")
    public String register(Model model){
        UserForm userForm=new UserForm();
        //can also add default values
        //userForm.setName("Divyansh");
        model.addAttribute("user", userForm);
        return "register";
    }

    //login
    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    //Register Form handling
    @PostMapping("/do-register")
    public String processRegister(@ModelAttribute UserForm userForm){
        System.out.println("Registered");
        //fetch data
        System.out.println(userForm);
        //validate data
        // save to database
        // Created user <- UserForm
        User user=User.builder()
        .name(userForm.getName())
        .email(userForm.getEmail())
        .password(userForm.getPassword())
        .about(userForm.getAbout())
        .phoneNumber(userForm.getPhoneNumber())
        .profilePic(
            "src\\main\\resources\\static\\images\\default.webp"
        )
        .build();
        User savedUser=userService.saveUser(user);
        System.out.println(savedUser);
        // message="sucessfull"
        // redirect to login 
        return "redirect:register";
    }

}
