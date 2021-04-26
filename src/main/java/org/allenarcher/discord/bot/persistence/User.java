package org.allenarcher.discord.bot.persistence;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class User {
    @Id
    private Long id;
    private Long posts;
    private Long experience;

    public void incrementPostsAndExperience(){
        this.posts++;
        this.experience++;
    }
}
