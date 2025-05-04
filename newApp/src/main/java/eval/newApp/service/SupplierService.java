package eval.newApp.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import eval.newApp.modele.supplier.Supplier;
import eval.newApp.modele.supplier.SupplierQuotation;
import eval.newApp.modele.supplier.SupplierQuotationItemDto;
import eval.newApp.modele.supplier.SupplierResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class SupplierService {

    private RestTemplate restTemplate=new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${erpnext.url}")
    String baseUrl;


    public void updateItemPriceInQuotation(String sid, String quotationName, String itemName, double newRate) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", "sid=" + sid);
        headers.setContentType(MediaType.APPLICATION_JSON);

        String url = baseUrl + "/api/resource/" + "Supplier Quotation Item"
                + "/" + itemName;

        String jsonBody = String.format(Locale.US, "{\"rate\": %.2f}", newRate);
        System.out.println(jsonBody);


        HttpEntity<String> request = new HttpEntity<>(jsonBody, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, request, String.class);

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new Exception("Erreur lors de la mise à jour : " + response.getStatusCode() + " -> " + response.getBody());
        }
    }






    public List<SupplierQuotationItemDto> getItemsFromSupplierQuotation(String sid, String quotationName) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", "sid=" + sid);
        headers.setContentType(MediaType.APPLICATION_JSON);

        String resource = "Supplier Quotation";
        String url = baseUrl + "/api/resource/" + resource
                + "/" + quotationName;

        System.out.println("url:"+url);

        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            JsonNode root = objectMapper.readTree(response.getBody());
            JsonNode data = root.get("data");

            String supplierName = data.path("supplier").asText(null);
            String supplierQuotationName = data.path("name").asText(null);
            JsonNode itemsNode = data.path("items");

            List<SupplierQuotationItemDto> itemList = new ArrayList<>();
            for (JsonNode itemNode : itemsNode) {
                SupplierQuotationItemDto item = new SupplierQuotationItemDto();
                item.setItemName(itemNode.path("item_name").asText(null));
                item.setQuantity(itemNode.path("qty").asDouble(0));
                item.setUnitPrice(itemNode.path("rate").asDouble(0));
                item.setDescription(itemNode.path("description").asText(null));
                item.setId(itemNode.path("name").asText(null));
                item.setSupplierName(supplierName);
                item.setSupplierQuotationName(supplierQuotationName);
                itemList.add(item);
            }
            return itemList;
        } else {
            throw new Exception("Échec de la récupération du Supplier Quotation : " + response.getStatusCode());
        }
    }





    public List<SupplierQuotation> getQuotationsBySupplier(String sid, String supplierName) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", "sid=" + sid);
        headers.setContentType(MediaType.APPLICATION_JSON);


        // Paramètres de la requête
        String resource = "Supplier Quotation";
        String filters = "[[\"supplier\", \"=\", \"" + supplierName + "\"]]";  // Format JSON pour le filtre
        String fields = "[\"name\", \"supplier\", \"transaction_date\", \"total\",\"status\"]";  // Format JSON pour les champs

        // Construction de l'URL complète (sans l'encodage manuel de tous les paramètres)
        String url = String.format("%sapi/resource/%s?filters=%s&fields=%s",
                baseUrl, resource, filters, fields);

        // Affichage de l'URL générée
        System.out.println("URL générée : " + url);

        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            JsonNode root = objectMapper.readTree(response.getBody());
            JsonNode dataNode = root.get("data");

            List<SupplierQuotation> quotations = new ArrayList<>();
            for (JsonNode node : dataNode) {
                SupplierQuotation sq = new SupplierQuotation();
                sq.setName(node.path("name").asText(null));
                sq.setSupplier(node.path("supplier").asText(null));
                sq.setTransaction_date(node.path("transaction_date").asText(null));
                sq.setTotal(node.path("total").asDouble(0.0));
                sq.setStatus(node.path("status").asText(null));
                quotations.add(sq);
            }
            return quotations;
        } else {
            throw new Exception("Échec de la récupération des Supplier Quotation : " + response.getStatusCode());
        }
    }
    public List<Supplier> getSuppliers(String sid) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", "sid=" + sid);
        headers.setContentType(MediaType.APPLICATION_JSON);

        String resource = "Supplier";
        String fieldsParam = "[\"*\"]";
        String url = baseUrl + "/api/resource/" + resource + "?fields=" + fieldsParam;

        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            JsonNode root = objectMapper.readTree(response.getBody());
            JsonNode dataNode = root.get("data");

            List<Supplier> suppliers = new ArrayList<>();
            for (JsonNode node : dataNode) {
                Supplier supplier = new Supplier();
                supplier.setName(node.path("name").asText(null));
                supplier.setSupplierName(node.path("supplier_name").asText(null));
                supplier.setSupplierGroup(node.path("supplier_group").asText(null));
                supplier.setSupplierType(node.path("supplier_type").asText(null));
                supplier.setCountry(node.path("country").asText(null));
                supplier.setLanguage(node.path("language").asText(null));
                supplier.setEmailId(node.path("email_id").asText(null));
                supplier.setMobileNo(node.path("mobile_no").asText(null));
                supplier.setPrimaryAddress(node.path("primary_address").asText(null));

                suppliers.add(supplier);
            }
            return suppliers;
        } else {
            throw new Exception("Échec de la récupération des fournisseurs : " + response.getStatusCode());
        }
    }

}
