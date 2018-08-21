package com.wanichnun.todoapp.model;

import lombok.Getter;

@Getter
public enum Response {
    SUCCESS("success"),
    ERROR("error");

    private final String content;

    Response(String content) {
        this.content = content;
    }
}
