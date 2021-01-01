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
@RequestMapping("/manufacturers")
public class ManufacturerController {

    private final ManufacturerService manufacturerService;
    private final OrderItemService orderItemService;
    private final ShoppingCartItemService shoppingCartItemService;
    private final ProductService productService;


    @GetMapping
    public String getAllManufacturers(Model model){
        List<Manufacturer> manufacturers = manufacturerService.getManufacturers();
        model.addAttribute("manufacturers", manufacturers);

        return "manufacturers/browse";
    }

    @GetMapping("/detail")
    public String getManufacturer(@RequestParam Integer id ,Model model)
    {
        Optional<Manufacturer> manufacturer = manufacturerService.getManufacturer(id);
        if(manufacturer.isPresent())
        {
            model.addAttribute("manufacturer",manufacturer.get());
            return "manufacturers/detail";
        }
        else
        {
            model.addAttribute("manufacturers", Collections.emptyList());
            return "manufacturers/browse";
        }
    }

    @GetMapping("/add")
    public String showAddManufacturerForm(Model model)
    {
        Manufacturer manufacturer = new Manufacturer();
        model.addAttribute("manufacturer", manufacturer);
        return "manufacturers/add";
    }

    @PostMapping("/add")
    public String handleAddManufacturerFormSubmission(@ModelAttribute  Manufacturer manufacturer, BindingResult manufacturerBindingResult, Model model)
    {
        if(manufacturerBindingResult.hasErrors())
        {
            return "manufacturers/add";
        }
        manufacturerService.createManufacturer(manufacturer);
        return "redirect:/manufacturers";
    }

    @GetMapping("/edit")
    public String showEditManufacturerForm(@RequestParam Integer id ,Model model)
    {
        return manufacturerService.getManufacturer(id)
                .map(manufacturer -> {
                    model.addAttribute("manufacturer", manufacturer);
                    return "manufacturers/edit";
                })
                .orElse("redirect:/manufacturers");
    }

    @PostMapping("/edit")
    public String handleEditManufacturerFormSubmission(@RequestParam Integer id ,@ModelAttribute Manufacturer manufacturer, BindingResult manufacturerBindingResult, Model model)
    {
        if(manufacturerBindingResult.hasErrors())
        {
            return "manufacturers/edit";
        }
        manufacturerService.updateManufacturer(id,manufacturer);
        return "redirect:/manufacturers";
    }

    @GetMapping("/delete")
    public String deleteManufacturer(@RequestParam Integer id, Model model)
    {
        //Get all Products with the Manufacturer in order to delete them later
        Manufacturer manufacturer = manufacturerService.getManufacturer(id).get();
        List<Product> products = productService.getAllProductsByManufacturer(manufacturer);

        //First delete all other Foreign Key Values in all +
        orderItemService.deleteAllOrderItemsFromListProducts(products);

        //Then delete the Foreign Key in all ShoppingCartItems
        shoppingCartItemService.deleteAllShoppingCartItemsFromListProducts(products);

        productService.deleteAllProducts(products);
        manufacturerService.deleteManufacturer(id);
        return "redirect:/manufacturers";
    }
}
