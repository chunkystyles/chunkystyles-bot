package org.allenarcher.discord.bot.command;

import discord4j.core.object.entity.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

@Component
public class UnrecognizedCommand implements Command {
    private final String text;

    public UnrecognizedCommand(@Value("${commands.unrecognized.text}") String text) {
        this.text = text;
    }

    @Override
    public Mono<Void> processCommand(Message message) {
        return Mono.just(message)
                .flatMap(Message::getChannel)
                .flatMap((channel -> channel.createMessage(String.format(text, message.getContent()))))
                .then();
    }

    @Override
    public List<String> getAliases() {
        return Collections.emptyList();
    }
}
