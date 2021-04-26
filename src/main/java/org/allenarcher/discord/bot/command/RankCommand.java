package org.allenarcher.discord.bot.command;

import discord4j.core.object.entity.Message;
import org.allenarcher.discord.bot.persistence.User;
import org.allenarcher.discord.bot.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class RankCommand implements Command {
    private final List<String> aliases;
    private final UserRepository userRepository;

    public RankCommand(@Value("${commands.rank.aliases}") List<String> aliases, UserRepository userRepository) {
        this.aliases = aliases;
        this.userRepository = userRepository;
    }

    @Override
    public Mono<Void> processCommand(Message message) {
        return Mono.just(message)
                .flatMap(Message::getChannel)
                .flatMap(channel -> {
                    Long id = message.getAuthor().get().getId().asLong();
                    User user = userRepository.selectUser(id);
                    long level = user != null ? calculateLevel(user.getExperience()) : 0L;
                    long experience = user != null ? user.getExperience() : 0L;
                    return channel.createMessage("Your xp = " + experience + "\nYour level = " + level);
                })
                .then();
    }

    @Override
    public List<String> getAliases() {
        return aliases;
    }

    private static Long calculateLevel(Long experience){
        return experience / 10 + 1;
    }
}
