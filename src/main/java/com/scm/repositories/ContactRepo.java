package com.scm.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.scm.entities.Contact;
import com.scm.entities.User;

@Repository
public interface ContactRepo extends JpaRepository<Contact,String>{

    
    List<Contact> findByUser(User user);

    // find contact by user Id:
    // Cuntom query method
    @Query("SELECT c from Contact c where c.user.id=:userId")
    List<Contact> findByUserId(@Param("userId") String userId);
}
