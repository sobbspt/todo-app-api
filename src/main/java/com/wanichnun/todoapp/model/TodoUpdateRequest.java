package com.wanichnun.todoapp.model;

import lombok.Data;

@Data
public class TodoUpdateRequest {
    private Boolean isPinned;
    private Boolean isDone;
    private Long order;
}
