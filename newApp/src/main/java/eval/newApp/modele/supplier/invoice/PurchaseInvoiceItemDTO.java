package eval.newApp.modele.supplier.invoice;

// PurchaseInvoiceItemDTO.java
public class PurchaseInvoiceItemDTO {
    private String itemCode;

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getQty() {
        return qty;
    }

    public void setQty(double qty) {
        this.qty = qty;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

    public String getPurchaseOrder() {
        return purchaseOrder;
    }

    public void setPurchaseOrder(String purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
    }

    public String getPurchaseReceipt() {
        return purchaseReceipt;
    }

    public void setPurchaseReceipt(String purchaseReceipt) {
        this.purchaseReceipt = purchaseReceipt;
    }

    private String itemName;
    private String description;

    public PurchaseInvoiceItemDTO(){}
    public PurchaseInvoiceItemDTO(String itemCode, String itemName, String description, double qty, double rate, double amount, String uom, String purchaseOrder, String purchaseReceipt) {
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.description = description;
        this.qty = qty;
        this.rate = rate;
        this.amount = amount;
        this.uom = uom;
        this.purchaseOrder = purchaseOrder;
        this.purchaseReceipt = purchaseReceipt;
    }

    private double qty;
    private double rate;
    private double amount;
    private String uom;
    private String purchaseOrder;
    private String purchaseReceipt;


}

