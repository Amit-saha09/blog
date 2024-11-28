package com.example.blog.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;


@Data
@Entity
@Table(name = "blog_user")
@ToString
//public class User extends AuditModel<Long> implements UserDetails {
/*****author-->Simi******/
//updated User class extension to Observer class
public class User extends AuditModel<Long> implements UserDetails, Observer {
    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    @Email
    private String email;

    @NotNull
    private String phone;

    @NotNull
    private String password;

    @NotNull
    private Boolean isActivated;

    @Nullable
    private String profilePictureFileIdentifier;
    @Nullable
    private String signatureFileIdentifier;

    @Override
    @Transient
    public Collection<? extends GrantedAuthority> getAuthorities() {
      /*   If you have roles implemented, uncomment and modify accordingly
         if (roles != null) {
             return roles.stream()
                     .map(role -> new SimpleGrantedAuthority(role.getName()))
                     .collect(Collectors.toSet());
         }*/
        return Collections.emptySet(); // Return an empty set if no roles are defined
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    // added code ---- author Simi
    private String name;

    /*public User(String name) {
        this.name = name;
    }*/


    //@Override /*****author-->Simi*****/
    /*public void update(String message) {
        System.out.println("Notification for user: " + firstName + " " + lastName + " - " + message);
    }*/

    //@Override /*****author-->Simi*****/
    public void update(String message) {
        System.out.println("Notification for user: " + name + " - " + message);
    }


}
