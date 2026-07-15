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

    interface POST_CONTROLLER {
        String CREATE = "";
        String SEARCH_ALL_POST = "";
        String LIKE = "/{id}/like";
        String UNLIKE = "/{id}/like";
        String LIKERS = "/{id}/likes";
    }
}
