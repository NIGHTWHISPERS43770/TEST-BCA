package com.test.Testapp.service.impl;

import com.test.Testapp.constant.Eroles;
import com.test.Testapp.entity.Role;
import com.test.Testapp.entity.User;
import com.test.Testapp.model.request.UserAuthRequest;
import com.test.Testapp.repository.UserRepository;
import com.test.Testapp.service.RoleService;
import com.test.Testapp.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final RoleService roleService;
    private final UserRepository userRepository;


    @Override
    public User createUser(UserAuthRequest userAuthRequest, User userForm) {
        Role roleGuru = roleService.getOrSave(Eroles.GURU);
        User user = User.builder()
                .username(userAuthRequest.getUsername())
                .password(userAuthRequest.getPassword())
                .roles(List.of(roleGuru))
                .build();
        return userRepository.saveAndFlush(user);
    }

    @Override
    public User loadByUserId(String userId) {
        return userRepository.findById(userId)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Load With User Id Is Fail"));

    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Username Not Found"));

    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Load by username is fail"))
                ;
    }
}
