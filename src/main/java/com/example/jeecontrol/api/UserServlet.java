package com.example.jeecontrol.api;

import com.example.jeecontrol.entities.User;
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
import java.util.Map;

@WebServlet(name = "userServlet", urlPatterns = {"/api/users/*"})
public class UserServlet extends HttpServlet {
    
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
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid path");
                return;
            }
            
            // Get user by ID
            String[] splits = pathInfo.split("/");
            if (splits.length != 2) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid path");
                return;
            }
            
            Long id = Long.parseLong(splits[1]);
            User user = libraryService.getUserById(id);
            
            if (user == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "User not found");
            } else {
                out.print(gson.toJson(user));
            }
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        
        try {
            // Read request body
            BufferedReader reader = request.getReader();
            Map<String, String> payload = gson.fromJson(reader, Map.class);
            
            String name = payload.get("name");
            String email = payload.get("email");
            
            if (name == null || name.trim().isEmpty()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Name is required");
                return;
            }
            
            if (email == null || email.trim().isEmpty()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Email is required");
                return;
            }
            
            User user = libraryService.createUser(name, email);
            out.print(gson.toJson(user));
            response.setStatus(HttpServletResponse.SC_CREATED);
        } catch (IllegalArgumentException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
