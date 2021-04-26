package org.allenarcher.discord.bot.event;

import discord4j.core.object.entity.Message;
import org.allenarcher.discord.bot.command.CommandManager;
import org.allenarcher.discord.bot.persistence.User;
import org.allenarcher.discord.bot.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Mono;

@Component
public class MessageListener {
    private final String commandPrefix;
    private final CommandManager commandManager;
    private final UserRepository userRepository;

    public MessageListener(@Value("${commands.prefix}") String commandPrefix,
                           CommandManager commandManager,
                           UserRepository userRepository) {
        this.commandPrefix = commandPrefix;
        this.commandManager = commandManager;
        this.userRepository = userRepository;
    }

    public Mono<Void> processMessageEvent(Message message) {
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

    private Mono<Void> processMessage(Message message){
        return Mono.just(message)
                .flatMap(message1 -> {
                    long id = message1.getAuthor().get().getId().asLong();
                    User user = userRepository.selectUser(id);
                    if (user != null) {
                        user.incrementPostsAndExperience();
                        userRepository.updateUser(user);
                    } else  {
                        user = new User();
                        user.setId(id);
                        user.setExperience(1L);
                        user.setPosts(1L);
                        userRepository.insertUser(user);
                    }
                    return Mono.empty();
                })
                .then();
    }

    private Mono<Void> processCommand(Message message){
        return Mono.just(message)
                .flatMap(message1 -> commandManager
                        .getCommand(parseCommandAlias(message1.getContent()))
                        .processCommand(message)
                ).then();
    }

    private String parseCommandAlias(String string){
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
