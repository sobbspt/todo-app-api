package com.wanichnun.lineexam.service;

import com.linecorp.bot.model.message.TextMessage;
import com.wanichnun.lineexam.document.Todo;
import com.wanichnun.lineexam.repository.TodoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Slf4j
@Service
public class TodoService {

    private TodoRepository todoRepository;

    @Autowired
    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public Todo create(String userId, String taskName, Boolean isPinned, Boolean isDone, Long order, Date date) {
        Todo todo = new Todo();
        todo.setId(UUID.randomUUID().toString());
        todo.setUserId(userId);
        todo.setIsDone(isDone);
        todo.setIsPinned(isPinned);
        todo.setOrder(order);
        todo.setDate(date);

        Todo result = todoRepository.save(todo);
        log.info("Saved todo {}", result);

        return result;
    }
}
