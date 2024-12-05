package com.scm.service;

import java.util.List;

import com.scm.entities.Contact;
import com.scm.entities.User;

public interface ContactService {

    // Save Contact
    Contact save(Contact contact);
    
    // update Contact
    Contact update(Contact contact);

    // GetAll Contacts
    List<Contact> getAll();

    // get Contact by Id
    Contact getById(String id);

    // delete Contact
    void delete(String id);

    // Search Conats
    List<Contact> search(String name,String email,String phoneNumber);

    // get contact ByUserId
    List<Contact> getByUserId(String userId);

    // get by user
    List<Contact> getByUser(User user);
}
