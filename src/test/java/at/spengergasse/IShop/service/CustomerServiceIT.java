package at.spengergasse.IShop.service;

import at.spengergasse.IShop.domain.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static at.spengergasse.IShop.domain.DomainFixtures.defaultCustomer;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CustomerServiceIT {

    @Autowired
    CustomerService customerService;

    @Test
    void ensureCreatingCustomerWorksProperly(){
        // given
        String fName = "Max";
        String lName = "Mustermann";
        Date gebDatum = Date.valueOf(LocalDate.of(1999,11,10));
        String email = "max.m@mustermail.com";
        String bank = "AT10 1234 5678";
        boolean newsletter = false;
        double credit = 10.50;
        String street = "Musterstraße";
        long zipcode = 1234;
        String phone = "+43 660 787878";
        String city = "Musterstadt";
        String country = "Musterland";
        String houseNr = "12";
        // when
        Customer createdCustomer = customerService.createCustomer(fName,lName,phone,gebDatum,email,bank,newsletter,credit,street,zipcode,city,country,houseNr);

        // then
        assertThat(createdCustomer).isNotNull();
        assertThat(createdCustomer.getId()).isNotNull();
        assertThat(createdCustomer.getEmail()).isEqualTo(email);
        assertThat(createdCustomer.getFirst_name()).isEqualTo(fName);
        assertThat(createdCustomer.getLast_name()).isEqualTo(lName);
    }
    @Test
    void ensureCreatingCustomer2WorksProperly(){

        //given
        Customer c = defaultCustomer();

        // when
        Customer createdCustomer = customerService.createCustomer(c);

        // then
        assertThat(createdCustomer).isNotNull();
        assertThat(createdCustomer.getId()).isNotNull();
        assertThat(createdCustomer.getEmail()).isEqualTo(c.getEmail());
        assertThat(createdCustomer.getFirst_name()).isEqualTo(c.getFirst_name());
        assertThat(createdCustomer.getLast_name()).isEqualTo(c.getLast_name());
    }
    @Test
    void ensureDeleteCustomerWorksProperly(){
        //given
        Customer createdCustomer = customerService.createCustomer(defaultCustomer());

        // when
        customerService.deleteCustomer(createdCustomer.getId());

        // then
        assertThat(customerService.getCustomer(createdCustomer.getId())).isEqualTo(Optional.empty());
    }
    @Test
    void ensureUpdateCustomerWorksProperly(){
        //given
        Customer createdCustomer = customerService.createCustomer(defaultCustomer());
        Customer updateCustomer = createdCustomer;
        updateCustomer.setFirst_name("Peter");

        // when
        customerService.updateCustomer(createdCustomer.getId(),updateCustomer);
        Customer newCustomer = customerService.getCustomer(updateCustomer.getId()).get();

        // then
        assertThat(newCustomer).isNotNull();
        assertThat(newCustomer.getId()).isNotNull();
        assertThat(newCustomer.getFirst_name()).isEqualTo("Peter");

    }


    @Test
    void ensureGetCustomersWorksProperly(){
        //given
        Customer createdCustomer1 = customerService.createCustomer(defaultCustomer());
        Customer createdCustomer2 = customerService.createCustomer(defaultCustomer());

        //when
        List<Customer> savedCustomers = customerService.getCustomers();


        // then
        assertThat(savedCustomers).isNotNull().contains(createdCustomer1);
        assertThat(savedCustomers).isNotNull().contains(createdCustomer2);

    }
    @Test
    void ensureGetCustomerWorksProperly(){
        // when
        Customer createdCustomer = customerService.createCustomer(defaultCustomer());
        Customer savedCustomer = customerService.getCustomer(createdCustomer.getId()).get();


        // then
        assertThat(savedCustomer).isNotNull();
        assertThat(savedCustomer.getId()).isNotNull();
        assertThat(savedCustomer.getEmail()).isEqualTo(createdCustomer.getEmail());
        assertThat(savedCustomer.getFirst_name()).isEqualTo(createdCustomer.getFirst_name());
        assertThat(savedCustomer.getLast_name()).isEqualTo(createdCustomer.getLast_name());

    }
}