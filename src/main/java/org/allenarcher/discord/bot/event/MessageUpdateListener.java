package org.allenarcher.discord.bot.event;

import discord4j.core.event.domain.message.MessageUpdateEvent;
import org.allenarcher.discord.bot.command.CommandManager;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class MessageUpdateListener extends MessageListener implements EventListener<MessageUpdateEvent>{

    public MessageUpdateListener(CommandManager commandManager) {
        super(commandManager);
    }

    @Override
    public Class<MessageUpdateEvent> getEventType() {
        return MessageUpdateEvent.class;
    }

    @Override
    public Mono<Void> execute(MessageUpdateEvent event) {
        // I have no reason to process message update events at this time.
        return Mono.empty();
    }
}
