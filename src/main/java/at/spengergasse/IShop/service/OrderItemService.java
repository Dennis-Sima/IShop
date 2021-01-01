package at.spengergasse.IShop.service;

import at.spengergasse.IShop.domain.Customer;
import at.spengergasse.IShop.domain.Order;
import at.spengergasse.IShop.domain.Order_item;
import at.spengergasse.IShop.domain.Product;
import at.spengergasse.IShop.persistence.OrderRepository;
import at.spengergasse.IShop.persistence.Order_itemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor

@Service
@Transactional(readOnly = true)
public class OrderItemService {

    private final Order_itemRepository order_itemRepository;


    @Transactional(readOnly = false)
    public Order_item createOrder_Item(@Valid @NotBlank Order order, Product product)
    {
        Order_item order_item = Order_item.builder()
                                    .order(order)
                                    .product(product)
                                    .build();
        return order_itemRepository.save(order_item);
    }

    @Transactional(readOnly = false)
    public Order_item createOrder_Item(Order_item order_item)
    {
        return order_itemRepository.save(order_item);
    }

    @Transactional(readOnly = false)
    public List<Order_item> createOrder_Items(List<Order_item> list)
    {
        return order_itemRepository.saveAll(list);
    }

    @Transactional(readOnly = false)
    public void deleteOrder_item(Long id) {
        order_itemRepository.deleteById(id);
    }

    @Transactional(readOnly = false)
    public void deleteAllOrder_items(List<Order_item> order_items)
    {
        order_itemRepository.deleteAll(order_items);
    }

    @Transactional(readOnly = false)
    public void deleteProduct(Product product)
    {
        order_itemRepository.deleteAll(order_itemRepository.findAllByProduct(product));
    }

    @Transactional(readOnly = false)
    public void deleteAllOrderItemsFromListOrders(List<Order> orders) {
        for(Order o : orders)
        {
            order_itemRepository.deleteAll(order_itemRepository.findAllByOrderId(o.getId()));
        }
    }
    @Transactional(readOnly = false)
    public void deleteAllOrderItemsFromListProducts(List<Product> products) {
        for(Product p : products)
        {
            order_itemRepository.deleteAll(order_itemRepository.findAllByProductEan(p.getEan()));
        }
    }

    @Transactional(readOnly = false)
    public void updateOrder_Item(Long id, final Order_item updatedOrder_Item) {
        order_itemRepository.findById(id)
                          .map(order_item -> {
                              order_item.setOrder(updatedOrder_Item.getOrder());
                              order_item.setProduct(updatedOrder_Item.getProduct());
                              return order_item;
                          })
                          .map(order_itemRepository::save);
    }

    public List<Order_item> getOrder_Items()
    {
        return order_itemRepository.findAll();
    }

    public Optional<Order_item> getOrder_Item(Long id)
    {
        return order_itemRepository.findById(id);
    }


}
