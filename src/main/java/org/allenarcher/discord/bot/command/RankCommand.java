package org.allenarcher.discord.bot.command;

import discord4j.core.object.entity.Message;
import org.allenarcher.discord.bot.level.LevelManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class RankCommand implements Command {
    private final List<String> aliases;
    private final LevelManager levelManager;

    public RankCommand(@Value("${commands.rank.aliases}") List<String> aliases, LevelManager levelManager) {
        this.aliases = aliases;
        this.levelManager = levelManager;
    }

    @Override
    public Mono<Void> processCommand(Message message) {
        return Mono.just(message)
                .flatMap(Message::getChannel)
                .flatMap(channel -> {
                    Integer experience = levelManager.getExperience(message.getAuthor().get().getId().asLong());
                    int level = calculateLevel(experience);
                    return channel.createMessage("Your xp = " + experience + "\nYour level = " + level);
                })
                .then();
    }

    @Override
    public List<String> getAliases() {
        return aliases;
    }

    private static int calculateLevel(int experience){
        return experience / 10 + 1;
    }
}
