package com.wanichnun.lineexam.controller;

import com.wanichnun.lineexam.model.Response;
import com.wanichnun.lineexam.model.ResponseModel;
import com.wanichnun.lineexam.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/todos")
public class TodoController {
    @Autowired
    private TodoService todoService;

    @GetMapping
    public HttpEntity<ResponseModel> getAll(
            @RequestParam(name = "userId") String userId) {
        return new ResponseModel(Response.SUCCESS.getContent(), todoService.listTodos(userId)).build(HttpStatus.OK);
    }

}
