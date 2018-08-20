package com.wanichnun.lineexam.repository;

import com.wanichnun.lineexam.document.Todo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TodoRepository extends MongoRepository<Todo, String> {
}
