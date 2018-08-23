package com.wanichnun.todoapp.service;

import com.linecorp.bot.model.message.TextMessage;
import com.wanichnun.todoapp.annotation.LogExecutionTime;
import com.wanichnun.todoapp.document.Todo;
import com.wanichnun.todoapp.model.Response;
import com.wanichnun.todoapp.repository.TodoRepository;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.valid4j.Assertive.require;

@Slf4j
@Service
public class TodoService {

    private static final String TODAY = "today";
    private static final String TOMORROW = "tomorrow";
    private static final String INVALID_TEXT_FORMAT = "Invalid text format";
    private static final String INVALID_DATE_FORMAT = "Invalid date format";
    private static final String DATE_FORMAT = "dd/MM/yyyy HH:mm";

    private TodoRepository todoRepository;

    @Autowired
    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @LogExecutionTime
    public TextMessage handleTodoCreateRequest(String userId, String message) {
        Boolean isPinned = false;
        Boolean isDone = false;


        String[] splittedMessage = message.split(" : ");
        require(splittedMessage.length >= 2 && splittedMessage.length <= 3, INVALID_TEXT_FORMAT);

        String taskName = splittedMessage[0];
        String dateText = splittedMessage[1];
        String timeText = "12:00";
        if (splittedMessage.length > 2) {
            timeText = splittedMessage[2];
        }

        DateFormat format = new SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH);

        if (dateText.equalsIgnoreCase(TODAY)) {
            dateText = convertDate(TODAY);
        }
        if (dateText.equalsIgnoreCase(TOMORROW)) {
            dateText = convertDate(TOMORROW);
        }

        Date date;
        try {
            date = format.parse(dateText + " " + timeText);
        } catch (ParseException pe) {
            pe.printStackTrace();
            return new TextMessage(INVALID_DATE_FORMAT);
        }

        this.create(userId, taskName, isPinned, isDone, date);
        return new TextMessage(Response.SUCCESS.getContent());
    }

    private String convertDate(String date) {
        StringBuilder sb = new StringBuilder();
        Calendar calendar = Calendar.getInstance();

        if (date.equalsIgnoreCase(TOMORROW)) {
            calendar.add(Calendar.DATE, 1);
        }

        sb.append(calendar.get(Calendar.DAY_OF_MONTH)).append("/");
        sb.append(calendar.get(Calendar.MONTH)).append("/");
        sb.append(calendar.get(Calendar.YEAR));

        return sb.toString();
    }

    public Todo create(String userId, String taskName, Boolean isPinned, Boolean isDone, Date date) {
        Todo todo = new Todo();
        todo.setId(UUID.randomUUID().toString());
        todo.setTaskName(taskName);
        todo.setUserId(userId);
        todo.setIsDone(isDone);
        todo.setIsPinned(isPinned);
        todo.setOrder(calculateOrder(userId));
        todo.setDate(date);

        Todo result = todoRepository.save(todo);
        log.info("Saved todo {}", result);

        return result;
    }

    private Long calculateOrder(String userId) {
        return (long) (todoRepository.findByUserId(userId).size() + 1);
    }

    public List<Todo> listTodos(String userId) {
        List<Todo> todoList = todoRepository.findByUserIdOrderByOrderAsc(userId);
        List<Todo> pinnedTodoList = new ArrayList<>();
        List<Todo> sortedTodoList = new ArrayList<>();
        todoList.forEach((todo -> {
            if (todo.getIsPinned()) {
                pinnedTodoList.add(todo);
            }
        }));

        todoList.removeAll(pinnedTodoList);
        sortedTodoList.addAll(pinnedTodoList);
        sortedTodoList.addAll(todoList);

        return sortedTodoList;
    }

    public List<Todo> updateOrder(String userId, List<Todo> todoList) {
        require(todoRepository.findByUserId(userId).size() > 0);
        for (int i = 0; i < todoList.size(); i++) {
            todoList.get(i).setOrder((long) i+1);
        }
        return todoRepository.saveAll(todoList);
    }
}
