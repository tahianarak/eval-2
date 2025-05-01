<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="eval.newApp.modele.supplier.SupplierQuotation" %>
<!DOCTYPE html>
<html lang="fr">
<head>
  <meta charset="UTF-8">
  <title>Liste des Devis Fournisseur - ERPStyle</title>
  <meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<body>
<jsp:include page="sidebar.jsp" />

<div class="list-box">
  <h2>Devis du fournisseur</h2>

  <table class="quotation-table">
    <thead>
      <tr>
        <th>Code</th>
        <th>Fournisseur</th>
        <th>Date</th>
        <th>Total (€)</th>
        <th>action</th>
      </tr>
    </thead>
    <tbody>
      <%
        List<SupplierQuotation> quotations = (List<SupplierQuotation>) request.getAttribute("quotations");
        if (quotations != null && !quotations.isEmpty()) {
          for (SupplierQuotation quotation : quotations) {
      %>
      <tr>
        <td><%= quotation.getName() %></td>
        <td><%= quotation.getSupplier() %></td>
        <td><%= quotation.getTransaction_date() %></td>
        <td><%= quotation.getTotal() %></td>
        <td><a href="<%= request.getContextPath() %>/listeSupplierQuotationItems?quotation=<%= quotation.getName() %>"><button>voir les details</button></a></td>
      </tr>
      <%
          }
        } else {
      %>
      <tr>
        <td colspan="4">Aucun devis trouvé pour ce fournisseur.</td>
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

  .quotation-table {
    width: 100%;
    border-collapse: collapse;
    margin-bottom: 20px;
  }

  .quotation-table th, .quotation-table td {
    border: 1px solid #dce1e7;
    padding: 10px;
    text-align: left;
    font-size: 14px;
  }

  .quotation-table th {
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
