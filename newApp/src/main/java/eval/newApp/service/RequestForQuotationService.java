package eval.newApp.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import eval.newApp.modele.RequestQuotation.RequestForQuotationDTO;
import eval.newApp.modele.RequestQuotation.RequestForQuotationItemDTO;
import eval.newApp.modele.supplier.SupplierQuotation;
import eval.newApp.modele.supplier.SupplierQuotationItemDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.*;

@Service
public class RequestForQuotationService {


    private RestTemplate restTemplate=new RestTemplate();


    private ObjectMapper objectMapper=new ObjectMapper();

    @Value("${erpnext.url}")
    private  String baseUrl;

    public SupplierQuotation createSupplierQuotation(String sid, String supplier, List<RequestForQuotationItemDTO> items) throws Exception {
        // Préparer les données pour créer le Supplier Quotation
        Map<String, Object> quotationData = new HashMap<>();
        quotationData.put("supplier", supplier);
        quotationData.put("transaction_date", "2025-05-01");

        List<Map<String, Object>> itemsData = new ArrayList<>();
        double total = 0;

        for (RequestForQuotationItemDTO itemDTO : items) {
            Map<String, Object> itemMap = new HashMap<>();
            itemMap.put("item_code", itemDTO.getItemCode());
            itemMap.put("item_name", itemDTO.getItemName());
            itemMap.put("description", itemDTO.getDescription());
            itemMap.put("qty", itemDTO.getQty());
            itemMap.put("rate", itemDTO.getRate());
            itemMap.put("uom", itemDTO.getUom());
            double amount = itemDTO.getQty() * itemDTO.getRate();
            itemMap.put("amount", amount);
            total += amount;
            itemsData.add(itemMap);
        }

        quotationData.put("items", itemsData);
        quotationData.put("total", total);
        quotationData.put("doctype", "Supplier Quotation");

        System.out.println(quotationData.toString());

        // Envoyer la requête POST pour créer le devis
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Cookie", "sid=" + sid);

        ObjectMapper mapper = new ObjectMapper();
        String jsonPayload = mapper.writeValueAsString(quotationData);

        HttpEntity<String> requestEntity = new HttpEntity<>(jsonPayload, headers);
        String createUrl = this.baseUrl +"/api/resource/"+ "Supplier Quotation";

        ResponseEntity<String> createResponse = restTemplate.exchange(
                createUrl, HttpMethod.POST, requestEntity, String.class);

        if (!createResponse.getStatusCode().is2xxSuccessful()) {
            throw new Exception("Erreur création Supplier Quotation: " + createResponse.getBody());
        }

        // Récupérer le nom du Supplier Quotation
        JsonNode responseJson = mapper.readTree(createResponse.getBody());
        String quotationName = responseJson.path("data").path("name").asText();

        String getDocUrl = this.baseUrl + "/api/resource/Supplier Quotation/" + quotationName;
        ResponseEntity<String> getDocResponse = restTemplate.exchange(
                getDocUrl, HttpMethod.GET, new HttpEntity<>(headers), String.class
        );
        if (!getDocResponse.getStatusCode().is2xxSuccessful()) {
            throw new Exception("Erreur récupération du Supplier Quotation avant soumission");
        }

        JsonNode latestDoc = mapper.readTree(getDocResponse.getBody()).path("data");

// Étape 2 : Utiliser ce document dans submit_doc
        Map<String, Object> submitPayload = new HashMap<>();
        submitPayload.put("doc", latestDoc);

        String submitJson = mapper.writeValueAsString(submitPayload);
        HttpEntity<String> submitEntity = new HttpEntity<>(submitJson, headers);

        String submitUrl = this.baseUrl + "/api/method/frappe.client.submit";
        ResponseEntity<String> submitResponse = restTemplate.exchange(
                submitUrl, HttpMethod.POST, submitEntity, String.class
        );

        if (!submitResponse.getStatusCode().is2xxSuccessful()) {
            throw new Exception("Erreur soumission Supplier Quotation: " + submitResponse.getBody());
        }

        // Retourner un objet SupplierQuotation minimal
        SupplierQuotation result = new SupplierQuotation();
        result.setName(quotationName);
        result.setSupplier(supplier);
        result.setTransaction_date("2025-05-01");
        result.setTotal(total);
        result.setStatus("Submitted");

        return result;
    }




























    public List<RequestForQuotationItemDTO> getItemsFromRFQ(String sid, String rfqName) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", "sid=" + sid);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(headers);

        String url = baseUrl + "/api/resource/Request for Quotation/" + rfqName;

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
        if (response.getStatusCode() != HttpStatus.OK) {
            throw new Exception("Erreur récupération RFQ : " + response.getStatusCode());
        }

        JsonNode root = objectMapper.readTree(response.getBody());
        JsonNode data = root.get("data");
        JsonNode itemsNode = data.get("items");

        List<RequestForQuotationItemDTO> items = new ArrayList<>();
        for (JsonNode itemNode : itemsNode) {
            RequestForQuotationItemDTO item = new RequestForQuotationItemDTO();
            item.setRequestForQuotationId(data.path("name").asText(null)); // lien vers le RFQ
            item.setItemCode(itemNode.path("item_code").asText(null));
            item.setItemName(itemNode.path("item_name").asText(null));
            item.setDescription(itemNode.path("description").asText(null));
            item.setQty(itemNode.path("qty").asDouble(0));
            item.setUom(itemNode.path("uom").asText(null));
            item.setRate(itemNode.path("rate").asDouble(0));
            items.add(item);
        }

        return items;
    }


    public List<RequestForQuotationDTO> getRFQsBySupplier(String sid, String supplierName) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        System.out.println("sid = " + sid);
        headers.add("Cookie", "sid=" + sid);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(headers);

        // Appel API personnalisée Frappe
        String rfqUrl = UriComponentsBuilder
                .fromHttpUrl(baseUrl + "/api/method/ma_app.api.request_quotation_api.get_rfq_by_supplier")
                .queryParam("supplier_name", supplierName)
                .toUriString();

        ResponseEntity<String> rfqResponse = restTemplate.exchange(rfqUrl, HttpMethod.GET, request, String.class);
        if (rfqResponse.getStatusCode() != HttpStatus.OK) {
            throw new Exception("Erreur récupération RFQs : " + rfqResponse.getStatusCode());
        }

        // Parsing manuel des données
        JsonNode root = objectMapper.readTree(rfqResponse.getBody());
        JsonNode rfqData = root.get("message");

        List<RequestForQuotationDTO> rfqs = new ArrayList<>();
        for (JsonNode rfqNode : rfqData) {
            RequestForQuotationDTO rfq = new RequestForQuotationDTO();
            rfq.setName(rfqNode.path("name").asText(null));
            rfq.setStatus(rfqNode.path("status").asText(null));
            rfq.setTransactionDate(objectMapper.treeToValue(rfqNode.path("transaction_date"), Date.class));
            rfq.setScheduleDate(objectMapper.treeToValue(rfqNode.path("schedule_date"), Date.class));
            rfqs.add(rfq);
        }

        return rfqs;
    }
}

