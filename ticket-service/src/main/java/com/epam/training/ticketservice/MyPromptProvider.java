package com.epam.training.ticketservice;

import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.springframework.shell.jline.PromptProvider;
import org.springframework.stereotype.Component;

@Component
public class MyPromptProvider implements PromptProvider {
    @Override
    public AttributedString getPrompt() {
        return new AttributedString("Ticket service>", AttributedStyle.DEFAULT.foreground(AttributedStyle.YELLOW));
    }
}
