<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="eval.newApp.modele.supplier.SupplierQuotationItemDto" %>
<!DOCTYPE html>
<html lang="fr">
<head>
  <meta charset="UTF-8">
  <title>Items du Devis Fournisseur</title>
  <meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<body>
<jsp:include page="sidebar.jsp" />
  <div class="list-box">
    <h2>Items du Devis Fournisseur</h2>

    <table class="supplier-table">
      <thead>
        <tr>
          <th>id article</th>
          <th>Nom de l’article</th>
          <th>Quantité</th>
          <th>Prix unitaire</th>
          <th>Description</th>
          <th>Devis fournisseur</th>
          <th>Fournisseur</th>
          <th>action</th>
        </tr>
      </thead>
      <tbody>
        <%
          List<SupplierQuotationItemDto> items = (List<SupplierQuotationItemDto>) request.getAttribute("items");
          if (items != null && !items.isEmpty()) {
              for (SupplierQuotationItemDto item : items) {
        %>
        <tr>
        <td><%= item.getId() %></td>
          <td><%= item.getItemName() %></td>
          <td><%= item.getQuantity() %></td>
          <td><%= item.getUnitPrice() %></td>
          <td><%= item.getDescription() %></td>
          <td><%= item.getSupplierQuotationName() %></td>
          <td><%= item.getSupplierName() %></td>
          <td>
              <form method="post" action="<%= request.getContextPath() %>/update-price">
                  <input type="hidden" name="quotation" value="<%= item.getSupplierQuotationName() %>" />
                  <input type="hidden" name="item" value="<%= item.getId() %>" />
                  <input type="number" name="price" step="0.01"  required />
                  <button type="submit">Mettre à jour</button>
              </form>
          </td>
        </tr>
        <%
              }
          } else {
        %>
        <tr>
          <td colspan="5">Aucun item trouvé pour ce devis fournisseur.</td>
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
      max-width: 900px;
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
