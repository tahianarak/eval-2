package eval.newApp.modele.supplier.invoice;

import java.util.List;

public class PurchaseInvoiceWithItemsDTO {
    private PurchaseInvoiceDTO invoice;
    private List<PurchaseInvoiceItemDTO> items;

    // Getters / Setters
    public PurchaseInvoiceDTO getInvoice() {
        return invoice;
    }

    public void setInvoice(PurchaseInvoiceDTO invoice) {
        this.invoice = invoice;
    }

    public List<PurchaseInvoiceItemDTO> getItems() {
        return items;
    }

    public void setItems(List<PurchaseInvoiceItemDTO> items) {
        this.items = items;
    }
}
