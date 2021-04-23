package org.allenarcher.discord.bot.command;

import discord4j.core.object.entity.Message;
import reactor.core.publisher.Mono;

import java.util.List;

public interface Command {
    Mono<Void> processCommand(Message message);
    List<String> getAliases();
}
