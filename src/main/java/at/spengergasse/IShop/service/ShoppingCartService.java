package at.spengergasse.IShop.service;

import at.spengergasse.IShop.domain.*;
import at.spengergasse.IShop.persistence.OrderRepository;
import at.spengergasse.IShop.persistence.Shopping_cartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor

@Service
@Transactional(readOnly = true)
public class ShoppingCartService {

    private final Shopping_cartRepository shoppingCartRepository;
    private final ShoppingCartItemService shoppingCartItemService;

    @Transactional(readOnly = false)
    public Shopping_cart createShoppingCart(@Valid @NotBlank Customer customer, List<Shopping_cart_item> items)
    {
        Shopping_cart shopping_cart = Shopping_cart.builder()
                                    .customer(customer)
                                    .shopping_cart_items(items)
                                    .build();

        return shoppingCartRepository.save(shopping_cart);
    }

    @Transactional(readOnly = false)
    public Shopping_cart createShoppingCart(Shopping_cart shopping_cart)
    {
        return shoppingCartRepository.save(shopping_cart);
    }
    @Transactional(readOnly = false)
    public void deleteAllShoppingCarts(List<Shopping_cart> shopping_carts) { shoppingCartRepository.deleteAll(shopping_carts); }

    @Transactional(readOnly = false)
    public void deleteShoppingCart(Long id) {
        shoppingCartRepository.deleteById(id);
    }

    @Transactional(readOnly = false)
    public void updateShoppingCart(Long id, final Shopping_cart updatedShoppingCart) {
        shoppingCartRepository.findById(id)
                          .map(shopping_cart -> {
                              shopping_cart.setCustomer(updatedShoppingCart.getCustomer());
                              return shopping_cart;
                          })
                          .map(shoppingCartRepository::save);
    }

    public List<Shopping_cart> getShoppingCarts()
    {
        return shoppingCartRepository.findAll();
    }

    public Optional<Shopping_cart> getShoppingCart(Long id)
    {
        return shoppingCartRepository.findById(id);
    }



    public List<Shopping_cart> getAllShoppingCartsByCustomer(Long id) {
        return shoppingCartRepository.findAllByCustomerId(id);
    }

}
