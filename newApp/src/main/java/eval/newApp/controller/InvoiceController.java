package eval.newApp.controller;

import eval.newApp.modele.supplier.invoice.PurchaseInvoiceDTO;
import eval.newApp.service.InvoiceService;
import jakarta.jws.WebParam;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class InvoiceController {

    @Autowired
    InvoiceService invoiceService;

    @GetMapping("/payer-facture")
    public ModelAndView payerFacture(@RequestParam("facture") String facture,HttpSession session)
    {

        try {
            String sid=null;
            if(session.getAttribute("token")==null)
            {
                throw new Exception("pas de session valide");
            }
            sid=session.getAttribute("token").toString();
            invoiceService.payPurchaseInvoice(sid,facture);
            return getListeFacture(session);

        }
        catch (Exception e)
        {
            e.printStackTrace();
            ModelAndView mvi=new ModelAndView("error");
            mvi.addObject("error",e.getMessage());
            return mvi;
        }
    }


    @GetMapping("/liste-supplier-invoice")
    public ModelAndView getListeFacture(HttpSession session)
    {
        ModelAndView mv=new ModelAndView("liste-supplier-invoice");
        try {
            String sid=null;
            if(session.getAttribute("token")==null)
            {
                throw new Exception("pas de session valide");
            }
            sid=session.getAttribute("token").toString();
            List<PurchaseInvoiceDTO> purchaseInvoices=invoiceService.getPaidPurchaseInvoices(sid);
            mv.addObject("invoices",purchaseInvoices);
            return mv;

        }
        catch (Exception e)
        {
            e.printStackTrace();
            ModelAndView mvi=new ModelAndView("error");
            mvi.addObject("error",e.getMessage());
            return mvi;
        }
    }

}
