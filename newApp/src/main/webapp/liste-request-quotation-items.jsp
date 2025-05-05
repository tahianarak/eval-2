<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="eval.newApp.modele.RequestQuotation.RequestForQuotationItemDTO" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Répondre à un RFQ - ERPStyle</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<body>
<jsp:include page="sidebar.jsp" />

<div class="list-box">
    <h2>Réponse au Request for Quotation</h2>

    <form method="post" action="<%= request.getContextPath() %>/creerSupplierQuotation">
        <table class="quotation-table">
            <thead>
            <tr>
                <th>Code Article</th>
                <th>Nom</th>
                <th>Description</th>
                <th>Quantité</th>
                <th>Unité</th>
                <th>Prix Proposé (€)</th>
            </tr>
            </thead>
            <tbody>
            <%
                List<RequestForQuotationItemDTO> items = (List<RequestForQuotationItemDTO>) request.getAttribute("items");
                if (items != null && !items.isEmpty()) {
                    for (int i = 0; i < items.size(); i++) {
                        RequestForQuotationItemDTO item = items.get(i);
            %>
            <tr>
                <td><%= item.getItemCode() %>
                    <input type="hidden" name="items[<%= i %>].itemCode" value="<%= item.getItemCode() %>" />
                </td>
                <td><%= item.getItemName() %>
                    <input type="hidden" name="items[<%= i %>].itemName" value="<%= item.getItemName() %>" />
                </td>
                <td><%= item.getDescription() %>
                    <input type="hidden" name="items[<%= i %>].description" value="<%= item.getDescription() %>" />
                </td>
                <td><%= item.getQty() %>
                    <input type="hidden" name="items[<%= i %>].qty" value="<%= item.getQty() %>" />
                </td>
                <td><%= item.getUom() %>
                    <input type="hidden" name="items[<%= i %>].uom" value="<%= item.getUom() %>" />
                </td>
                <td>
                    <input type="number" name="items[<%= i %>].rate" step="0.01" required />
                </td>
            </tr>
            <%
                    }
                } else {
            %>
            <tr>
                <td colspan="6">Aucun article trouvé pour ce RFQ.</td>
            </tr>
            <%
                }
            %>
            </tbody>
        </table>

        <div style="text-align: center; margin-top: 20px;">
            <button type="submit">Soumettre le devis</button>
        </div>
    </form>

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
        padding: 10px 25px;
        border: none;
        border-radius: 5px;
        font-size: 16px;
        cursor: pointer;
    }

    button:hover {
        background-color: #45a049;
    }

    input[type="number"] {
        width: 100px;
        padding: 5px;
    }
</style>

</body>
</html>
