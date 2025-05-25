package com.scm.helpers;

import java.security.Principal;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

public class Helper {

    public static String getEmailOfLoggedInUser(Authentication authentication){

        //checking the way of Authentication:-
        

        if(authentication instanceof OAuth2AuthenticationToken){
            var oauth2AuthenticationToken =(OAuth2AuthenticationToken)authentication;
            var clientId=oauth2AuthenticationToken.getAuthorizedClientRegistrationId();
            var oauthUser=(DefaultOAuth2User)authentication.getPrincipal();
            String username="";
            if(clientId.equalsIgnoreCase("google")){

                username=oauthUser.getAttribute("email").toString();
                System.out.println("logged in using google");

            }else if(clientId.equalsIgnoreCase("github")) {

                username=oauthUser.getAttribute("email")!=null ? oauthUser.getAttribute("email").toString() : oauthUser.getAttribute("login").toString()+"@gmail.com";
                System.out.println("logged in using github");
            }
            return username;
        }
        else{
            //logged in with normal email password
            System.out.println("logged in using email");
            return authentication.getName();
        }
    }

    public static String getLinkforEmailVerification(String emailToken){
        String link="https://alignbook-sa2x.onrender.com/auth/verify-email?token="+emailToken;

        return link;
    }
}
