package com.example.jeecontrol.entities;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "documents")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "document_type")
public class Document {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String title;
    
    @Temporal(TemporalType.DATE)
    private Date dateCreat;
    
    @OneToMany(mappedBy = "document")
    private java.util.List<Borrow> borrows;
    
    // Constructors
    public Document() {
    }
    
    public Document(String title, Date dateCreat) {
        this.title = title;
        this.dateCreat = dateCreat;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDateCreat() {
        return dateCreat;
    }

    public void setDateCreat(Date dateCreat) {
        this.dateCreat = dateCreat;
    }
    
    public java.util.List<Borrow> getBorrows() {
        return borrows;
    }
    
    public void setBorrows(java.util.List<Borrow> borrows) {
        this.borrows = borrows;
    }
}
