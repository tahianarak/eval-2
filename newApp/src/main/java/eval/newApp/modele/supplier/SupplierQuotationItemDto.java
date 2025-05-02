package eval.newApp.modele.supplier;

public class SupplierQuotationItemDto {


    public SupplierQuotationItemDto(double quantity, double unitPrice, String supplierName, String supplierQuotationName, String itemName) {
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.supplierName = supplierName;
        this.supplierQuotationName = supplierQuotationName;
        this.itemName = itemName;
    }

    public  SupplierQuotationItemDto(){}

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private   String id;
    private  String description;
    private double quantity;
    private double unitPrice;
    private String supplierName;
    private String supplierQuotationName;

    private String itemName;



    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getSupplierQuotationName() {
        return supplierQuotationName;
    }

    public void setSupplierQuotationName(String supplierQuotationName) {
        this.supplierQuotationName = supplierQuotationName;
    }

    // Getters & Setters
}
