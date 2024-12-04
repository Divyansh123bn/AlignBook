package com.scm.controllers;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.scm.entities.Contact;
import com.scm.entities.User;
import com.scm.forms.ContactForm;
import com.scm.helpers.Helper;
import com.scm.helpers.Message;
import com.scm.helpers.MessageType;
import com.scm.service.ContactService;
import com.scm.service.ImageService;
import com.scm.service.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/user/contacts")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @Autowired
    private UserService userService;

    @Autowired
    private ImageService imageService;

    private Logger logger=LoggerFactory.getLogger(ContactController.class);

    //add contact page: handler
    @RequestMapping("/add")
    public String addContacts(Model model){
        ContactForm contactForm=new ContactForm();
        model.addAttribute("contactForm", contactForm);
        return "user/add_contact";
    }

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public String saveContact(@Valid @ModelAttribute ContactForm contactForm,BindingResult result,Authentication authentication,HttpSession session){
        // process the form
        String username=Helper.getEmailOfLoggedInUser(authentication);
        // validte form
        if(result.hasErrors()){
            session.setAttribute("message",Message
            .builder().
            content("Enter Details Correctly")
            .type(MessageType.red).
            build());
            return "user/add_contact";
        }
        // form -->contact
        User user=userService.getUserByEmail(username);
        //process the contact picture
       
        //uploading image code
        String filename=UUID.randomUUID().toString();
        String fileURL=imageService.uploadImage(contactForm.getContactImage(),filename);
        // 
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
        contact.setPicture(fileURL);
        contact.setCloudinaryImagePublicId(filename);
        //save Contact to database
        contactService.save(contact);
        System.out.println(contactForm);
        session.setAttribute("message",Message.builder().content("Contact has been Added SuccessFully").type(MessageType.green).build());
        return "redirect:/user/contacts/add";
    }
}
