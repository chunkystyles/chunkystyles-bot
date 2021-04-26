package org.allenarcher.discord.bot.level;

import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class LevelManager {

    private final Map<Long, Integer> experienceMap;

    public LevelManager() {
        this.experienceMap = new ConcurrentHashMap<>();
    }

    public Integer getExperience(Long key){
        return this.experienceMap.getOrDefault(key, 0);
    }

    public Mono<Void> addExperience(Long key){
        experienceMap.put(key, experienceMap.getOrDefault(key, 0) + 1);
        return Mono.empty();
    }
}
