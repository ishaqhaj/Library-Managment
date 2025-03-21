package com.example.jeecontrol.entities;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    
    private String mail;
    
    @OneToMany(mappedBy = "user")
    private List<Borrow> borrows;
    
    // Constructors
    public User() {
    }
    
    public User(String name, String mail) {
        this.name = name;
        this.mail = mail;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
    
    public List<Borrow> getBorrows() {
        return borrows;
    }
    
    public void setBorrows(List<Borrow> borrows) {
        this.borrows = borrows;
    }
}
