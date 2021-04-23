package org.allenarcher.discord.bot;

import discord4j.core.object.entity.Message;
import org.allenarcher.discord.bot.command.CommandManager;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Mono;

public class MessageListener {

    private static final String commandPrefix = "!";

    public static Mono<Void> processMessageEvent(Message message) {
        return Mono.just(message)
                .filter(message2 -> message2.getAuthor().map(user -> !user.isBot()).orElse(false))
                .flatMap(message2 -> {
                    if (StringUtils.startsWithIgnoreCase(message2.getContent(), commandPrefix)){
                        return processCommand(message2);
                    } else {
                        return processMessage(message2);
                    }
                })
                .then();
    }

    private static Mono<Void> processMessage(Message message){
        return Mono.just(message)
                .flatMap(Message::getChannel)
                .flatMap((channel -> channel.createMessage("You posted a message, not a command.")))
                .then();
    }

    private static Mono<Void> processCommand(Message message){
        return Mono.just(message)
                .flatMap(message1 -> {
                    String messageContent = message1.getContent().replaceFirst(commandPrefix, "");
                    String commandAlias = messageContent.split(" ")[0];
                    return CommandManager.getInstance().getCommand(commandAlias).processCommand(message);
                })
                .then();
    }
}
