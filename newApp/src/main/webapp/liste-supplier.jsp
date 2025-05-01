<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="eval.newApp.modele.supplier.Supplier" %>
<!DOCTYPE html>
<html lang="fr">
<head>
  <meta charset="UTF-8">
  <title>Liste des Fournisseurs - ERPStyle</title>
  <meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<body>
<jsp:include page="sidebar.jsp" />
  <div class="list-box">
    <h2>Fournisseurs</h2>

    <table class="supplier-table">
      <thead>
        <tr>
          <th>Nom</th>
          <th>Groupe</th>
          <th>Pays</th>
          <th>Type</th>
          <th>action</th>
        </tr>
      </thead>
      <tbody>
        <%
          List<Supplier> suppliers = (List<Supplier>) request.getAttribute("suppliers");
          if (suppliers != null) {
              for (Supplier supplier : suppliers) {
        %>
        <tr>
          <td><%= supplier.getSupplierName() %></td>
          <td><%= supplier.getSupplierGroup() %></td>
          <td><%= supplier.getCountry() %></td>
          <td><%= supplier.getSupplierType() %></td>
          <td><a href="<%= request.getContextPath() %>/listeSupplierQuotation"><button>voir les devis</button></a></td>
        </tr>
        <%
              }
          } else {
        %>
        <tr>
          <td colspan="4">Aucun fournisseur trouvé.</td>
        </tr>
        <%
          }
        %>
      </tbody>
    </table>

    <div class="footer">
      © 2025 MonEntreprise
    </div>
  </div>

  <style>
    body {
      margin: 0;
      font-family: "Helvetica Neue", Helvetica, Arial, sans-serif;
      background-color: #f7f9fc;
      display: flex;
      align-items: center;
      justify-content: center;
      min-height: 100vh;
    }

    .list-box {
      background: #fff;
      border: 1px solid #e1e5eb;
      box-shadow: 0 2px 8px rgba(0,0,0,0.05);
      border-radius: 6px;
      width: 100%;
      max-width: 800px;
      padding: 30px;
      box-sizing: border-box;
    }

    .list-box h2 {
      text-align: center;
      color: #4b6cb7;
      margin-bottom: 24px;
      font-weight: 500;
    }

    .supplier-table {
      width: 100%;
      border-collapse: collapse;
      margin-bottom: 20px;
    }

    .supplier-table th, .supplier-table td {
      border: 1px solid #dce1e7;
      padding: 10px;
      text-align: left;
      font-size: 14px;
    }

    .supplier-table th {
      background-color: #f0f4f8;
      color: #333;
    }

    .footer {
      text-align: center;
      font-size: 13px;
      color: #999;
    }
  </style>
</body>
</html>
