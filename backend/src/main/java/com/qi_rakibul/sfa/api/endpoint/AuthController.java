package com.qi_rakibul.sfa.api.endpoint;

import com.qi_rakibul.sfa.api.payload.request.LoginRequest;
import com.qi_rakibul.sfa.api.payload.request.SignupRequest;
import com.qi_rakibul.sfa.api.payload.response.AuthResponse;
import com.qi_rakibul.sfa.application.service.AuthService;
import com.qi_rakibul.sfa.util.ServiceEndpoints;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ServiceEndpoints.Controllers.AUTH_CONTROLLER)
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping(ServiceEndpoints.AUTH_CONTROLLER.SIGNUP)
    @ResponseStatus(HttpStatus.CREATED)
    public void signup(
            @Valid @RequestBody SignupRequest request
    ) {
        authService.signup(request);
    }

    @PostMapping(ServiceEndpoints.AUTH_CONTROLLER.LOGIN)
    public AuthResponse login(
            @Valid @RequestBody LoginRequest request
    ) {
        return authService.login(request);
    }
}