package com.wanichnun.lineexam.service;

import com.linecorp.bot.model.message.TextMessage;
import com.sun.org.apache.xpath.internal.operations.Bool;
import com.wanichnun.lineexam.document.Todo;
import com.wanichnun.lineexam.model.ResponseModel;
import com.wanichnun.lineexam.repository.TodoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.valid4j.Assertive.require;

@Slf4j
@Service
public class TodoService {

    private TodoRepository todoRepository;

    @Autowired
    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public TextMessage handleTodoCreateRequest(String userId, String message) throws ParseException {
        Boolean isPinned = false;
        Boolean isDone = false;
        Long order = 0L;

        String[] splittedMessage = message.split(" : ");
        require(splittedMessage.length >= 2 && splittedMessage.length <= 3, "Invalid text format");

        String taskName = splittedMessage[0];
        String dateText = splittedMessage[1];
        String timeText = "12:00";
        if (splittedMessage.length > 2) {
            timeText = splittedMessage[2];
        }

        DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.ENGLISH);
        Date date = format.parse(dateText + " " + timeText);
        this.create(userId, taskName, isPinned, isDone, order, date);
        return new TextMessage("success");
    }

    public Todo create(String userId, String taskName, Boolean isPinned, Boolean isDone, Long order, Date date) {
        Todo todo = new Todo();
        todo.setId(UUID.randomUUID().toString());
        todo.setTaskName(taskName);
        todo.setUserId(userId);
        todo.setIsDone(isDone);
        todo.setIsPinned(isPinned);
        todo.setOrder(order);
        todo.setDate(date);

        Todo result = todoRepository.save(todo);
        log.info("Saved todo {}", result);

        return result;
    }

    public Todo update(String id, String userId, Boolean isPinned, Boolean isDone, Long order) {
        Optional<Todo> result = todoRepository.findByIdAndUserId(id, userId);
        require(result.isPresent(), "Resource not found");
        System.out.println("result " + result);
        Todo todo = result.get();
        todo.setIsPinned(isPinned);
        todo.setIsDone(isDone);
        todo.setOrder(order);

        Todo updatedTodo = todoRepository.save(todo);
        log.info("Updated todo {}", updatedTodo);
        return updatedTodo;
    }

    public List<Todo> listTodos(String userId) {
        List<Todo> todoList = todoRepository.findByUserId(userId);
        return todoList;
    }
}
