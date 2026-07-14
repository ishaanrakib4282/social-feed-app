package com.qi_rakibul.sfa.api.endpoint;

import com.qi_rakibul.sfa.application.domain.AuthenticatedUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
public class TestController {
    @GetMapping("/test-method")
    public String test(
            Authentication authentication
    ) {

        AuthenticatedUser user = (AuthenticatedUser) authentication.getPrincipal();

        return "Hello " + user.getEmail();
    }
}