package eval.newApp.modele.supplier.invoice;


import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Cell;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class PurchaseInvoicePdfGenerator {

    public static byte[] generatePdf(PurchaseInvoiceWithItemsDTO purchaseInvoiceWithItems) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(outputStream);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        // Titre
        Paragraph title = new Paragraph("Facture d'Achat")
                .setTextAlignment(com.itextpdf.layout.properties.TextAlignment.CENTER)
                .setFontSize(18);
        document.add(title);

        // Informations sur la facture
        Table table = new Table(2); // 2 colonnes pour l'étiquette et la valeur
        table.setWidth(100);

        // Ajout des informations de la facture
        addCell(table, "Numéro de la Facture:", purchaseInvoiceWithItems.getInvoice().getName());
        addCell(table, "Statut:", purchaseInvoiceWithItems.getInvoice().getStatus());
        addCell(table, "Fournisseur:", purchaseInvoiceWithItems.getInvoice().getSupplier());
        addCell(table, "Date de publication:", purchaseInvoiceWithItems.getInvoice().getPostingDate().toString());
        addCell(table, "Total de la Facture:", String.valueOf(purchaseInvoiceWithItems.getInvoice().getGrandTotal()));
        addCell(table, "Montant restant:", String.valueOf(purchaseInvoiceWithItems.getInvoice().getOutstandingAmount()));

        document.add(table);

        // Section des items de la facture
        Paragraph itemsTitle = new Paragraph("Détails des Items")
                .setTextAlignment(com.itextpdf.layout.properties.TextAlignment.LEFT)
                .setFontSize(16);
        document.add(itemsTitle);

        // Table pour les items de la facture
        Table itemsTable = new Table(4); // 4 colonnes pour chaque info sur l'item
        itemsTable.setWidth(100);

        // Ajout des en-têtes de la table
        addHeaderCell(itemsTable, "Code Article");
        addHeaderCell(itemsTable, "Nom de l'Article");
        addHeaderCell(itemsTable, "Quantité");
        addHeaderCell(itemsTable, "Prix Unitaire");

        // Ajout des items
        for (PurchaseInvoiceItemDTO item : purchaseInvoiceWithItems.getItems()) {
            addCell(itemsTable, item.getItemCode());
            addCell(itemsTable, item.getItemName());
            addCell(itemsTable, String.valueOf(item.getQty()));
            addCell(itemsTable, String.valueOf(item.getRate()));
        }

        document.add(itemsTable);
        document.close();

        return outputStream.toByteArray();
    }

    private static void addCell(Table table, String value) {
        Cell cell = new Cell().add(new Paragraph(value))
                .setTextAlignment(com.itextpdf.layout.properties.TextAlignment.LEFT)
                .setVerticalAlignment(com.itextpdf.layout.properties.VerticalAlignment.MIDDLE);
        table.addCell(cell);
    }

    private static void addCell(Table table, String label, String value) {
        Cell labelCell = new Cell().add(new Paragraph(label))
                .setTextAlignment(com.itextpdf.layout.properties.TextAlignment.RIGHT)
                .setVerticalAlignment(com.itextpdf.layout.properties.VerticalAlignment.MIDDLE);
        Cell valueCell = new Cell().add(new Paragraph(value))
                .setTextAlignment(com.itextpdf.layout.properties.TextAlignment.LEFT)
                .setVerticalAlignment(com.itextpdf.layout.properties.VerticalAlignment.MIDDLE);

        table.addCell(labelCell);
        table.addCell(valueCell);
    }

    private static void addHeaderCell(Table table, String header) {
        Cell headerCell = new Cell().add(new Paragraph(header))
                .setTextAlignment(com.itextpdf.layout.properties.TextAlignment.CENTER)
                .setBold()
                .setVerticalAlignment(com.itextpdf.layout.properties.VerticalAlignment.MIDDLE);
        table.addCell(headerCell);
    }
}
