package org.allenarcher.discord.bot.command;

import discord4j.core.object.entity.Message;
import org.allenarcher.discord.bot.level.LevelManager;
import reactor.core.publisher.Mono;

import java.util.List;

public class RankCommand implements Command {
    @Override
    public Mono<Void> processCommand(Message message) {
        return Mono.just(message)
                .flatMap(Message::getChannel)
                .flatMap(channel -> {
                    Integer experience = LevelManager.getInstance().getExperience(message.getAuthor().get().getId().asLong());
                    int level = calculateLevel(experience);
                    return channel.createMessage("Your xp = " + experience + "\nYour level = " + level);
                })
                .then();
    }

    @Override
    public List<String> getAliases() {
        return List.of("rank", "level");
    }

    private static int calculateLevel(int experience){
        return experience / 10 + 1;
    }
}
