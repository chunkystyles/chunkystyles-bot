package org.allenarcher.discord.bot.event;

import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.Message;
import org.allenarcher.discord.bot.command.CommandManager;
import org.allenarcher.discord.bot.level.LevelManager;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Mono;

public class MessageListener {

    private static final String commandPrefix = "!";

    public static Mono<Void> processMessageEvent(Message message) {
        return Mono.just(message)
                .filter(message1 -> message1.getAuthor().map(user -> !user.isBot()).orElse(false))
                .flatMap(message1 -> {
                    if (StringUtils.startsWithIgnoreCase(message1.getContent(), commandPrefix)){
                        return processCommand(message1);
                    } else {
                        return processMessage(message1);
                    }
                })
                .then();
    }

    private static Mono<Void> processMessage(Message message){
        return Mono.just(message)
                .flatMap(message1 -> {
                    LevelManager.getInstance().addExperience(message1.getAuthor().get().getId().asLong());
                    return Mono.empty();
                })
                .then();
    }

    private static Mono<Void> processCommand(Message message){
        return Mono.just(message)
                .flatMap(message1 -> CommandManager.getInstance()
                        .getCommand(parseCommandAlias(message1.getContent()))
                        .processCommand(message)
                ).then();
    }

    private static String parseCommandAlias(String string){
        if (StringUtils.startsWithIgnoreCase(string, commandPrefix) && string.length() > 1) {
            int index = string.indexOf(" ");
            if (index < 1){
                index = string.length();
            }
            return string.substring(1, index);
        } else {
            return "";
        }
    }
}
