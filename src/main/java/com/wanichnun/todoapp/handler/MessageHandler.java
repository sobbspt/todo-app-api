package com.wanichnun.todoapp.handler;

import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;
import com.wanichnun.todoapp.service.TodoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.valid4j.errors.RequireViolation;

import java.text.ParseException;

import static org.valid4j.Assertive.require;

@LineMessageHandler
@Slf4j
public class MessageHandler {
    String edit = "edit";

    @Autowired
    private TodoService todoService;

    @EventMapping
    public TextMessage handleTextMessageEvent(MessageEvent<TextMessageContent> event) throws ParseException {
        log.info("Got message this bot: {}", event);
        try {
            if (edit.equals(event.getMessage().getText())) {
                return new TextMessage("https://google.com");
            }
            else {
                String userId = event.getSource().getUserId();
                String messageContent = event.getMessage().getText();
                return todoService.handleTodoCreateRequest(userId, messageContent);
            }
        }
        catch (RequireViolation re) {
            return new TextMessage(re.getMessage());
        }
        catch (Exception e) {
            log.error("Error " + e);
            return new TextMessage("Wrong format");
        }

    }
}
