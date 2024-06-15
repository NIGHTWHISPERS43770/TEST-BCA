package com.test.Testapp.service.impl;

import com.test.Testapp.constant.Eroles;
import com.test.Testapp.entity.Role;
import com.test.Testapp.entity.User;
import com.test.Testapp.model.request.UserAuthRequest;
import com.test.Testapp.model.response.UserAuthResponse;
import com.test.Testapp.repository.UserRepository;
import com.test.Testapp.security.JwtUtils;
import com.test.Testapp.service.LoginAndRegisterService;
import com.test.Testapp.service.RoleService;
import com.test.Testapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LoginAndRegisterServiceImpl implements LoginAndRegisterService {

    private final RoleService roleService;
    private final UserService userService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    @Override
    public UserAuthResponse register(UserAuthRequest userAuthRequest) {
        Role roleCustomer = roleService.getOrSave(Eroles.GURU);
        String hashPassword = passwordEncoder.encode(userAuthRequest.getPassword());
        User userForm = userRepository.saveAndFlush(
                User.builder()
                        .username(userAuthRequest.getUsername())
                        .password(hashPassword)
                        .roles(List.of(roleCustomer))
                        .build());
        User user = userService.createUser(userAuthRequest, userForm);
        // list role
        List<String> roles = user.getRoles().stream().map(role -> role.getRole().name()).toList();
        return UserAuthResponse.builder()
                .username(user.getUsername())
                .roles(roles)
                .build();
    }

    public String login(UserAuthRequest authRequest) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                authRequest.getUsername(),
                authRequest.getPassword()
        );
        // call method untuk kebutuhan validasi credential
        Authentication authenticated = authenticationManager.authenticate(authentication);
        // jika valid username dan password, maka selanjutnya simpan sesi untuk akses resource tertentu
        SecurityContextHolder.getContext().setAuthentication(authenticated);
        // berikan token
        User user = (User) authenticated.getPrincipal();
        return jwtUtils.generateToken(user);
    }

}
