package com.example.jeecontrol.api;

import com.example.jeecontrol.entities.Borrow;
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
import java.util.List;
import java.util.Map;

@WebServlet(name = "borrowServlet", urlPatterns = {"/api/borrows/*"})
public class BorrowServlet extends HttpServlet {
    
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
                // Get all current borrows
                List<Borrow> borrows = libraryService.getCurrentBorrows();
                out.print(gson.toJson(borrows));
            } else {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid path");
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
                // Create new borrow
                BufferedReader reader = request.getReader();
                Map<String, Object> payload = gson.fromJson(reader, Map.class);
                
                Number documentId = (Number) payload.get("documentId");
                Number userId = (Number) payload.get("userId");
                
                if (documentId == null) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Document ID is required");
                    return;
                }
                
                if (userId == null) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "User ID is required");
                    return;
                }
                
                Borrow borrow = libraryService.borrowDocument(documentId.longValue(), userId.longValue());
                out.print(gson.toJson(borrow));
                response.setStatus(HttpServletResponse.SC_CREATED);
            } else if (pathInfo.equals("/return")) {
                // Return a document
                BufferedReader reader = request.getReader();
                Map<String, Object> payload = gson.fromJson(reader, Map.class);
                
                Number documentId = (Number) payload.get("documentId");
                
                if (documentId == null) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Document ID is required");
                    return;
                }
                
                Borrow borrow = libraryService.returnDocument(documentId.longValue());
                out.print(gson.toJson(borrow));
            } else {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid path");
            }
        } catch (IllegalArgumentException | IllegalStateException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
