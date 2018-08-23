package com.wanichnun.todoapp.repository;

import com.wanichnun.todoapp.document.Todo;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface TodoRepository extends MongoRepository<Todo, String> {

    List<Todo> findByUserId(String userId);
    List<Todo> findByUserIdOrderByOrderAsc(String userId);
}
