package com.test.Testapp.controller;


import com.test.Testapp.model.request.UserAuthRequest;
import com.test.Testapp.model.response.UserAuthResponse;
import com.test.Testapp.model.response.WebResponse;
import com.test.Testapp.service.LoginAndRegisterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class LoginAndRegisterController {
    private final LoginAndRegisterService loginAndRegisterService;
    @PostMapping("/register")
    public ResponseEntity<?> createUser(
            @Valid @RequestBody UserAuthRequest userAuthRequest){
        UserAuthResponse userAuthResponse = loginAndRegisterService.register(userAuthRequest);
        WebResponse<UserAuthResponse> response = WebResponse.<UserAuthResponse>builder()
                .status(HttpStatus.CREATED.getReasonPhrase())
                .message("Success Register")
                .data(userAuthResponse)
                .build();
        return ResponseEntity.ok(response);
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserAuthRequest userAuthRequest){
        String token = loginAndRegisterService.login(userAuthRequest);
        WebResponse<String> response = WebResponse.<String>builder()
                .status(HttpStatus.CREATED.getReasonPhrase())
                .message("Success Login")
                .data(token)
                .build();
        return ResponseEntity.ok(response);
    }
}

