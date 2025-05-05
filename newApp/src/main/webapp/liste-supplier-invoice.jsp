<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="eval.newApp.modele.*" %>
<%@ page import="eval.newApp.modele.supplier.invoice.PurchaseInvoiceDTO" %>
<!DOCTYPE html>
<html lang="fr">
<head>
  <meta charset="UTF-8">
  <title>Liste des Factures Fournisseur - ERPStyle</title>
  <meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<body>
<jsp:include page="sidebar.jsp" />

<div class="list-box">
  <h2>Factures Fournisseurs</h2>

  <table class="supplier-table">
    <thead>
      <tr>
        <th>Nom</th>
        <th>Statut</th>
        <th>Fournisseur</th>
        <th>Date</th>
        <th>Total</th>
        <th>Restant à payer</th>
        <th>Action</th>
      </tr>
    </thead>
    <tbody>
      <%
        List<PurchaseInvoiceDTO> invoices = (List<PurchaseInvoiceDTO>) request.getAttribute("invoices");
        if (invoices != null && !invoices.isEmpty()) {
          for (PurchaseInvoiceDTO invoice : invoices) {
      %>
      <tr>
        <td><%= invoice.getName() %></td>
        <td><%= invoice.getStatus() %></td>
        <td><%= invoice.getSupplier() %></td>
        <td><%= invoice.getPostingDate() != null ? new java.text.SimpleDateFormat("yyyy-MM-dd").format(invoice.getPostingDate()) : "" %></td>
        <td><%= FormatUtil.formaterMontant( invoice.getGrandTotal()) %> €</td>
        <td><%= FormatUtil.formaterMontant( invoice.getOutstandingAmount()) %> €</td>
        <td>
          <% if(!invoice.getStatus().equals("Paid") && !invoice.getStatus().equals("Draft")) { %>
            <a href="<%= request.getContextPath() %>/payer-facture?facture=<%= invoice.getName() %>" style="text-decoration: none;">
              <button class="full-width-btn">Payer en totalité</button>
            </a>
          <% } %>
          <a href="<%= request.getContextPath() %>/pdf-facture?facture=<%= invoice.getName() %>" style="text-decoration: none;">
               <button class="full-width-btn">exporter pdf</button>
          </a>

        </td>
      </tr>
      <%
          }
        } else {
      %>
      <tr>
        <td colspan="7">Aucune facture trouvée.</td>
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
    max-width: 1000px;
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

  .full-width-btn {
    display: block;
    width: 100%;
    padding: 12px;
    background-color: #3498db;
    color: white;
    text-align: center;
    font-size: 16px;
    border: none;
    border-radius: 5px;
    cursor: pointer;
    text-decoration: none;
    transition: background-color 0.3s ease;
  }

  .full-width-btn:hover {
    background-color: #2980b9;
  }

  .footer {
    text-align: center;
    font-size: 13px;
    color: #999;
    margin-top: 20px;
  }
  a .full-width-btn, a.full-width-btn {
    text-decoration: none;
  }
</style>

</body>
</html>
