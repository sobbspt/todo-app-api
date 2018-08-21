package com.wanichnun.todoapp.document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;

@Data
@Document(collection = "Todo")
public class Todo {
    @Id
    private String id;
    private String userId;
    private String taskName;
    private Boolean isPinned;
    private Boolean isDone;
    private Long order;
    private Date date;
}
