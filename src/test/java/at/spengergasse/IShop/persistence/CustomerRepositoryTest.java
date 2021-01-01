package at.spengergasse.IShop.persistence;

import at.spengergasse.IShop.domain.Customer;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import static at.spengergasse.IShop.domain.DomainFixtures.defaultCustomer;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assumptions.assumeThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class CustomerRepositoryTest {
    @Autowired
    private CustomerRepository customerRepository;

    @BeforeEach
    void setup(){
        assumeThat(customerRepository).isNotNull();
    }

    @Test
    void ensureSavingAndReReadingWorksProperly() {
        //given
        Customer c = defaultCustomer();
        
        //when
        Customer saved = customerRepository.save(c);

        //then
        assertThat(saved).isNotNull();
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getVersion()).isNotNull();

    }

    @Test
    void findAllByCityLike() {
        //given
        Customer c = defaultCustomer();
       customerRepository.save(c);

        //when
        List<Customer> customers = customerRepository.findAllByCityLike("Vienna%");

        //then
        assertThat(customers).isNotNull().contains(c);
    }
}