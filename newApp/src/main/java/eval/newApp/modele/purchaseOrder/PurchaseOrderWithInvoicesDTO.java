package eval.newApp.modele.purchaseOrder;

import eval.newApp.modele.supplier.invoice.PurchaseInvoiceDTO;

import java.util.List;

public class PurchaseOrderWithInvoicesDTO {
    private String purchaseOrderName;
    private String supplierName;
    private String status;
    private String transactionDate;
    private double grandTotal;
    private List<PurchaseInvoiceDTO> invoices;



    public String isPaid()
    {
        double ans=grandTotal;
        for (PurchaseInvoiceDTO invoiceDTO:invoices)
        {
            if(invoiceDTO.getOutstandingAmount()!=0)
            {
                return "non";
            }
            ans=ans-invoiceDTO.getGrandTotal();
        }
        if(ans==0)
        {
            return "oui";
        }
        return "non";
    }

    public String isRecu()
    {
        if(this.status.equals("To Bill") || this.status.equals("Completed"))
        {
            return  "oui";
        }
        return "non";
    }



    // Getters and Setters
    public String getPurchaseOrderName() {
        return purchaseOrderName;
    }

    public void setPurchaseOrderName(String purchaseOrderName) {
        this.purchaseOrderName = purchaseOrderName;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public double getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(double grandTotal) {
        this.grandTotal = grandTotal;
    }

    public List<PurchaseInvoiceDTO> getInvoices() {
        return invoices;
    }

    public void setInvoices(List<PurchaseInvoiceDTO> invoices) {
        this.invoices = invoices;
    }
}

