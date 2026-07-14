package com.qi_rakibul.sfa.application.service;

import com.qi_rakibul.sfa.api.payload.request.LoginRequest;
import com.qi_rakibul.sfa.api.payload.request.SignupRequest;
import com.qi_rakibul.sfa.api.payload.response.AuthResponse;

public interface AuthService {

    void signup(SignupRequest request);

    AuthResponse login(LoginRequest request);
}
