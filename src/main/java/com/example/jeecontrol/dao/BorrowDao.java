package com.example.jeecontrol.dao;

import com.example.jeecontrol.entities.Borrow;
import com.example.jeecontrol.entities.Document;
import com.example.jeecontrol.entities.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Transactional
public class BorrowDao {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    public List<Borrow> findAll() {
        TypedQuery<Borrow> query = entityManager.createQuery("SELECT b FROM Borrow b", Borrow.class);
        return query.getResultList();
    }
    
    public Borrow findById(Long id) {
        return entityManager.find(Borrow.class, id);
    }
    
    public Borrow save(Borrow borrow) {
        if (borrow.getId() == null) {
            entityManager.persist(borrow);
        } else {
            borrow = entityManager.merge(borrow);
        }
        return borrow;
    }
    
    public void delete(Borrow borrow) {
        entityManager.remove(borrow);
    }
    
    public List<Borrow> findCurrentBorrows() {
        TypedQuery<Borrow> query = entityManager.createQuery(
                "SELECT b FROM Borrow b WHERE b.returnDate IS NULL", Borrow.class);
        return query.getResultList();
    }
    
    public Borrow findCurrentBorrowByDocument(Document document) {
        TypedQuery<Borrow> query = entityManager.createQuery(
                "SELECT b FROM Borrow b WHERE b.document = :document AND b.returnDate IS NULL", 
                Borrow.class);
        query.setParameter("document", document);
        List<Borrow> results = query.getResultList();
        return results.isEmpty() ? null : results.get(0);
    }
    
    public List<Borrow> findBorrowsByUser(User user) {
        TypedQuery<Borrow> query = entityManager.createQuery(
                "SELECT b FROM Borrow b WHERE b.user = :user", 
                Borrow.class);
        query.setParameter("user", user);
        return query.getResultList();
    }
}
