package com.test.Testapp.service;

import com.test.Testapp.entity.User;
import com.test.Testapp.model.request.UserAuthRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService extends UserDetailsService {
    User createUser(UserAuthRequest userAuthRequest, User userForm);
    User loadByUserId(String userId);
    User findByUsername(String username);
    User loadUserByUsername(String username) throws UsernameNotFoundException;
}
