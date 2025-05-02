package eval.newApp.modele.purchaseOrder;


import java.sql.Date;

public class PurchaseOrderDTO {
    private String name;
    private String status;
    private String supplierName;
    private Date transactionDate;
    private double grandTotal;

    // Getters & Setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        if(status.equals("To Bill"))
        {
            this.status="Recu";
        }
        else
        {
            this.status = status;
        }

    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public double getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(double grandTotal) {
        this.grandTotal = grandTotal;
    }
}
