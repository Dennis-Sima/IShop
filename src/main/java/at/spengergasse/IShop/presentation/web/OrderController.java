package at.spengergasse.IShop.presentation.web;

import at.spengergasse.IShop.domain.*;
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
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final CustomerService customerService;
    private final ProductService productService;
    private final OrderItemService orderItemService;

    @GetMapping
    public String getAllOrders(Model model){
        List<Order> orders = orderService.getOrders();
        List<Product> products = productService.getProducts();
        model.addAttribute("orders", orders);

        return "orders/browse";
    }

    @GetMapping("/detail")
    public String getOrder(@RequestParam Long id ,Model model)
    {
        Optional<Order> order = orderService.getOrder(id);

        if(order.isPresent())
        {
            Order o = order.get();
            model.addAttribute("order",o);
            model.addAttribute("items",o.getOrder_items());
            return "orders/detail";
        }
        else
        {
            model.addAttribute("order", Collections.emptyList());
            return "orders/browse";
        }
    }

    @GetMapping("/add")
    public String showAddOrderForm(Model model)
    {
        List<Product> products = productService.getProducts();
        Order order = new Order();
        List<Customer> customers = customerService.getCustomers();
        Product product = new Product();
        Order_item order_item = new Order_item();
        model.addAttribute("order", order);
        model.addAttribute("customers",customers);
        model.addAttribute("products",products);
        model.addAttribute("product", product);
        model.addAttribute("order_item",order_item);
        return "orders/add";
    }


    @PostMapping("/add")
    public String handleAddOrderFormSubmission(@ModelAttribute  Order order, @ModelAttribute Order_item order_item, BindingResult orderBindingResult, Model model)
    {
        if(orderBindingResult.hasErrors())
        {
            return "orders/add";
        }
        orderService.createOrder(order);
        order_item.setOrder(order);
        orderItemService.createOrder_Item(order_item);
        return "redirect:/orders";
    }
    @GetMapping("/addProduct")
    public String showAddProductForm(@RequestParam Long id, Model model)
    {
        List<Product> products = productService.getProducts();
        Order_item order_item = new Order_item();
        model.addAttribute("order_item",order_item);
        model.addAttribute("products",products);
        return "orders/addProduct";
    }

    @PostMapping("/addProduct")
    public String handleAddProductFormSubmission(@RequestParam Long id, @ModelAttribute Order_item order_item, BindingResult productBindingResult, Model model)
    {
        orderService.updateOrder(id,orderService.getOrder(id).get());
        order_item.setOrder(orderService.getOrder(id).get());
        orderItemService.createOrder_Item(order_item);
        return ("redirect:/orders/detail?id=" + id);
    }

    @GetMapping("/deleteItem")
    public String deleteItem(@RequestParam Long id,Model model)
    {
        // We have to find out the id of the Order, because the RequestParam is the OrderItemID
        Long Order_id = orderItemService.getOrder_Item(id).get().getOrder().getId();
        orderItemService.deleteOrder_item(id);
        return ("redirect:/orders/detail?id=" + Order_id);
    }

    @GetMapping("/edit")
    public String showEditOrderForm(@RequestParam Long id ,Model model)
    {
        return orderService.getOrder(id)
                .map(order -> {
                    model.addAttribute("order", order);
                    model.addAttribute("customers", customerService.getCustomers());
                    return "orders/edit";
                })
                .orElse("redirect:/orders");
    }

    @PostMapping("/edit")
    public String handleEditOrderFormSubmission(@RequestParam Long id ,@ModelAttribute Order order, BindingResult orderBindingResult, Model model)
    {
        if(orderBindingResult.hasErrors())
        {
            return "orders/edit";
        }
        orderService.updateOrder(id,order);
        return "redirect:/orders";
    }

    @GetMapping("/delete")
    public String deleteOrder(@RequestParam Long id, Model model)
    {
        List<Order_item> items = orderService.getOrder(id).get().getOrder_items();
        orderItemService.deleteAllOrder_items(items);
        orderService.deleteOrder(id);
        return "redirect:/orders";
    }
}
