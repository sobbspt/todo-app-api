package com.wanichnun.lineexam.handler;

import com.linecorp.bot.model.event.message.TextMessageContent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class MessageHandlerTest {
    @Autowired
    MessageHandler messageHandler;

//    @Test
//    public void testHandleTextMessageEvent() {
//        TextMessageContent textMessageContent = new TextMessageContent("123", "edit");
//    }
}
