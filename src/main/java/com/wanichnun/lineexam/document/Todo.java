package com.wanichnun.lineexam.document;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Data
@Document(collection = "Todo")
public class Todo {
    @Field(value = "id")
    private String id;
    private String userId;
    private String taskName;
    private Boolean isPinned;
    private Boolean isDone;
    private Long order;
    private Date date;
}
