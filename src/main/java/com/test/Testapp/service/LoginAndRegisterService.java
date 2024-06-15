package com.test.Testapp.service;

import com.test.Testapp.model.request.UserAuthRequest;
import com.test.Testapp.model.response.UserAuthResponse;

public interface LoginAndRegisterService {

    UserAuthResponse register(UserAuthRequest userAuthRequest);

    String login(UserAuthRequest authRequest);
}


