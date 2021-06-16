package io.swagger.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class AuthorizedUser implements UserDetails {

  ArrayList<GrantedAuthority> authorities = null;
  private User user;

  public AuthorizedUser(User user) {
    this.user = user;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

    grantedAuthorities.add(new SimpleGrantedAuthority(user.getRole().name()));
    return grantedAuthorities;
  }

  public void setAuthorities(ArrayList<GrantedAuthority> authorities) {
    this.authorities = authorities;
  }

  @Override
  public String getUsername() {
    return user.getEmail();
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  @Override
  public String getPassword() {
    return user.getPassword();
  }

  public String getEmail() {
    return user.getEmail();
  }

  public Integer getId() {
    return (int)user.getId();
  }

  public User.RoleEnum getRole() {
    return user.getRole();
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
}
