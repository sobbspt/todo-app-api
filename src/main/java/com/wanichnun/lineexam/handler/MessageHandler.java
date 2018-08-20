package com.wanichnun.lineexam.handler;

import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;
import com.sun.org.apache.xpath.internal.operations.Bool;
import com.wanichnun.lineexam.document.Todo;
import com.wanichnun.lineexam.service.TodoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.valid4j.errors.RequireViolation;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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
                Boolean isPinned = false;
                Boolean isDone = false;
                Long order = 0L;

                String[] message = event.getMessage().getText().split(" : ");
                require(message.length >= 2 && message.length <= 3, "Invalid text format");

                String taskName = message[0];
                String dateText = message[1];
                String timeText = "12:00";
                if (message.length > 2) {
                    timeText = message[2];
                }

                DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.ENGLISH);
                Date date = format.parse(dateText + " " + timeText);
                todoService.create(userId, taskName, isPinned, isDone, order, date);

                return new TextMessage("success");
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
