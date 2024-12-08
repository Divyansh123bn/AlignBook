package com.scm.controllers;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.scm.entities.Contact;
import com.scm.entities.User;
import com.scm.forms.ContactForm;
import com.scm.forms.ContactSearchForm;
import com.scm.helpers.AppConstants;
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

    // view Contacts Handler
    @RequestMapping()
    public String viewContacts(
        @RequestParam(value = "page",defaultValue = "0")int page,
        @RequestParam(value = "size",defaultValue = AppConstants.PAGE_SIZE+"")int size,
        @RequestParam(value = "sortBy",defaultValue = "name")String sortBy,
        @RequestParam(value = "direction",defaultValue = "asc")String direction,
        Model model,Authentication authentication
        ){
        // Get All Contacts of the user
        String username=Helper.getEmailOfLoggedInUser(authentication);
        User user=userService.getUserByEmail(username);
        Page<Contact> pageContact= contactService.getByUser(user,page,size,sortBy,direction);
        model.addAttribute("pageContact", pageContact);
        model.addAttribute("pageSize", AppConstants.PAGE_SIZE);
		model.addAttribute("contactSearchForm", new ContactSearchForm());
        return "user/contacts";
    }

    // user search handler
    @RequestMapping("/search")
    public String searchHandler(
        @ModelAttribute ContactSearchForm contactSearchForm,
        @RequestParam(value = "page",defaultValue = "0")int page,
        @RequestParam(value = "size",defaultValue = AppConstants.PAGE_SIZE+"")int size,
        @RequestParam(value = "sortBy",defaultValue = "name")String sortBy,
        @RequestParam(value = "direction",defaultValue = "asc")String direction, Model model,
        Authentication authentication
    ){
        logger.info("field {} keyword{}",contactSearchForm.getField(),contactSearchForm.getKeyword());

		Page<Contact> pageContact=null;

		String username=Helper.getEmailOfLoggedInUser(authentication);
        User user=userService.getUserByEmail(username);

        if(contactSearchForm.getField().equalsIgnoreCase("name")){
            pageContact=contactService.searchByName(contactSearchForm.getKeyword(), size, page, sortBy,direction,user);
        }
        else if (contactSearchForm.getField().equalsIgnoreCase("email")){
            pageContact=contactService.searchByEmail(contactSearchForm.getKeyword(), size, page, sortBy,direction,user);
        }else if(contactSearchForm.getField().equalsIgnoreCase("phoneNumber")){
            pageContact=contactService.searchByPhoneNumber(contactSearchForm.getKeyword(), size, page, sortBy,direction,user);
        }else{
			pageContact= contactService.getByUser(user,page,size,sortBy,direction);
		}
		model.addAttribute("contactSearchForm", contactSearchForm);
        model.addAttribute("pageContact",pageContact);
		model.addAttribute("pageSize", AppConstants.PAGE_SIZE);
        return "user/search";
    }

    @RequestMapping("/delete/{contactId}")
    public String deleteContact(@PathVariable String contactId,HttpSession session){
        contactService.delete(contactId);
        logger.info("Contact {} Deleted",contactId);
        session.setAttribute("message",
            Message.builder().content("Contact has Been Deleted Successfully").type(MessageType.green).build());
        return "redirect:/user/contacts"; 
    }
}
