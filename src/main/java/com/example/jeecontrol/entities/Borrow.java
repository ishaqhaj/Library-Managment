package com.example.jeecontrol.entities;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "borrows")
public class Borrow {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "document_id")
    private Document document;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    
    @Temporal(TemporalType.DATE)
    private Date dateBorrow;
    
    @Temporal(TemporalType.DATE)
    private Date returnDate;
    
    // Constructors
    public Borrow() {
    }
    
    public Borrow(Document document, User user, Date dateBorrow, Date returnDate) {
        this.document = document;
        this.user = user;
        this.dateBorrow = dateBorrow;
        this.returnDate = returnDate;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getDateBorrow() {
        return dateBorrow;
    }

    public void setDateBorrow(Date dateBorrow) {
        this.dateBorrow = dateBorrow;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }
}
