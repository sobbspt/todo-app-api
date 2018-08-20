package com.wanichnun.lineexam.model;

import lombok.Data;

@Data
public class TodoUpdateRequest {
    private Boolean isPinned;
    private Boolean isDone;
    private Long order;
}
