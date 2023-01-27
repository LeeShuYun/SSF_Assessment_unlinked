package dev.leeshuyun.SSFAssessment.controller;

//controller - ??? clean up later
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

//etc - ??? clean up later
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import dev.leeshuyun.SSFAssessment.models.PizzaOrder;
import dev.leeshuyun.SSFAssessment.service.PizzaService;

@Controller
@RequestMapping(path="/")
public class PizzaController {

    private PizzaOrder pizzaOrder = new PizzaOrder();

    // public PizzaOrder createPizzaOrder(@ModelAttribute PizzaOrder pizzaOrder, Model model){
    //     return "";
    // }
    //this is just to get the person from the Model and add Person obj to the Model.
    @GetMapping(path="/")
    public String getRegForm(Model model){
        model.addAttribute("pizzaOrder", pizzaOrder);
        return "index"; //brings you back to /index.html
    }

    // @Valid validates the PersonObj we just got with conditions inside the pizzaorder Obj itself. 
    // BindingResult stores the result
    @PostMapping(path="/pizza", consumes= "application/x-www-form-urlencoded")
    // public String postRegistration(@Valid PizzaOrder pizzaorder, BindingResult bResult, Model model){
    public String postPizzaDetails(PizzaOrder pizzaOrder, Model model){
        //any failure to validate the inputs will bring us back to original form
        // if(bResult.hasErrors()){
        //     return "index"; 
        // }
        return "orderdeliveryform";
        
    }

    @PostMapping(path="/pizza/order", consumes= "application/x-www-form-urlencoded")
    public String postOrderDeliveryDetails(PizzaOrder pizzaOrder, Model model){


        //generateId
        pizzaOrder.setOrderId(pizzaOrder.generateId());

        //calculate the total order cost
        calcOrderCost(pizzaOrder);
        
        //save to Redis 
        
        
        return "orderdeliveryform";
        
        
    }


}
