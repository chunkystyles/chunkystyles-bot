package org.allenarcher.discord.bot.command;

import discord4j.core.object.entity.Message;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

public class UnrecognizedCommand implements Command {
    @Override
    public Mono<Void> processCommand(Message message) {
        return Mono.just(message)
                .flatMap(Message::getChannel)
                .flatMap((channel -> channel.createMessage("Command '" + message.getContent() + "' is not recognized.")))
                .then();
    }

    @Override
    public List<String> getAliases() {
        return Collections.singletonList("unrecognized");
    }
}
