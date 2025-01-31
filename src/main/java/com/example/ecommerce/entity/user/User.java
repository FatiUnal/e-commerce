package com.example.ecommerce.entity.user;


import jakarta.persistence.*;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "user_type",discriminatorType = DiscriminatorType.STRING)
public abstract class  User implements UserDetails {


    @Id
    @GeneratedValue
    private int id;
    private String firstName;
    private String lastName;
    private String phoneNo;

    @Column(unique = true)
    private String username;

    @Column(unique = true)
    private String password;
    private LocalDateTime createDate = LocalDateTime.now();

    @ElementCollection(targetClass = Roles.class,fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "roles",nullable = false)
    @Enumerated(EnumType.STRING)
    private Set<Roles> authorities;

    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean enabled;

    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean accountNonLocked;


    public User(String firstName, String lastName, String phoneNo, String username, String password, Set<Roles> authorities, boolean enabled, boolean accountNonLocked) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNo = phoneNo;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
        this.enabled = enabled;
        this.accountNonLocked = accountNonLocked;
    }

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public Set<Roles> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Roles> authorities) {
        this.authorities = authorities;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }
}