package com.qi_rakibul.sfa.util;

public interface ServiceEndpoints {

    interface Controllers {
        String ALL_ENDPOINTS = "/api/**";
        String AUTH_CONTROLLER = "/api/auth";
        String POST_CONTROLLER = "/api/posts";
    }

    interface AUTH_CONTROLLER {
        String SIGNUP = "/signup";
        String LOGIN = "/login";
    }
}
