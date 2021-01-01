package at.spengergasse.IShop.service;

import at.spengergasse.IShop.domain.Customer;
import at.spengergasse.IShop.persistence.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;

import static at.spengergasse.IShop.domain.DomainFixtures.defaultCustomer;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assumptions.assumeThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    private CustomerService customerService;

    @BeforeEach
    void setup(){
        assumeThat(customerRepository).isNotNull();
        customerService = new CustomerService(customerRepository);
    }
    @Test
    void ensureCreateCustomerCallsRightCollaborators()
    {
        //given
        Customer c = defaultCustomer();
        when(customerRepository.save(c)).thenReturn(c);

        //when
        Customer createdCustomer = customerService.createCustomer(c);

        //then
        assertThat(createdCustomer).isNotNull();
        assertThat(createdCustomer).isSameAs(c);

        verify(customerRepository, times(1)).save(c);
        verifyNoMoreInteractions(customerRepository);
    }
}