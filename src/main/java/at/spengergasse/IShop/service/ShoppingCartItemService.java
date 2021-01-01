package at.spengergasse.IShop.service;

import at.spengergasse.IShop.domain.*;
import at.spengergasse.IShop.persistence.Order_itemRepository;
import at.spengergasse.IShop.persistence.Shopping_cart_itemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor

@Service
@Transactional(readOnly = true)
public class ShoppingCartItemService {

    private final Shopping_cart_itemRepository shopping_cart_itemRepository;


    @Transactional(readOnly = false)
    public Shopping_cart_item createShopping_cart_Item(@Valid @NotBlank Shopping_cart shopping_cart, Product product)
    {
        Shopping_cart_item shopping_cart_item = Shopping_cart_item.builder()
                                    .shoppingCart(shopping_cart)
                                    .product(product)
                                    .build();
        return shopping_cart_itemRepository.save(shopping_cart_item);
    }

    @Transactional(readOnly = false)
    public Shopping_cart_item createShoppingCartItem(Shopping_cart_item shopping_cart_item)
    {
        return shopping_cart_itemRepository.save(shopping_cart_item);
    }

    @Transactional(readOnly = false)
    public List<Shopping_cart_item> createShoppingCartItems(List<Shopping_cart_item> list)
    {
        return shopping_cart_itemRepository.saveAll(list);
    }

    @Transactional(readOnly = false)
    public void deleteShoppingCartItem(Long id) {
        shopping_cart_itemRepository.deleteById(id);
    }

    @Transactional(readOnly = false)
    public void deleteAllShoppingCartItems(List<Shopping_cart_item> shopping_cart_items)
    {
        shopping_cart_itemRepository.deleteAll(shopping_cart_items);
    }



    @Transactional(readOnly = false)
    public void deleteProduct(Product product)
    {
        shopping_cart_itemRepository.deleteAll(shopping_cart_itemRepository.findAllByProduct(product));
    }

    @Transactional(readOnly = false)
    public void deleteAllShoppingCartItemsFromListShoppingCarts(List<Shopping_cart> shopping_carts) {
        for(Shopping_cart sh : shopping_carts)
        {
            shopping_cart_itemRepository.deleteAll(shopping_cart_itemRepository.findAllByShoppingCartId(sh.getId()));
        }
    }
    @Transactional(readOnly = false)
    public void deleteAllShoppingCartItemsFromListProducts(List<Product> products) {
        for(Product p : products)
        {
            shopping_cart_itemRepository.deleteAll(shopping_cart_itemRepository.findAllByProductEan(p.getEan()));
        }
    }

    @Transactional(readOnly = false)
    public void updateShoppingCartItem(Long id, final Shopping_cart_item updatedShopping_Cart_Item) {
        shopping_cart_itemRepository.findById(id)
                          .map(shopping_cart_item -> {
                              shopping_cart_item.setShoppingCart(updatedShopping_Cart_Item.getShoppingCart());
                              shopping_cart_item.setProduct(updatedShopping_Cart_Item.getProduct());
                              return shopping_cart_item;
                          })
                          .map(shopping_cart_itemRepository::save);
    }

    public List<Shopping_cart_item> getShopping_Cart_Items()
    {
        return shopping_cart_itemRepository.findAll();
    }

    public Optional<Shopping_cart_item> getShopping_Cart_Item(Long id)
    {
        return shopping_cart_itemRepository.findById(id);
    }

}
