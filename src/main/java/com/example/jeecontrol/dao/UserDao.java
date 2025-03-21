package com.example.jeecontrol.dao;

import com.example.jeecontrol.entities.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import java.util.List;

@Transactional
public class UserDao {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    public List<User> findAll() {
        TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u", User.class);
        return query.getResultList();
    }
    
    public User findById(Long id) {
        return entityManager.find(User.class, id);
    }
    
    public User findByEmail(String email) {
        TypedQuery<User> query = entityManager.createQuery(
                "SELECT u FROM User u WHERE u.mail = :email", User.class);
        query.setParameter("email", email);
        List<User> results = query.getResultList();
        return results.isEmpty() ? null : results.get(0);
    }
    
    public User save(User user) {
        if (user.getId() == null) {
            entityManager.persist(user);
        } else {
            user = entityManager.merge(user);
        }
        return user;
    }
    
    public void delete(User user) {
        entityManager.remove(user);
    }
}
