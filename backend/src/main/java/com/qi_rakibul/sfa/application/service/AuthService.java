package com.qi_rakibul.sfa.application.service;

import com.qi_rakibul.sfa.api.payload.request.SignupRequest;

public interface AuthService {

    void signup(SignupRequest request);
}
