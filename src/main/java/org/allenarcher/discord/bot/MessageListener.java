package org.allenarcher.discord.bot;

import discord4j.core.object.entity.Message;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Mono;

public class MessageListener {

    private static final String commandPrefix = "!";

    public static Mono<Void> processMessageEvent(Message eventMessage) {
        return Mono.just(eventMessage)
                .filter(message -> message.getAuthor().map(user -> !user.isBot()).orElse(false))
                .flatMap(message -> {
                    if (StringUtils.startsWithIgnoreCase(message.getContent(), commandPrefix)){
                        return processCommand(message);
                    } else {
                        return processMessage(message);
                    }
                })
                .then();
    }

    private static Mono<Void> processMessage(Message eventMessage){
        return Mono.just(eventMessage)
                .flatMap(Message::getChannel)
                .flatMap((messageChannel -> messageChannel.createMessage("You posted a message, not a command.")))
                .then();
    }

    private static Mono<Void> processCommand(Message eventMessage){
        return Mono.just(eventMessage)
                .flatMap(Message::getChannel)
                .flatMap((messageChannel -> messageChannel.createMessage("You used the command '" + eventMessage.getContent() + "'")))
                .then();
    }
}
