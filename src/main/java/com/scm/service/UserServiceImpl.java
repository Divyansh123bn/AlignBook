package com.scm.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.scm.entities.User;
import com.scm.helpers.AppConstants;
import com.scm.helpers.Helper;
import com.scm.helpers.ResourceNotFoundException;
import com.scm.repositories.UserRepo;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    private Logger logger=LoggerFactory.getLogger(this.getClass());

    @Override
    public User saveUser(User user) {
        //genrate user id:
        String userId=UUID.randomUUID().toString();
        user.setUserId(userId);
        //default things to be passed here ex. image
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoleList(List.of(AppConstants.ROLE_USER));
    
        String emailToken=UUID.randomUUID().toString();

        user.setEmailToken(emailToken);
        User savedUser= userRepo.save(user);

        String emailLink=Helper.getLinkforEmailVerification(emailToken);
        emailService.sendEmail(savedUser.getEmail(),"AlignBook: Verify your Account to continue services: ", emailLink);
        return savedUser;
    }

    @Override
    public Optional<User> getUserById(String id) {
        return userRepo.findById(id);
    }

    @Override
    public Optional<User> updateUser(User user) {
       User user2= userRepo.findById(user.getUserId()).orElseThrow(()-> new ResourceNotFoundException("User not present"));
        // update the value of user 2 from user 
       user2.setName(user.getName());
       user2.setEmail(user.getEmail());
       user2.setAbout(user.getAbout());
       user2.setPassword(user.getPassword());
       user2.setPhoneNumber(user.getPhoneNumber());
       user2.setProfilePic(user.getProfilePic());
       user2.setEnabled(user.isEnabled());
       user2.setEmailVerified(user.isEmailVerified());
       user2.setPhoneVerified(user.isPhoneVerified());
       user2.setProvider(user.getProvider());
       user2.setProviderUserId(user.getProviderUserId());
        // save data in database
        User save=userRepo.save(user2);
        // 
        return Optional.ofNullable(save);
    }

    @Override
    public void deleteUser(String id) {
        User user=userRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("User not present"));
        userRepo.delete(user);
    }

    @Override
    public boolean isUserExist(String userId) {
       User user=userRepo.findById(userId).orElse(null);
       return user!=null ? true : false ;
    }

    @Override
    public boolean isUserExistByEmail(String email) {
        User user=userRepo.findByEmail(email).orElse(null);
        return user!=null ? true : false;
    }

    @Override
    public List<User> getAllUsers() {
       return userRepo.findAll();
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepo.findByEmail(email).orElse(null);
    }

}
