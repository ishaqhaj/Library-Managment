package com.example.jeecontrol.dao;

import com.example.jeecontrol.entities.Document;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import java.util.List;

@Transactional
public class DocumentDao {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    public List<Document> findAll() {
        TypedQuery<Document> query = entityManager.createQuery("SELECT d FROM Document d", Document.class);
        return query.getResultList();
    }
    
    public Document findById(Long id) {
        return entityManager.find(Document.class, id);
    }
    
    public Document save(Document document) {
        if (document.getId() == null) {
            entityManager.persist(document);
        } else {
            document = entityManager.merge(document);
        }
        return document;
    }
    
    public void delete(Document document) {
        entityManager.remove(document);
    }
    
    public List<Document> findAvailableDocuments() {
        String jpql = "SELECT d FROM Document d WHERE NOT EXISTS " +
                "(SELECT b FROM Borrow b WHERE b.document = d AND b.returnDate IS NULL)";
        TypedQuery<Document> query = entityManager.createQuery(jpql, Document.class);
        return query.getResultList();
    }
}
