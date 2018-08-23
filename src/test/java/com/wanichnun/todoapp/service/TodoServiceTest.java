package com.wanichnun.todoapp.service;

import com.linecorp.bot.model.message.TextMessage;
import com.wanichnun.todoapp.document.Todo;
import com.wanichnun.todoapp.repository.TodoRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.valid4j.errors.RequireViolation;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TodoServiceTest {
    private TodoService todoService;

    @Mock
    private TodoRepository todoRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        todoService = new TodoService(todoRepository);
    }

    @Test
    public void testCreateTodo() {
        String mockUserId = "U1";
        String mockTaskName = "mock-task";
        Boolean mockIsPinned = false;
        Boolean mockIsDone = false;
        Long mockOrder = 0L;
        Date mockDate = new Date();

        Todo mockTodo = new Todo();
        mockTodo.setUserId(mockUserId);
        mockTodo.setTaskName(mockTaskName);
        mockTodo.setOrder(mockOrder);
        mockTodo.setIsPinned(mockIsPinned);
        mockTodo.setIsDone(mockIsDone);
        mockTodo.setDate(mockDate);


        when(todoRepository.save(any())).thenReturn(mockTodo);

        Todo actual = todoService.create(mockUserId, mockTaskName, mockIsPinned, mockIsDone, mockDate);

        verify(todoRepository, times(1)).save(any());

        Assert.assertEquals(mockTaskName, actual.getTaskName());
        Assert.assertEquals(mockUserId, actual.getUserId());
        Assert.assertEquals(mockOrder, actual.getOrder());
        Assert.assertEquals(mockIsDone, actual.getIsDone());
        Assert.assertEquals(mockIsPinned, actual.getIsPinned());
        Assert.assertEquals(mockDate, actual.getDate());
    }

    @Test
    public void testUpdateSuccess() {
        String mockUserId = "U1";
        String mockTaskName = "mock-task";
        Boolean mockIsPinned = false;
        Boolean mockIsDone = false;
        Long mockOrder = 0L;
        Date mockDate = new Date();

        Todo mockTodo = new Todo();
        mockTodo.setUserId(mockUserId);
        mockTodo.setTaskName(mockTaskName);
        mockTodo.setOrder(mockOrder);
        mockTodo.setIsPinned(mockIsPinned);
        mockTodo.setIsDone(mockIsDone);
        mockTodo.setDate(mockDate);


        when(todoRepository.findByUserId(any())).thenReturn(Arrays.asList(mockTodo));
        when(todoRepository.saveAll(any())).thenReturn(Arrays.asList(mockTodo));

        List<Todo> actual = todoService.updateOrder(mockUserId, Arrays.asList(mockTodo));

        verify(todoRepository, times(1)).findByUserId(any());
        verify(todoRepository, times(1)).saveAll(any());

        Assert.assertEquals(1, actual.size());
    }

    @Test
    public void testListTodosSuccess() {
        String mockUserId = "U1";
        String mockTaskName = "mock-task";
        Boolean mockIsDone = false;
        Date mockDate = new Date();

        Todo mockTodo1 = new Todo();
        mockTodo1.setId("1");
        mockTodo1.setUserId(mockUserId);
        mockTodo1.setTaskName(mockTaskName);
        mockTodo1.setOrder(1L);
        mockTodo1.setIsPinned(false);
        mockTodo1.setIsDone(mockIsDone);
        mockTodo1.setDate(mockDate);

        Todo mockTodo2 = new Todo();
        mockTodo2.setId("2");
        mockTodo2.setUserId(mockUserId);
        mockTodo2.setTaskName(mockTaskName);
        mockTodo2.setOrder(2L);
        mockTodo2.setIsPinned(true);
        mockTodo2.setIsDone(mockIsDone);
        mockTodo2.setDate(mockDate);

        List<Todo> mockTodoList = new ArrayList<>();
        mockTodoList.add(mockTodo1);
        mockTodoList.add(mockTodo2);

        when(todoRepository.findByUserIdOrderByOrderAsc(any())).thenReturn(mockTodoList);

        List<Todo> actual = todoService.listTodos("U1");

        verify(todoRepository, times(1)).findByUserIdOrderByOrderAsc(any());

        Assert.assertEquals(2, actual.size());
        Assert.assertEquals("2", actual.get(0).getId());

    }

    @Test(expected = RequireViolation.class)
    public void testHandleTodoCreateRequestFailedWhenMessageInvalidFormat() throws ParseException {
        String mockUserId = "U1";
        String mockMessage = "Wrong format";

        Todo mockTodo = new Todo();
        mockTodo.setId("1");
        mockTodo.setUserId(mockUserId);
        mockTodo.setIsPinned(false);

        todoService.handleTodoCreateRequest(mockUserId, mockMessage);
    }

    @Test
    public void testHandleTodoCreateRequestSuccessWhenSpecifyTime() throws ParseException {
        String mockUserId = "U1";
        String mockMessage = "Meeting with James : 20/12/2018 : 22:00";

        Todo mockTodo = new Todo();
        mockTodo.setId("1");
        mockTodo.setUserId(mockUserId);
        mockTodo.setIsPinned(false);

        when(todoRepository.save(any())).thenReturn(mockTodo);

        todoService.handleTodoCreateRequest(mockUserId, mockMessage);

        verify(todoRepository, times(1)).save(any());
    }

    @Test
    public void testHandleTodoCreateRequestSuccessWhenNotSpecifyTime() throws ParseException {
        String mockUserId = "U1";
        String mockMessage = "Meeting with James : 20/12/2018";

        Todo mockTodo = new Todo();
        mockTodo.setId("1");
        mockTodo.setUserId(mockUserId);
        mockTodo.setIsPinned(false);

        when(todoRepository.save(any())).thenReturn(mockTodo);

        todoService.handleTodoCreateRequest(mockUserId, mockMessage);

        verify(todoRepository, times(1)).save(any());
    }

    @Test
    public void testHandleTodoCreateRequestSuccessWhenSpecifyDateAndTimeToday() {
        String mockUserId = "U1";
        String mockMessage = "Meeting with James : today : 22:00";

        Todo mockTodo = new Todo();
        mockTodo.setId("1");
        mockTodo.setUserId(mockUserId);
        mockTodo.setIsPinned(false);

        when(todoRepository.save(any())).thenReturn(mockTodo);

        todoService.handleTodoCreateRequest(mockUserId, mockMessage);

        verify(todoRepository, times(1)).save(any());
    }

    @Test
    public void testHandleTodoCreateRequestSuccessWhenSpecifyDateTomorrow() {
        String mockUserId = "U1";
        String mockMessage = "Meeting with James : tomorrow";

        Todo mockTodo = new Todo();
        mockTodo.setId("1");
        mockTodo.setUserId(mockUserId);
        mockTodo.setIsPinned(false);

        when(todoRepository.save(any())).thenReturn(mockTodo);

        todoService.handleTodoCreateRequest(mockUserId, mockMessage);

        verify(todoRepository, times(1)).save(any());
    }

    @Test
    public void testHandleTodoCreateRequestFailedWhenWrongDate() {
        String mockUserId = "U1";
        String mockMessage = "Meeting with James : wrong";

        Todo mockTodo = new Todo();
        mockTodo.setId("1");
        mockTodo.setUserId(mockUserId);
        mockTodo.setIsPinned(false);

        TextMessage textMessage = todoService.handleTodoCreateRequest(mockUserId, mockMessage);

        Assert.assertEquals("Invalid date format", textMessage.getText());
    }
}
