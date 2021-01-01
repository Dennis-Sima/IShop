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
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class Shopping_cart_itemRepositoryTest {


    @Autowired
    private Shopping_cart_itemRepository shopping_cart_itemRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private Shopping_cartRepository shopping_cartRepository;
    @Autowired
    private ManufacturerRepository manufacturerRepository;
    @Autowired
    private  CustomerRepository customerRepository;

    @BeforeEach
    void setup(){
        assumeThat(shopping_cart_itemRepository).isNotNull();
        assumeThat(productRepository).isNotNull();
        assumeThat(shopping_cartRepository).isNotNull();
        assumeThat(manufacturerRepository).isNotNull();
        assumeThat(customerRepository).isNotNull();
    }

    @Test
    void ensureSavingAndReReadingWorksProperly() {
        //given
        Manufacturer m = defaultManufacturer();
        Manufacturer savedManufacturer = manufacturerRepository.save(m);

        Product p = defaultProduct(savedManufacturer);
        Product savedProduct = productRepository.save(p);

        Shopping_cart sh = defaultShopping_cart(defaultCustomer(), new ArrayList<Shopping_cart_item>());
        Shopping_cart savedShopping_cart = shopping_cartRepository.save(sh);

        Shopping_cart_item c = defaultShopping_cart_item(savedShopping_cart, savedProduct);

        //when
        Shopping_cart_item saved = shopping_cart_itemRepository.save(c);

        //then
        assertThat(saved).isNotNull();
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getVersion()).isNotNull();

    }

    @Test
    void findAllByShoppingCartCustomerIdLike() {
    //given
        Manufacturer m = defaultManufacturer();
        Manufacturer savedManufacturer = manufacturerRepository.save(m);

        Product p = defaultProduct(savedManufacturer);
        Product savedProduct = productRepository.save(p);

        Customer c = defaultCustomer();
        Customer savedCustomer = customerRepository.save(c);

        Shopping_cart sh = defaultShopping_cart(savedCustomer, new ArrayList<Shopping_cart_item>());
        Shopping_cart savedShopping_cart = shopping_cartRepository.save(sh);

        Shopping_cart_item shi = defaultShopping_cart_item(savedShopping_cart, savedProduct);
        shopping_cart_itemRepository.save(shi);

        //when
        List<Shopping_cart_item> shopping_cart_items = shopping_cart_itemRepository.findAllByShoppingCartId(savedShopping_cart.getId());

        //then
        assertThat(shopping_cart_items).isNotNull().contains(shi);
    }


}