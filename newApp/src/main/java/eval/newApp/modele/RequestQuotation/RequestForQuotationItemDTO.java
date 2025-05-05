package eval.newApp.modele.RequestQuotation;




public class RequestForQuotationItemDTO {
    private String requestForQuotationId; // <-- nouvel attribut
    private String itemCode;
    private String itemName;
    private String description;
    private double qty;
    private String uom;
    private double rate;

    // Getters / Setters
    public String getRequestForQuotationId() {
        return requestForQuotationId;
    }

    public void setRequestForQuotationId(String requestForQuotationId) {
        this.requestForQuotationId = requestForQuotationId;
    }

    public String getItemCode() { return itemCode; }
    public void setItemCode(String itemCode) { this.itemCode = itemCode; }

    public String getItemName() { return itemName; }
    public void setItemName(String itemName) { this.itemName = itemName; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getQty() { return qty; }
    public void setQty(double qty) { this.qty = qty; }

    public String getUom() { return uom; }
    public void setUom(String uom) { this.uom = uom; }

    public double getRate() { return rate; }
    public void setRate(double rate) { this.rate = rate; }
}


