package com.wanichnun.todoapp.repository;

import com.wanichnun.todoapp.document.Todo;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface TodoRepository extends MongoRepository<Todo, String> {
    Optional<Todo> findByIdAndUserId(String id, String userId);

    List<Todo> findByUserId(String userId);
}
