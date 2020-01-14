package ru.shara.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "address")
    private String address;

    @ElementCollection(targetClass = UserRoleEnum.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<UserRoleEnum> authorities = new HashSet<>();

    public User() {
    }

    public User(String name, String password, String email, String address) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.address = address;
    }

    public User(String name, String password, String email, String address, Set<UserRoleEnum> authorities) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.address = address;
        this.authorities = authorities;
    }

    public User(Long id, String name, String password, String email, String address, Set<UserRoleEnum> authorities) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
        this.address = address;
        this.authorities = authorities;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return name;
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

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    public Set<UserRoleEnum> getRoles() {
        return authorities;
    }

    public void setAuthorities(Set<UserRoleEnum> authorities) {
        this.authorities = authorities;
    }

    public void addRole(String role) {
        if (role.equals("USER")) {
            authorities.add(UserRoleEnum.USER);
        }
    }

    public boolean isAdmin() {
        return authorities.contains(UserRoleEnum.ADMIN);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", authorities=" + authorities +
                '}';
    }
}
