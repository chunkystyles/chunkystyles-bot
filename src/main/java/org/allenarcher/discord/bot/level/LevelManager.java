package org.allenarcher.discord.bot.level;

import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LevelManager {
    private static LevelManager instance;

    static {
        LevelManager.instance = new LevelManager();
    }

    private final Map<Long, Integer> experienceMap;

    private LevelManager(){
        this.experienceMap = new ConcurrentHashMap<>();
    }

    public Integer getExperience(Long key){
        return this.experienceMap.getOrDefault(key, 0);
    }

    public Mono<Void> addExperience(Long key){
        experienceMap.put(key, experienceMap.getOrDefault(key, 0) + 1);
        return Mono.empty();
    }

    public static LevelManager getInstance(){
        return LevelManager.instance;
    }
}
