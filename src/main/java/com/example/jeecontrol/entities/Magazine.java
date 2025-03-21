package com.example.jeecontrol.entities;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "magazines")
@DiscriminatorValue("MAGAZINE")
public class Magazine extends Document {
    
    private String publisher;
    
    private String issueNumber;
    
    @Temporal(TemporalType.DATE)
    private Date dateIssue;
    
    // Constructors
    public Magazine() {
    }
    
    public Magazine(String title, Date dateCreat, String publisher, String issueNumber, Date dateIssue) {
        super(title, dateCreat);
        this.publisher = publisher;
        this.issueNumber = issueNumber;
        this.dateIssue = dateIssue;
    }
    
    // Getters and Setters
    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getIssueNumber() {
        return issueNumber;
    }

    public void setIssueNumber(String issueNumber) {
        this.issueNumber = issueNumber;
    }

    public Date getDateIssue() {
        return dateIssue;
    }

    public void setDateIssue(Date dateIssue) {
        this.dateIssue = dateIssue;
    }
}
