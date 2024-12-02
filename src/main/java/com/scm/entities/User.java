package com.scm.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User implements UserDetails{
    @Id
    private String userId;
    @Column(name = "user_name",nullable = false)
    private String name;
    @Column(unique = true,nullable = false)
    private String email;
    @Getter(value = AccessLevel.NONE)
    private String password;
    @Column(columnDefinition = "Text")
    private String about;
    @Column(columnDefinition = "Text")
    private String profilePic;
    private String phoneNumber;
    //information
    @Getter(value = AccessLevel.NONE)
    private boolean enabled=true;
    private boolean emailVerified=false;
    private boolean phoneVerified=false;
    //SELF, GOOGLE, FACEBOOK, GITHUB, ETC
    @Enumerated(value = EnumType.STRING)
    private Providers provider=Providers.SELF;
    private String providerUserId;
    //contacts
    //one user can have multiple contacts
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.LAZY,orphanRemoval = true)
    private List<Contact> contacts=new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roleList=new ArrayList<>();


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //list of roles[USER,ADMIN]
        //Collection of simpleGrantedAuthority[roles{USER,ADMIN}]
        Collection<SimpleGrantedAuthority> roles= roleList.stream().map(role->new SimpleGrantedAuthority(role)).collect(Collectors.toList());
        return roles;
    }

    //for this project email is username

    @Override
    public String getUsername() {
       return this.email;
    }

    @Override
    public boolean isEnabled(){
        return this.enabled;
    }

    @Override
    public String getPassword(){
        return this.password;
    }

    
}
