package eval.newApp.controller;

import eval.newApp.modele.supplier.Supplier;
import eval.newApp.service.SupplierService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class SupplierController
{
    @Autowired
    SupplierService supplierService;

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
