package at.spengergasse.IShop.persistence;

import at.spengergasse.IShop.domain.Customer;
import at.spengergasse.IShop.domain.Order;
import at.spengergasse.IShop.domain.Order_item;
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

import static at.spengergasse.IShop.domain.DomainFixtures.defaultCustomer;
import static at.spengergasse.IShop.domain.DomainFixtures.defaultOrder;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assumptions.assumeThat;
import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
@DataJpaTest
class OrderRepositoryTest {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CustomerRepository customerRepository;

    @BeforeEach
    void setup(){
        assumeThat(orderRepository).isNotNull();
        assumeThat(customerRepository).isNotNull();
    }
    @Test
    void ensureSavingAndReReadingWorksProperly() {
        //given
        Order c = defaultOrder(new Customer(), new ArrayList<Order_item>());
        //when
        Order saved = orderRepository.save(c);

        //then
        assertThat(saved).isNotNull();
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getVersion()).isNotNull();

    }
    @Test
    void findAllByCustomerId() {
        //given
        Customer c = defaultCustomer();
        Customer savedCustomer = customerRepository.save(c);

        Order o = defaultOrder(savedCustomer, new ArrayList<Order_item>());
        Order saved3 =  orderRepository.save(o);

        //when
        List<Order> orders = orderRepository.findAllByCustomerId(savedCustomer.getId());

        //then
        assertThat(orders).isNotNull().contains(saved3);
    }
}