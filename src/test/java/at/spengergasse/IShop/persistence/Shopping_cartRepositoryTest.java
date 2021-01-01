package at.spengergasse.IShop.persistence;

import at.spengergasse.IShop.domain.Customer;
import at.spengergasse.IShop.domain.Shopping_cart;
import at.spengergasse.IShop.domain.Shopping_cart_item;
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
import static at.spengergasse.IShop.domain.DomainFixtures.defaultShopping_cart;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assumptions.assumeThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class Shopping_cartRepositoryTest {

    @Autowired
    private Shopping_cartRepository shopping_cartRepository;
    @Autowired
    private CustomerRepository customerRepository;

    @BeforeEach
    void setup(){

        assumeThat(shopping_cartRepository).isNotNull();
        assertThat(customerRepository).isNotNull();
    }

    @Test
    void ensureSavingAndReReadingWorksProperly() {
        //given
        Shopping_cart c = defaultShopping_cart(new Customer(),new ArrayList<Shopping_cart_item>());


        //when
        Shopping_cart savedShopping_cart = shopping_cartRepository.save(c);

        //then
        assertThat(savedShopping_cart).isNotNull();
        assertThat(savedShopping_cart.getId()).isNotNull();
        assertThat(savedShopping_cart.getVersion()).isNotNull();

    }

    @Test
    void findAllByCustomerId() {
        //given
        Customer c = defaultCustomer();
        Customer savedCustomer = customerRepository.save(c);

        Shopping_cart sh = defaultShopping_cart(savedCustomer,new ArrayList<Shopping_cart_item>());
        shopping_cartRepository.save(sh);

        //when
        List<Shopping_cart> shopping_carts = shopping_cartRepository.findAllByCustomerId(savedCustomer.getId());

        //then
        assertThat(shopping_carts).isNotNull().contains(sh);
    }


}