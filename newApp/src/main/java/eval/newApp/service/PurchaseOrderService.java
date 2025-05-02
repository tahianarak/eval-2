package eval.newApp.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import eval.newApp.modele.purchaseOrder.PurchaseOrderDTO;
import eval.newApp.modele.purchaseOrder.PurchaseOrderWithInvoicesDTO;
import eval.newApp.modele.supplier.invoice.PurchaseInvoiceDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class PurchaseOrderService {
    private RestTemplate restTemplate=new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${erpnext.url}")
    String baseUrl;


    public List<PurchaseOrderWithInvoicesDTO> getPurchaseOrdersWithInvoices(String sid, String supplierNameFilter) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", "sid=" + sid);
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Ajout du filtre par supplier_name
        String filters = String.format("[[\"supplier_name\", \"=\", \"%s\"]]", supplierNameFilter);

        String url = baseUrl + "/api/resource/Purchase Order"
                + "?fields=[\"name\", \"status\", \"supplier_name\", \"transaction_date\", \"grand_total\"]"
                + "&filters=" + filters;

        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            JsonNode root = objectMapper.readTree(response.getBody());
            JsonNode data = root.get("data");

            List<PurchaseOrderWithInvoicesDTO> purchaseOrderList = new ArrayList<>();

            for (JsonNode poNode : data) {
                String purchaseOrderName = poNode.path("name").asText();
                String supplierName = poNode.path("supplier_name").asText();
                String status = poNode.path("status").asText();
                String transactionDate = poNode.path("transaction_date").asText();
                double grandTotal = poNode.path("grand_total").asDouble();

                // Récupérer les Purchase Invoices liées à ce Purchase Order
                List<PurchaseInvoiceDTO> invoices = getPurchaseInvoicesForOrder(purchaseOrderName, sid);

                PurchaseOrderWithInvoicesDTO purchaseOrderWithInvoices = new PurchaseOrderWithInvoicesDTO();
                purchaseOrderWithInvoices.setPurchaseOrderName(purchaseOrderName);
                purchaseOrderWithInvoices.setSupplierName(supplierName);
                purchaseOrderWithInvoices.setStatus(status);
                purchaseOrderWithInvoices.setTransactionDate(transactionDate);
                purchaseOrderWithInvoices.setGrandTotal(grandTotal);
                purchaseOrderWithInvoices.setInvoices(invoices);

                purchaseOrderList.add(purchaseOrderWithInvoices);
            }

            return purchaseOrderList;
        } else {
            throw new Exception("Erreur lors de la récupération des Purchase Orders : " + response.getStatusCode());
        }
    }


    private List<PurchaseInvoiceDTO> getPurchaseInvoicesForOrder(String purchaseOrderName, String sid) throws Exception {
        // URL pour récupérer les Purchase Invoices liées au Purchase Order
        String invoiceUrl = baseUrl + "/api/method/ma_app.api.customer_api.get_purchase_invoices_by_order?purchase_order=" + purchaseOrderName;


        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", "sid=" + sid);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(invoiceUrl, HttpMethod.GET, request, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            JsonNode root = objectMapper.readTree(response.getBody());
            JsonNode data = root.get("message");

            List<PurchaseInvoiceDTO> invoices = new ArrayList<>();

            for (JsonNode invoiceNode : data) {
                String invoiceName = invoiceNode.path("name").asText();
                double outstandingAmount = invoiceNode.path("outstanding_amount").asDouble();
                double grandTotal = invoiceNode.path("grand_total").asDouble();

                PurchaseInvoiceDTO invoice = new PurchaseInvoiceDTO();
                invoice.setName(invoiceName);
                invoice.setOutstandingAmount(outstandingAmount);
                invoice.setGrandTotal(grandTotal);
                invoices.add(invoice);
            }

            return invoices;
        } else {
            throw new Exception("Erreur lors de la récupération des Purchase Invoices : " + response.getStatusCode());
        }
    }







    public List<PurchaseOrderDTO> getPurchaseOrders(String sid) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", "sid=" + sid);
        headers.setContentType(MediaType.APPLICATION_JSON);

        String resource = "Purchase Order";
        String url = baseUrl + "/api/resource/" + resource +
                "?fields=[\"name\",\"status\",\"supplier_name\",\"transaction_date\",\"grand_total\"]";

        System.out.println("URL: " + url);

        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            JsonNode root = objectMapper.readTree(response.getBody());
            JsonNode data = root.get("data");

            List<PurchaseOrderDTO> orders = new ArrayList<>();
            for (JsonNode orderNode : data) {
                PurchaseOrderDTO order = new PurchaseOrderDTO();
                order.setName(orderNode.path("name").asText(null));
                order.setStatus(orderNode.path("status").asText(null));
                order.setSupplierName(orderNode.path("supplier_name").asText(null));
                order.setTransactionDate(objectMapper.treeToValue(orderNode.path("transaction_date"), java.sql.Date.class));
                order.setGrandTotal(orderNode.path("grand_total").asDouble(0));

                orders.add(order);
            }
            return orders;
        } else {
            throw new Exception("Échec de la récupération des Purchase Orders : " + response.getStatusCode());
        }
    }

}
