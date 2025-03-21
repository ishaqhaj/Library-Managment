package com.example.jeecontrol.dao;

import com.example.jeecontrol.entities.Book;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import java.util.List;

@Transactional
public class BookDao {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    public List<Book> findAll() {
        TypedQuery<Book> query = entityManager.createQuery("SELECT b FROM Book b", Book.class);
        return query.getResultList();
    }
    
    public Book findById(Long id) {
        return entityManager.find(Book.class, id);
    }
    
    public Book findByIsbn(String isbn) {
        TypedQuery<Book> query = entityManager.createQuery(
                "SELECT b FROM Book b WHERE b.isbn = :isbn", Book.class);
        query.setParameter("isbn", isbn);
        List<Book> results = query.getResultList();
        return results.isEmpty() ? null : results.get(0);
    }
    
    public Book save(Book book) {
        if (book.getId() == null) {
            entityManager.persist(book);
        } else {
            book = entityManager.merge(book);
        }
        return book;
    }
    
    public void delete(Book book) {
        entityManager.remove(book);
    }
}
