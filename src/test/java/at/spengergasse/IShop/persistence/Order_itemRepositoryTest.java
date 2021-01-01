package at.spengergasse.IShop.persistence;

import at.spengergasse.IShop.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static at.spengergasse.IShop.domain.DomainFixtures.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assumptions.assumeThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class Order_itemRepositoryTest {

    @Autowired
    private Order_itemRepository order_itemRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ManufacturerRepository manufacturerRepository;

    @BeforeEach
    void setup(){
        assumeThat(order_itemRepository).isNotNull();
        assumeThat(orderRepository).isNotNull();
        assumeThat(customerRepository).isNotNull();
        assumeThat(manufacturerRepository).isNotNull();
        assumeThat(productRepository).isNotNull();
    }


    @Test
    void ensureSavingAndReReadingWorksProperly() {
        //given
        Manufacturer m = defaultManufacturer();
        Manufacturer savedManufacturer = manufacturerRepository.save(m);

        Product p = defaultProduct(savedManufacturer);
        Product savedProduct = productRepository.save(p);

        Customer c = defaultCustomer();
        Customer savedCustomer = customerRepository.save(c);

        Order o = defaultOrder(savedCustomer, new ArrayList<Order_item>());
        o.setCustomer(savedCustomer);
        Order savedOrder = orderRepository.save(o);

        Order_item oi = defaultOrder_item(savedProduct, savedOrder);

        //when
        Order_item saved_order = order_itemRepository.save(oi);

        //then
        assertThat(saved_order).isNotNull();
        assertThat(saved_order.getId()).isNotNull();
        assertThat(saved_order.getVersion()).isNotNull();

    }

    @Test
    void findAllByOrderId() {
        //given
        Manufacturer m = defaultManufacturer();
        Manufacturer savedManufacturer = manufacturerRepository.save(m);

        Product p = defaultProduct(savedManufacturer);
        Product savedProduct = productRepository.save(p);

        Customer c = defaultCustomer();
        Customer savedCustomer = customerRepository.save(c);

        Order o = defaultOrder(savedCustomer, new ArrayList<Order_item>());
        o.setCustomer(savedCustomer);
        Order savedOrder = orderRepository.save(o);

        Order_item oi = defaultOrder_item(savedProduct, savedOrder);
        order_itemRepository.save(oi);

        //when
        List<Order_item> orderItems = order_itemRepository.findAllByOrderId(savedOrder.getId());

        //then
        assertThat(orderItems).isNotNull().contains(oi);
    }



}