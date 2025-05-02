package eval.newApp.controller;

import eval.newApp.modele.purchaseOrder.PurchaseOrderDTO;
import eval.newApp.modele.purchaseOrder.PurchaseOrderWithInvoicesDTO;
import eval.newApp.modele.supplier.invoice.PurchaseInvoiceDTO;
import eval.newApp.service.PurchaseOrderService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class PurchaseOderController
{
    @Autowired
    PurchaseOrderService purchaseOrderService;


    @GetMapping("/liste-orders")
    public ModelAndView getListPurchaseOrder(@RequestParam("supplier") String supplier, HttpSession session)
    {
        ModelAndView mv=new ModelAndView("liste-purchase-order");
        try {
            String sid=null;
            if(session.getAttribute("token")==null)
            {
                throw new Exception("pas de session valide");
            }
            sid=session.getAttribute("token").toString();
            List<PurchaseOrderWithInvoicesDTO> purchaseOrderWithInvoicesDTOS=purchaseOrderService.getPurchaseOrdersWithInvoices(sid,supplier);
            mv.addObject("orders",purchaseOrderWithInvoicesDTOS);
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
