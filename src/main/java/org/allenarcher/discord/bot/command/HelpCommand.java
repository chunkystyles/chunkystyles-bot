package org.allenarcher.discord.bot.command;

import discord4j.core.object.entity.Message;
import reactor.core.publisher.Mono;

import java.util.List;

public class HelpCommand implements Command {
    private final String helpCommandText;
    private final List<String> aliases;

    public HelpCommand(){
        helpCommandText = "Some help text.";
        aliases = List.of("help", "h");
    }

    @Override
    public Mono<Void> processCommand(Message message) {
        return Mono.just(message)
                .flatMap(Message::getChannel)
                .flatMap((channel -> channel.createMessage(helpCommandText)))
                .then();
    }

    @Override
    public List<String> getAliases() {
        return aliases;
    }
}
