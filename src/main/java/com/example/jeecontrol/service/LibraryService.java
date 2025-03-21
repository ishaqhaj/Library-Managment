package com.example.jeecontrol.service;

import com.example.jeecontrol.dao.*;
import com.example.jeecontrol.entities.*;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.Date;
import java.util.List;

@Stateless
public class LibraryService {
    
    @Inject
    private DocumentDao documentDao;
    
    @Inject
    private UserDao userDao;
    
    @Inject
    private BorrowDao borrowDao;
    
    @Inject
    private BookDao bookDao;
    
    @Inject
    private MagazineDao magazineDao;
    
    // Document related methods
    public List<Document> getAllDocuments() {
        return documentDao.findAll();
    }
    
    public List<Document> getAvailableDocuments() {
        return documentDao.findAvailableDocuments();
    }
    
    public Document getDocumentById(Long id) {
        return documentDao.findById(id);
    }
    
    // User related methods
    public User createUser(String name, String email) {
        // Check if user with same email already exists
        User existingUser = userDao.findByEmail(email);
        if (existingUser != null) {
            throw new IllegalArgumentException("User with email " + email + " already exists");
        }
        
        User user = new User(name, email);
        return userDao.save(user);
    }
    
    public User getUserById(Long id) {
        return userDao.findById(id);
    }
    
    // Borrow related methods
    @Transactional
    public Borrow borrowDocument(Long documentId, Long userId) {
        Document document = documentDao.findById(documentId);
        if (document == null) {
            throw new IllegalArgumentException("Document not found with id: " + documentId);
        }
        
        User user = userDao.findById(userId);
        if (user == null) {
            throw new IllegalArgumentException("User not found with id: " + userId);
        }
        
        // Check if document is already borrowed
        Borrow currentBorrow = borrowDao.findCurrentBorrowByDocument(document);
        if (currentBorrow != null) {
            throw new IllegalStateException("Document is already borrowed");
        }
        
        // Create new borrow
        Borrow borrow = new Borrow();
        borrow.setDocument(document);
        borrow.setUser(user);
        borrow.setDateBorrow(new Date());
        
        return borrowDao.save(borrow);
    }
    
    @Transactional
    public Borrow returnDocument(Long documentId) {
        Document document = documentDao.findById(documentId);
        if (document == null) {
            throw new IllegalArgumentException("Document not found with id: " + documentId);
        }
        
        Borrow borrow = borrowDao.findCurrentBorrowByDocument(document);
        if (borrow == null) {
            throw new IllegalStateException("Document is not currently borrowed");
        }
        
        borrow.setReturnDate(new Date());
        return borrowDao.save(borrow);
    }
    
    public List<Borrow> getCurrentBorrows() {
        return borrowDao.findCurrentBorrows();
    }
    
    // Book related methods
    @Transactional
    public Book addBook(String title, String author, String isbn, Date publicationDate) {
        // Check if book with same ISBN already exists
        Book existingBook = bookDao.findByIsbn(isbn);
        if (existingBook != null) {
            throw new IllegalArgumentException("Book with ISBN " + isbn + " already exists");
        }
        
        Book book = new Book();
        book.setTitle(title);
        book.setAuthor(author);
        book.setIsbn(isbn);
        book.setDatePublishion(publicationDate);
        book.setDateCreat(new Date());
        
        return bookDao.save(book);
    }
    
    // Magazine related methods
    @Transactional
    public Magazine addMagazine(String title, String publisher, String issueNumber, Date issueDate) {
        Magazine magazine = new Magazine();
        magazine.setTitle(title);
        magazine.setPublisher(publisher);
        magazine.setIssueNumber(issueNumber);
        magazine.setDateIssue(issueDate);
        magazine.setDateCreat(new Date());
        
        return magazineDao.save(magazine);
    }
}
