package org.allenarcher.discord.bot.event;

import discord4j.core.event.domain.message.MessageCreateEvent;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class MessageCreateListener implements EventListener<MessageCreateEvent> {
    private final MessageListener messageListener;

    public MessageCreateListener(MessageListener messageListener) {
        this.messageListener = messageListener;
    }

    @Override
    public Class<MessageCreateEvent> getEventType() {
        return MessageCreateEvent.class;
    }

    @Override
    public Mono<Void> execute(MessageCreateEvent event) {
        return messageListener.processMessageEvent(event.getMessage());
    }
}
