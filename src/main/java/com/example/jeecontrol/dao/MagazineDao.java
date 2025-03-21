package com.example.jeecontrol.dao;

import com.example.jeecontrol.entities.Magazine;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import java.util.List;

@Transactional
public class MagazineDao {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    public List<Magazine> findAll() {
        TypedQuery<Magazine> query = entityManager.createQuery("SELECT m FROM Magazine m", Magazine.class);
        return query.getResultList();
    }
    
    public Magazine findById(Long id) {
        return entityManager.find(Magazine.class, id);
    }
    
    public Magazine save(Magazine magazine) {
        if (magazine.getId() == null) {
            entityManager.persist(magazine);
        } else {
            magazine = entityManager.merge(magazine);
        }
        return magazine;
    }
    
    public void delete(Magazine magazine) {
        entityManager.remove(magazine);
    }
}
