package org.allenarcher.discord.bot.command;

import discord4j.core.object.entity.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class HelpCommand implements Command {
    private final String helpCommandText;
    private final List<String> aliases;

    public HelpCommand(@Value("${commands.help.text}") String helpCommandText,
                       @Value("${commands.help.aliases}") List<String> aliases) {
        this.helpCommandText = helpCommandText;
        this.aliases = aliases;
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
