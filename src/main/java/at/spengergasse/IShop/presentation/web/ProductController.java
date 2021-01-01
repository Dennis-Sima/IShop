package at.spengergasse.IShop.presentation.web;

import at.spengergasse.IShop.domain.Category;
import at.spengergasse.IShop.domain.Customer;
import at.spengergasse.IShop.domain.Manufacturer;
import at.spengergasse.IShop.domain.Product;
import at.spengergasse.IShop.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@RequiredArgsConstructor

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final ManufacturerService manufacturerService;
    private final OrderItemService orderItemService;
    private final ShoppingCartItemService shoppingCartItemService;


    @GetMapping
    public String getAllProduct(Model model){
        List<Product> products = productService.getProducts();
        model.addAttribute("products", products);

        return "products/browse";
    }

    @GetMapping("/detail")
    public String getProduct(@RequestParam Integer id ,Model model)
    {
        Optional<Product> product = productService.getProduct(id);
        if(product.isPresent())
        {
            model.addAttribute("product",product.get());
            return "products/detail";
        }
        else
        {
            model.addAttribute("product", Collections.emptyList());
            return "redirect:/products";
        }
    }

    @GetMapping("/add")
    public String showAddProductForm(Model model)
    {
        Product product = new Product();
        List<Category> categories = List.of(Category.values());
        List<Manufacturer> manufacturers = manufacturerService.getManufacturers();
        model.addAttribute("product", product);
        model.addAttribute("categories", categories);
        model.addAttribute("manufacturers", manufacturers);
        return "products/add";
    }

    @PostMapping("/add")
    public String handleAddProductFormSubmission(@ModelAttribute Product product, BindingResult productBindingResult, Model model)
    {
        if(productBindingResult.hasErrors())
        {
            return "products/add";
        }
        productService.createProduct(product);
        return "redirect:/products";
    }

    @GetMapping("/edit")
    public String showEditProductForm(@RequestParam Integer id ,Model model)
    {
        List<Category> categories = List.of(Category.values());
        List<Manufacturer> manufacturers = manufacturerService.getManufacturers();
        return productService.getProduct(id)
                .map(product -> {
                    model.addAttribute("product", product);
                    model.addAttribute("categories", categories);
                    model.addAttribute("manufacturers", manufacturers);
                    return "products/edit";
                })
                .orElse("redirect:/products");
    }

    @PostMapping("/edit")
    public String handleEditProductFormSubmission(@RequestParam Integer id ,@ModelAttribute Product product, BindingResult productBindingResult, Model model)
    {
        if(productBindingResult.hasErrors())
        {
            return "products/edit";
        }
        productService.updateProduct(id,product);
        return "redirect:/products";
    }

    @GetMapping("/delete")
    public String deleteProduct(@RequestParam Integer id, Model model)
    {
        orderItemService.deleteProduct(productService.getProduct(id).get());
        shoppingCartItemService.deleteProduct(productService.getProduct(id).get());
        productService.deleteProduct(id);
        return "redirect:/products";
    }
}
