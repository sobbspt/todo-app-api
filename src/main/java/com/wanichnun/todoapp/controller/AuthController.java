package com.wanichnun.todoapp.controller;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.wanichnun.todoapp.model.AuthRequest;
import com.wanichnun.todoapp.model.Response;
import com.wanichnun.todoapp.model.ResponseModel;
import com.wanichnun.todoapp.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping()
    public HttpEntity<ResponseModel> update(
            @RequestBody AuthRequest request
            ) throws UnirestException {
        return new ResponseModel(Response.SUCCESS.getContent(), authService.getAccessToken(request)).build(HttpStatus.OK);
    }
}
