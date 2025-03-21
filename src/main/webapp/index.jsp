<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Documentation API</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 20px;
            line-height: 1.6;
            color: #333;
        }
        .container {
            max-width: 1000px;
            margin: 0 auto;
        }
        h1 {
            color: #2c3e50;
            border-bottom: 2px solid #eee;
            padding-bottom: 10px;
        }
        h2 {
            color: #3498db;
            margin-top: 30px;
        }
        h3 {
            color: #2980b9;
        }
        pre {
            background-color: #f4f4f4;
            padding: 15px;
            border-radius: 5px;
            overflow-x: auto;
        }
        code {
            font-family: Consolas, monospace;
        }
        .method {
            display: inline-block;
            padding: 3px 6px;
            border-radius: 3px;
            font-weight: bold;
            color: white;
        }
        .get {
            background-color: #61affe;
        }
        .post {
            background-color: #49cc90;
        }
        .put {
            background-color: #fca130;
        }
        .delete {
            background-color: #f93e3e;
        }
        .test-tool {
            margin-bottom: 20px;
        }
        .test-tool img {
            max-width: 100%;
            height: auto;
            border: 1px solid #ddd;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin: 20px 0;
        }
        th, td {
            text-align: left;
            padding: 12px;
            border-bottom: 1px solid #ddd;
        }
        th {
            background-color: #f2f2f2;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>Documentation des API</h1>
    
    <section>
        <h2>Documents</h2>
        
        <h3>Obtenir tous les documents</h3>
        <p><strong>GET</strong> /api/documents/</p>
        <p>Renvoie la liste de tous les documents présents dans la bibliothèque.</p>
        
        <h3>Obtenir les documents disponibles</h3>
        <p><strong>GET</strong> /api/documents/available</p>
        <p>Renvoie la liste des documents qui ne sont pas actuellement empruntés.</p>
        
        <h3>Obtenir un document par ID</h3>
        <p><strong>GET</strong> /api/documents/{id}</p>
        <p>Renvoie les détails d'un document spécifique.</p>
        
        <h3>Ajouter un nouveau livre</h3>
        <p><strong>POST</strong> /api/documents/book</p>

        <h3>Ajouter un nouveau magazine</h3>
        <p><strong>POST</strong> /api/documents/magazine</p>
        
    </section>
    
    <section>
        <h2>Utilisateurs</h2>
        
        <h3>Obtenir un utilisateur par ID</h3>
        <p><strong>GET</strong> /api/users/{id}</p>
        <p>Renvoie les détails d'un utilisateur spécifique.</p>
        
        <h3>Ajouter un nouvel utilisateur</h3>
        <p><strong>POST</strong> /api/users/</p>

    </section>
    
    <section>
        <h2>Emprunts</h2>
        
        <h3>Obtenir tous les emprunts en cours</h3>
        <p><strong>GET</strong> /api/borrows/</p>
        <p>Renvoie la liste de tous les emprunts en cours.</p>
        
        <h3>Emprunter un document</h3>
        <p><strong>POST</strong> /api/borrows/</p>

        <h3>Retourner un document</h3>
        <p><strong>POST</strong> /api/borrows/return</p>

    </section>
</div>
</body>
</html>