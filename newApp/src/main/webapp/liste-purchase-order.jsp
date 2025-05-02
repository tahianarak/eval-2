<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="eval.newApp.modele.purchaseOrder.PurchaseOrderWithInvoicesDTO" %>
<!DOCTYPE html>
<html lang="fr">
<head>
  <meta charset="UTF-8">
  <title>Liste des Bon de Commande - ERPStyle</title>
  <meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<body>
<jsp:include page="sidebar.jsp" />
  <div class="list-box">
    <h2>Bons de commande</h2>

    <table class="supplier-table">
      <thead>
        <tr>
          <th>Référence</th>
          <th>Fournisseur</th>
          <th>Statut</th>
          <th>Date</th>
          <th>Total</th>
          <th>recu</th>
          <th>paye</th>
        </tr>
      </thead>
      <tbody>
        <%
          List<PurchaseOrderWithInvoicesDTO> orders = (List<PurchaseOrderWithInvoicesDTO>) request.getAttribute("orders");
          if (orders != null && !orders.isEmpty()) {
              for (PurchaseOrderWithInvoicesDTO order : orders) {
        %>
        <tr>
          <td><%= order.getPurchaseOrderName() %></td>
          <td><%= order.getSupplierName() %></td>
          <td><%= order.getStatus() %></td>
          <td><%= order.getTransactionDate() %></td>
          <td><%= order.getGrandTotal() %> €</td>
          <td><%= order.isRecu() %></td>
          <td><%= order.isPaid() %> </td>
        </tr>
        <%
              }
          } else {
        %>
        <tr>
          <td colspan="5">Aucun bon de commande trouvé.</td>
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
