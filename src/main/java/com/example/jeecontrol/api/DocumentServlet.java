package com.example.jeecontrol.api;

import com.example.jeecontrol.entities.Book;
import com.example.jeecontrol.entities.Document;
import com.example.jeecontrol.entities.Magazine;
import com.example.jeecontrol.service.LibraryService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@WebServlet(name = "documentServlet", urlPatterns = {"/api/documents/*"})
public class DocumentServlet extends HttpServlet {
    
    @Inject
    private LibraryService libraryService;
    
    private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        
        String pathInfo = request.getPathInfo();
        
        try {
            if (pathInfo == null || pathInfo.equals("/")) {
                // List all documents
                List<Document> documents = libraryService.getAllDocuments();
                out.print(gson.toJson(documents));
            } else if (pathInfo.equals("/available")) {
                // List available documents
                List<Document> availableDocuments = libraryService.getAvailableDocuments();
                out.print(gson.toJson(availableDocuments));
            } else {
                // Get document by ID
                String[] splits = pathInfo.split("/");
                if (splits.length != 2) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid path");
                    return;
                }
                Long id = Long.parseLong(splits[1]);
                Document document = libraryService.getDocumentById(id);
                if (document == null) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Document not found");
                } else {
                    out.print(gson.toJson(document));
                }
            }
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        
        String pathInfo = request.getPathInfo();
        
        try {
            if (pathInfo == null || pathInfo.equals("/")) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid path");
                return;
            }
            
            // Read request body
            BufferedReader reader = request.getReader();
            Map<String, Object> payload = gson.fromJson(reader, Map.class);
            
            if (pathInfo.equals("/book")) {
                // Add a new book
                String title = (String) payload.get("title");
                String author = (String) payload.get("author");
                String isbn = (String) payload.get("isbn");
                Date publicationDate = parseDate((String) payload.get("publicationDate"));
                
                Book book = libraryService.addBook(title, author, isbn, publicationDate);
                out.print(gson.toJson(book));
                response.setStatus(HttpServletResponse.SC_CREATED);
            } else if (pathInfo.equals("/magazine")) {
                // Add a new magazine
                String title = (String) payload.get("title");
                String publisher = (String) payload.get("publisher");
                String issueNumber = (String) payload.get("issueNumber");
                Date issueDate = parseDate((String) payload.get("issueDate"));
                
                Magazine magazine = libraryService.addMagazine(title, publisher, issueNumber, issueDate);
                out.print(gson.toJson(magazine));
                response.setStatus(HttpServletResponse.SC_CREATED);
            } else {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid path");
            }
        } catch (IllegalArgumentException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
    
    private Date parseDate(String dateStr) {
        if (dateStr == null) return null;
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid date format. Expected: yyyy-MM-dd");
        }
    }
}
