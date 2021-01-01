package at.spengergasse.IShop.presentation.web;

import at.spengergasse.IShop.domain.Customer;
import at.spengergasse.IShop.domain.Order;
import at.spengergasse.IShop.domain.Shopping_cart;
import at.spengergasse.IShop.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor

@Controller
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;
    private final OrderService orderService;
    private final OrderItemService orderItemService;
    private final ShoppingCartService shoppingCartService;
    private final ShoppingCartItemService shoppingCartItemService;


    @GetMapping
    public String getAllCustomers(Model model){
        List<Customer> customers = customerService.getCustomers();
        model.addAttribute("customers", customers);

        return "customers/browse";
    }

    @GetMapping("/detail")
    public String getCustomer(@RequestParam Long id ,Model model)
    {
        Optional<Customer> customer = customerService.getCustomer(id);
        if(customer.isPresent())
        {
            model.addAttribute("customer",customer.get());
            return "customers/detail";
        }
        else
        {
            model.addAttribute("customers", Collections.emptyList());
            return "customers/browse";
        }
    }

    @GetMapping("/add")
    public String showAddCustomerForm(Model model)
    {
        Customer customer = new Customer();
        model.addAttribute("customer", customer);
        return "customers/add";
    }

    @PostMapping("/add")
    public String handleAddCustomerFormSubmission(@ModelAttribute Customer customer, BindingResult customerBindingResult, Model model)
    {
        if(customerBindingResult.hasErrors())
        {
            return "customers/add";
        }
        customerService.createCustomer(customer);
        return "redirect:/customers";
    }

    @GetMapping("/edit")
    public String showEditCustomerForm(@RequestParam Long id ,Model model)
    {
        return customerService.getCustomer(id)
                .map(customer -> {
                    model.addAttribute("customer", customer);
                    return "customers/edit";
                })
                .orElse("redirect:/customers");
    }

    @PostMapping("/edit")
    public String handleEditCustomerFormSubmission(@RequestParam Long id ,@ModelAttribute Customer customer, BindingResult customerBindingResult, Model model)
    {
        if(customerBindingResult.hasErrors())
        {
            return "customers/edit";
        }
        customerService.updateCustomer(id,customer);
        return "redirect:/customers";
    }

    @GetMapping("/delete")
    public String deleteCustomer(@RequestParam Long id, Model model)
    {
        //First delete all other Foreign Key Values in Order & OrderItems
        List<Order> orders = orderService.getAllOrdersByCustomer(id);
        orderItemService.deleteAllOrderItemsFromListOrders(orders);
        orderService.deleteAllOrders(orders);

        //Then delete the Foreign Key in ShoppingCart & ShoppingCartItems
        List<Shopping_cart> shopping_carts = shoppingCartService.getAllShoppingCartsByCustomer(id);
        shoppingCartItemService.deleteAllShoppingCartItemsFromListShoppingCarts(shopping_carts);
        shoppingCartService.deleteAllShoppingCarts(shopping_carts);

        customerService.deleteCustomer(id);
        return "redirect:/customers";
    }
}
