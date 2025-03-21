package com.example.jeecontrol.entities;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "books")
@DiscriminatorValue("BOOK")
public class Book extends Document {
    
    private String author;
    
    private String isbn;
    
    @Temporal(TemporalType.DATE)
    private Date datePublishion;
    
    // Constructors
    public Book() {
    }
    
    public Book(String title, Date dateCreat, String author, String isbn, Date datePublishion) {
        super(title, dateCreat);
        this.author = author;
        this.isbn = isbn;
        this.datePublishion = datePublishion;
    }
    
    // Getters and Setters
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Date getDatePublishion() {
        return datePublishion;
    }

    public void setDatePublishion(Date datePublishion) {
        this.datePublishion = datePublishion;
    }
}
