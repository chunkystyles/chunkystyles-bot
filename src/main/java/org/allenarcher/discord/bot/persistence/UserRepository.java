package org.allenarcher.discord.bot.persistence;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
public class UserRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void insertUser(User user){
        this.entityManager.persist(user);
    }

    public User selectUser(Long id){
        return this.entityManager.find(User.class, id);
    }

    @Transactional
    public void updateUser(User user){
        this.entityManager.merge(user);
    }
}
