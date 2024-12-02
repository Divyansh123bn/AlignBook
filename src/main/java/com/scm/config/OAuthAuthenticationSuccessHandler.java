package com.scm.config;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.scm.entities.Providers;
import com.scm.entities.User;
import com.scm.helpers.AppConstants;
import com.scm.repositories.UserRepo;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class OAuthAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    Logger logger=LoggerFactory.getLogger(OAuthAuthenticationSuccessHandler.class);

    @Autowired
    private UserRepo userRepo;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

        logger.info("OAuthenticationSuccessHandler");

        DefaultOAuth2User user=(DefaultOAuth2User)authentication.getPrincipal(); 
        // logger.info(user.getName());
        // user.getAttributes().forEach((key,value)->{
        //     logger.info("{}=>{}",key,value);
        // });

        //Save Data in DataBase:-
        String name=user.getAttribute("name").toString();
        String email=user.getAttribute("email").toString();
        String picture=user.getAttribute("picture").toString();

        User user1=new User();
        user1.setName(name);
        user1.setEmail(email);
        user1.setProfilePic(picture);
        user1.setPassword("password");
        user1.setUserId(java.util.UUID.randomUUID().toString());
        user1.setProvider(Providers.GOOGLE);
        user1.setEnabled(true);
        user1.setEmailVerified(true);
        user1.setProviderUserId(user.getName());
        user1.setRoleList(List.of(AppConstants.ROLE_USER));
        user1.setAbout("Hii! There I am using AlignBook.");
        
        User user2= userRepo.findByEmail(email).orElse(null);
        if(user2==null){
            userRepo.save(user1);
            logger.info("User Saved Successfully");
        }

        new DefaultRedirectStrategy().sendRedirect(request, response, "/user/profile");
    }

}
