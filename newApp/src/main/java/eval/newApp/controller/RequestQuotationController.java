package eval.newApp.controller;

import eval.newApp.modele.RequestQuotation.RequestForQuotationDTO;
import eval.newApp.modele.RequestQuotation.RequestForQuotationItemDTO;
import eval.newApp.modele.purchaseOrder.PurchaseOrderWithInvoicesDTO;
import eval.newApp.modele.supplier.SupplierQuotation;
import eval.newApp.service.RequestForQuotationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class RequestQuotationController
{
    @Autowired
    RequestForQuotationService requestForQuotationService;




    @PostMapping("/creerSupplierQuotation")
    public ModelAndView creerSupplierQuotation(HttpServletRequest request, HttpSession session) {
        try {
            // Vérifie la session
            if (session.getAttribute("token") == null) {
                throw new Exception("Pas de session valide");
            }
            String sid = session.getAttribute("token").toString();

            // Récupère les paramètres globaux
            String rfqId = request.getParameter("rfqId");
            String supplierId = (String) session.getAttribute("supplier");

            // Construction des items à partir des paramètres du formulaire
            List<RequestForQuotationItemDTO> items = new ArrayList<>();

            int index = 0;
            while (true) {
                String prefix = "items[" + index + "]";
                String itemCode = request.getParameter(prefix + ".itemCode");
                if (itemCode == null) break; // plus d’éléments

                RequestForQuotationItemDTO item = new RequestForQuotationItemDTO();
                item.setItemCode(itemCode);
                item.setItemName(request.getParameter(prefix + ".itemName"));
                item.setDescription(request.getParameter(prefix + ".description"));
                item.setQty(Double.parseDouble(request.getParameter(prefix + ".qty")));
                item.setUom(request.getParameter(prefix + ".uom"));
                item.setRate(Double.parseDouble(request.getParameter(prefix + ".rate")));

                items.add(item);
                index++;
            }

            // Appel du service
            SupplierQuotation quotation = requestForQuotationService.createSupplierQuotation(
                    sid, supplierId, items);

            ModelAndView mv = new ModelAndView("accueil");

            return mv;

        } catch (Exception e) {
            e.printStackTrace();
            ModelAndView error = new ModelAndView("error");
            error.addObject("error", e.getMessage());
            return error;
        }
    }





    @GetMapping("/liste-request-quotation")
    public ModelAndView getLRQ(@RequestParam("supplier") String supplier, HttpSession session){
        ModelAndView mv=new ModelAndView("liste-request-quotation");
        try {
            String sid=null;
            if(session.getAttribute("token")==null)
            {
                throw new Exception("pas de session valide");
            }
            session.setAttribute("supplier",supplier);
            sid=session.getAttribute("token").toString();
           List<RequestForQuotationDTO> requestForQuotationDTOS=requestForQuotationService.getRFQsBySupplier(sid,supplier);
            mv.addObject("request",requestForQuotationDTOS);
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

    @GetMapping("/liste-request-quotation-item")
    public ModelAndView getLRQI(@RequestParam("request") String request, HttpSession session){
        ModelAndView mv=new ModelAndView("liste-request-quotation-items");
        try {
            String sid=null;
            if(session.getAttribute("token")==null)
            {
                throw new Exception("pas de session valide");
            }
            sid=session.getAttribute("token").toString();
            List<RequestForQuotationItemDTO> requestForQuotationDTOS=requestForQuotationService.getItemsFromRFQ(sid,request);
            mv.addObject("items",requestForQuotationDTOS);
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
