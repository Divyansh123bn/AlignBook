package com.scm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.scm.entities.Contact;
import com.scm.entities.User;
import com.scm.forms.ContactForm;
import com.scm.helpers.Helper;
import com.scm.service.ContactService;
import com.scm.service.UserService;

@Controller
@RequestMapping("/user/contacts")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @Autowired
    private UserService userService;

    //add contact page: handler
    @RequestMapping("/add")
    public String addContacts(Model model){
        ContactForm contactForm=new ContactForm();
        model.addAttribute("contactForm", contactForm);
        return "user/add_contact";
    }

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public String saveContact(@ModelAttribute ContactForm contactForm,Authentication authentication){
        // process the form
        String username=Helper.getEmailOfLoggedInUser(authentication);
        // form -->contact
        User user=userService.getUserByEmail(username);
        //process the contact picture
        Contact contact=new Contact();
        contact.setName(contactForm.getName());
        contact.setEmail(contactForm.getEmail());
        contact.setAddress(contactForm.getAddress());
        contact.setDescription(contactForm.getDescription());
        contact.setFavourite(contactForm.isFavourite());
        contact.setPhoneNumber(contactForm.getPhoneNumber());
        contact.setWebsiteLink(contactForm.getWebsiteLink());
        contact.setLinkedInLink(contactForm.getLinkedInLink());
        contact.setUser(user);
        //save Contact to database
        contactService.save(contact);
        System.out.println(contactForm);
        return "redirect:/user/contacts/add";
    }
}
