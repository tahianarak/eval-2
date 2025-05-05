package eval.newApp.controller;

import eval.newApp.modele.supplier.Supplier;
import eval.newApp.modele.supplier.SupplierQuotation;
import eval.newApp.modele.supplier.SupplierQuotationItemDto;
import eval.newApp.service.SupplierService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class SupplierController
{
    @Autowired
    SupplierService supplierService;


    @PostMapping("/update-price")
    public ModelAndView modifierPrix(HttpSession session,@RequestParam("quotation") String quotation,@RequestParam("item") String item,@RequestParam("price") double price)
    {
        ModelAndView mv=new ModelAndView("liste-supplier-quotation-item");
        try {
            String sid=null;
            if(session.getAttribute("token")==null)
            {
                throw new Exception("pas de session valide");
            }
            sid=session.getAttribute("token").toString();
            supplierService.updateItemPriceInQuotation(sid,quotation,item,price);
            return listeSupplier(session);
        }
        catch (Exception e)
        {
            ModelAndView mvi=new ModelAndView("error");
            mvi.addObject("error",e.getMessage());
            return mvi;
        }
    }

    @GetMapping("/listeSupplierQuotationItems")
    public ModelAndView listeQuotationItem(@RequestParam("quotation") String quotation, HttpSession session) throws Exception {
        ModelAndView mv=new ModelAndView("liste-supplier-quotation-item");
        try {
            String sid=null;
            if(session.getAttribute("token")==null)
            {
                throw new Exception("pas de session valide");
            }
            sid=session.getAttribute("token").toString();
            List<SupplierQuotationItemDto> itemDtos=supplierService.getItemsFromSupplierQuotation(sid,quotation);
            mv.addObject("items",itemDtos);
            return mv;
        }
        catch (Exception e)
        {
            ModelAndView mvi=new ModelAndView("error");
            mvi.addObject("error",e.getMessage());
            return mvi;
        }
    }

    @GetMapping("/listeSupplierQuotation")
    public ModelAndView listeSupplierQuotation(@RequestParam("supplier") String supplier, HttpSession session) throws Exception {
        ModelAndView mv=new ModelAndView("liste-supplier-quotation");
        try {
            if(session.getAttribute("token")==null)
            {
                throw new Exception("pas de session valide");
            }
            session.setAttribute("supplier",supplier);
            List<SupplierQuotation> quotations=supplierService.getQuotationsBySupplier(session.getAttribute("token").toString(),supplier);
            mv.addObject("quotations", quotations);
            return mv;
        }
        catch (Exception e)
        {
            ModelAndView mvi=new ModelAndView("error");
            mvi.addObject("error",e.getMessage());
            return mvi;
        }


    }
    @GetMapping("/listeSupplier")
    public ModelAndView listeSupplier(HttpSession session) throws Exception {
        ModelAndView mv=new ModelAndView("liste-supplier");
        try {
            if(session.getAttribute("token")==null)
            {
                throw new Exception("pas de session valide");
            }
            List<Supplier> suppliers = supplierService.getSuppliers(session.getAttribute("token").toString());
            mv.addObject("suppliers", suppliers);
            return mv;
        }
        catch (Exception e)
        {
            ModelAndView mvi=new ModelAndView("error");
            mvi.addObject("error",e.getMessage());
            return mvi;
        }


    }


}
