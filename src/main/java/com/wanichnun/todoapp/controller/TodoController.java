package com.wanichnun.todoapp.controller;

import com.wanichnun.todoapp.document.Todo;
import com.wanichnun.todoapp.model.Response;
import com.wanichnun.todoapp.model.ResponseModel;
import com.wanichnun.todoapp.model.TodoUpdateRequest;
import com.wanichnun.todoapp.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/users")
public class TodoController {
    @Autowired
    private TodoService todoService;

    @GetMapping(path = "{userId}/todos")
    public HttpEntity<ResponseModel> getAll(
            @PathVariable(name = "userId") String userId) {
        return new ResponseModel(Response.SUCCESS.getContent(), todoService.listTodos(userId)).build(HttpStatus.OK);
    }

//    @PutMapping(path = "{userId}/todos/{todoId}")
//    public HttpEntity<ResponseModel> update(
//            @PathVariable(name = "userId") String userId,
//            @PathVariable(name = "todoId") String todoId,
//            @RequestBody TodoUpdateRequest request
//    ) {
//        return new ResponseModel(Response.SUCCESS.getContent(), todoService.update(
//                todoId,
//                userId,
//                request.getIsPinned(),
//                request.getIsDone(),
//                request.getOrder())).build(HttpStatus.OK);
//    }

    @PutMapping(path = "{userId}/todos")
    public HttpEntity<ResponseModel> update(
            @PathVariable(name = "userId") String userId,
            @RequestBody List<Todo> todoList
    ) {
        return new ResponseModel(Response.SUCCESS.getContent(), todoService.updateOrder(
                userId,
                todoList)).build(HttpStatus.OK);
    }
}
