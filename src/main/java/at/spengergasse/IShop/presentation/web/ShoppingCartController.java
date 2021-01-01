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
@RequestMapping("/shoppingCarts")
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;
    private final CustomerService customerService;
    private final ProductService productService;
    private final ShoppingCartItemService shoppingCartItemService;
    /*private Shopping_cart s1;
    private List<Order_item> oil = new ArrayList<Order_item>();
    private Long sId = 0l;*/

    @GetMapping
    public String getAllShoppingCarts(Model model){
        List<Shopping_cart> shoppingCarts= shoppingCartService.getShoppingCarts();
        model.addAttribute("shoppingCarts", shoppingCarts);

        return "shoppingCarts/browse";
    }

    @GetMapping("/detail")
    public String getShoppingCarts(@RequestParam Long id ,Model model)
    {
        Optional<Shopping_cart> shopping_cart = shoppingCartService.getShoppingCart(id);

        if(shopping_cart.isPresent())
        {
            Shopping_cart s = shopping_cart.get();
            model.addAttribute("shoppingCart",s);
            model.addAttribute("items",s.getShopping_cart_items());
            return "shoppingCarts/detail";
        }
        else
        {
            model.addAttribute("shoppingCart", Collections.emptyList());
            return "shoppingCarts/browse";
        }
    }

    @GetMapping("/add")
    public String showAddShoppingCartForm(Model model)
    {
        Shopping_cart shopping_cart = new Shopping_cart();
        List<Customer> customers = customerService.getCustomers();
        List<Product> products = productService.getProducts();
        Product product = new Product();
        Shopping_cart_item shopping_cart_item = new Shopping_cart_item();
        model.addAttribute("shoppingCart", shopping_cart);
        model.addAttribute("customers",customers);
        model.addAttribute("products",products);
        model.addAttribute("product", product);
        model.addAttribute("shoppingCartItem",shopping_cart_item);
        return "shoppingCarts/add";
    }


    @PostMapping("/add")
    public String handleAddShoppingCartFormSubmission(@ModelAttribute Shopping_cart shoppingCart, @ModelAttribute Shopping_cart_item shoppingCartItem, BindingResult shoppingCartBindingResult, Model model)
    {
        if(shoppingCartBindingResult.hasErrors())
        {
            return "shoppingCarts/add";
        }
        shoppingCartService.createShoppingCart(shoppingCart);
        shoppingCartItem.setShoppingCart(shoppingCart);
        shoppingCartItemService.createShoppingCartItem(shoppingCartItem);
        return "redirect:/shoppingCarts";
    }
    @GetMapping("/addProduct")
    public String showAddProductForm(@RequestParam Long id, Model model)
    {
        Shopping_cart_item shopping_cart_item = new Shopping_cart_item();
        List<Product> products = productService.getProducts();
        //Long sId = id;
        model.addAttribute("shoppingCartItem",shopping_cart_item);
        model.addAttribute("products",products);
        return "shoppingCarts/addProduct";
    }

    @PostMapping("/addProduct")
    public String handleAddProductFormSubmission(@RequestParam Long id, @ModelAttribute Shopping_cart_item shopping_cart_item, BindingResult productBindingResult, Model model)
    {
        if(productBindingResult.hasErrors())
        {
            return "shoppingCarts/addProduct";
        }
        shoppingCartService.updateShoppingCart(id,shoppingCartService.getShoppingCart(id).get());
        shopping_cart_item.setShoppingCart(shoppingCartService.getShoppingCart(id).get());
        shoppingCartItemService.createShoppingCartItem(shopping_cart_item);
        return ("redirect:/shoppingCarts/detail?id=" + id);
    }

    @GetMapping("/deleteItem")
    public String deleteItem(@RequestParam Long id,Model model)
    {
        // We have to find out the id of the ShoppingCart, because the RequestParam is the ShoppingCartID
        Long shoppingCart_id = shoppingCartItemService.getShopping_Cart_Item(id).get().getShoppingCart().getId();
        shoppingCartItemService.deleteShoppingCartItem(id);
        return ("redirect:/shoppingCarts/detail?id=" + shoppingCart_id);
    }

    @GetMapping("/edit")
    public String showEditShoppingCartForm(@RequestParam Long id ,Model model)
    {
        return shoppingCartService.getShoppingCart(id)
                .map(shoppingCart -> {
                    model.addAttribute("shoppingCart", shoppingCart);
                    model.addAttribute("customers", customerService.getCustomers());
                    return "shoppingCarts/edit";
                })
                .orElse("redirect:/shoppingCarts");
    }

    @PostMapping("/edit")
    public String handleEditShoppingCartFormSubmission(@RequestParam Long id ,@ModelAttribute Shopping_cart shoppingCart, BindingResult shoppingCartBindingResult, Model model)
    {
        if(shoppingCartBindingResult.hasErrors())
        {
            return "shoppingCarts/edit";
        }
        shoppingCartService.updateShoppingCart(id,shoppingCart);
        return "redirect:/shoppingCarts";
    }

    @GetMapping("/delete")
    public String deleteShoppingCart(@RequestParam Long id, Model model)
    {
        List<Shopping_cart_item> items = shoppingCartService.getShoppingCart(id).get().getShopping_cart_items();
        shoppingCartItemService.deleteAllShoppingCartItems(items);
        shoppingCartService.deleteShoppingCart(id);
        return "redirect:/shoppingCarts";
    }
}
