package at.spengergasse.IShop.service;

import at.spengergasse.IShop.domain.*;
import at.spengergasse.IShop.persistence.OrderRepository;
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
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemService orderItemService;

    @Transactional(readOnly = false)
    public Order createOrder(@Valid @NotBlank Date deliveryDate, Date purchaseDate, Customer customer, List<Order_item> items)
    {
        Order order = Order.builder()
                                    .deliveryDate(deliveryDate)
                                    .purchaseDate(purchaseDate)
                                    .customer(customer)
                                    .order_items(items)
                                    .build();

        return orderRepository.save(order);
    }

    @Transactional(readOnly = false)
    public Order createOrder(Order order)
    {
        return orderRepository.save(order);
    }

    @Transactional(readOnly = false)
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
    @Transactional(readOnly = false)
    public void deleteAllOrders(List<Order> orders) { orderRepository.deleteAll(orders); }

    @Transactional(readOnly = false)
    public void updateOrder(Long id, final Order updatedOrder) {
        orderRepository.findById(id)
                          .map(order -> {
                              order.setDeliveryDate(updatedOrder.getDeliveryDate());
                              order.setPurchaseDate(updatedOrder.getPurchaseDate());
                              order.setCustomer(updatedOrder.getCustomer());
                              order.setOrder_items(updatedOrder.getOrder_items());
                              return order;
                          })
                          .map(orderRepository::save);
    }

    public List<Order> getOrders()
    {
        return orderRepository.findAll();
    }

    public Optional<Order> getOrder(Long id)
    {
        return orderRepository.findById(id);
    }

    public List<Order> getAllOrdersByCustomer(Long id) {
        return orderRepository.findAllByCustomerId(id);
    }


}
