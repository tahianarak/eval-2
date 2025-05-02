package eval.newApp.modele.supplier.invoice;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;

public class PurchaseInvoiceDTO {

    @JsonProperty("name")
    private String name;

    @JsonProperty("status")
    private String status;

    @JsonProperty("outstanding_amount")
    private double outstandingAmount;

    @JsonProperty("supplier")
    private String supplier;

    @JsonProperty("posting_date")
    private Date postingDate;

    @JsonProperty("grand_total")
    private double grandTotal;

    // Getters et setters

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

            this.status = status;
    }

    public double getOutstandingAmount() {
        return outstandingAmount;
    }

    public void setOutstandingAmount(double outstandingAmount) {
        this.outstandingAmount = outstandingAmount;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public Date getPostingDate() {
        return postingDate;
    }

    public void setPostingDate(Date postingDate) {
        this.postingDate = postingDate;
    }

    public double getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(double grandTotal) {
        this.grandTotal = grandTotal;
    }

    @Override
    public String toString() {
        return "PurchaseInvoiceDTO{" +
                "name='" + name + '\'' +
                ", status='" + status + '\'' +
                ", outstandingAmount=" + outstandingAmount +
                ", supplier='" + supplier + '\'' +
                ", postingDate=" + postingDate +
                ", grandTotal=" + grandTotal +
                '}';
    }
}
