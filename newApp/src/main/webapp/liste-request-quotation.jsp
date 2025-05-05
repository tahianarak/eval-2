<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="eval.newApp.modele.RequestQuotation.RequestForQuotationDTO" %>
<!DOCTYPE html>
<html lang="fr">
<head>
  <meta charset="UTF-8">
  <title>Liste des RFQ du Fournisseur - ERPStyle</title>
  <meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<body>
<jsp:include page="sidebar.jsp" />

<div class="list-box">
  <h2>Demandes de prix pour le fournisseur</h2>

  <table class="quotation-table">
    <thead>
      <tr>
        <th>Code</th>
        <th>Date</th>
        <th>Échéance</th>
        <th>Statut</th>
        <th>Action</th>
      </tr>
    </thead>
    <tbody>
      <%
        List<RequestForQuotationDTO> rfqs = (List<RequestForQuotationDTO>) request.getAttribute("request");
        if (rfqs != null && !rfqs.isEmpty()) {
          for (RequestForQuotationDTO rfq : rfqs) {
      %>
      <tr>
        <td><%= rfq.getName() %></td>
        <td><%= rfq.getTransactionDate() %></td>
        <td><%= rfq.getScheduleDate() %></td>
        <td><%= rfq.getStatus() %></td>
        <td>
          <a href="<%= request.getContextPath() %>/liste-request-quotation-item?request=<%= rfq.getName() %>">
            <button>Voir les détails</button>
          </a>
        </td>
      </tr>
      <%
          }
        } else {
      %>
      <tr>
        <td colspan="5">Aucune demande de prix trouvée pour ce fournisseur.</td>
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

  button {
    background-color: #4CAF50;
    color: white;
    padding: 10px 20px;
    border: none;
    border-radius: 5px;
    cursor: pointer;
    text-align: center;
  }

  button:hover {
    background-color: #45a049;
  }
</style>

</body>
</html>
