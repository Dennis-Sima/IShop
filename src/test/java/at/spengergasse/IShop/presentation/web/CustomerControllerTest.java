package at.spengergasse.IShop.presentation.web;

import at.spengergasse.IShop.domain.Customer;
import at.spengergasse.IShop.domain.DomainFixtures;
import at.spengergasse.IShop.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static at.spengergasse.IShop.domain.DomainFixtures.defaultCustomer;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assumptions.assumeThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {

    @Mock
    private  CustomerService customerService;
    @Mock
    private  OrderService orderService;
    @Mock
    private  OrderItemService orderItemService;
    @Mock
    private  ShoppingCartService shoppingCartService;
    @Mock
    private  ShoppingCartItemService shoppingCartItemService;
    @Mock
    private CustomerController customerController;

    @Mock
    private Model model;

    @MockBean
    private BindingResult bindingResult;

    @BeforeEach
    void setup(){
        assumeThat(customerService).isNotNull();
        assumeThat(orderService).isNotNull();
        assumeThat(orderItemService).isNotNull();
        assumeThat(shoppingCartService).isNotNull();
        assumeThat(shoppingCartItemService).isNotNull();

        assumeThat(model).isNotNull();
        customerController = new CustomerController(customerService, orderService, orderItemService, shoppingCartService, shoppingCartItemService);
    }

    @Test
    void ensureViewRoutingGetCustomerIsCorrect(){

        //given
        Long id = 100l;
        Customer c = defaultCustomer(id);
        given(customerService.getCustomer(id)).willReturn(Optional.of(c));

        //when
        String view = customerController.getCustomer(id, model);

        //then
        assertThat(view).isEqualTo("customers/detail");
        verify(model, times(1)).addAttribute("customer", c);
        verifyNoMoreInteractions(customerService);
        verifyNoMoreInteractions(model);


    }

    @Test
    void ensureViewRoutingGetCustomerIsCorrectForNonExistingCustomer(){

        //given
        Long id = 100l;
        given(customerService.getCustomer(id)).willReturn(Optional.empty());

        //when
        String view = customerController.getCustomer(id, model);

        //then
        assertThat(view).isEqualTo("customers/browse");
        verify(model, times(1)).addAttribute("customers", Collections.emptyList());
        verifyNoMoreInteractions(customerService);
        verifyNoMoreInteractions(model);
    }

    @Test
    void ensureViewRoutingGetCustomersIsCorrect() {
        //given
        List<Customer> customers = List.of(defaultCustomer(1l), defaultCustomer(2l));
        when(customerService.getCustomers()).thenReturn(customers);


        //when
        String view = customerController.getAllCustomers(model);

        //then
        assertThat(view).isEqualTo("customers/browse");
        verify(model, times(1)).addAttribute("customers", customers);
        verifyNoMoreInteractions(customerService);
        verifyNoMoreInteractions(model);
    }
    @Test
    void ensureViewRoutingAddCustomersIsCorrect() {
        //given
        Customer customer = new Customer();

        //when
        String view = customerController.showAddCustomerForm(model);

        //then
        assertThat(view).isEqualTo("customers/add");
        verify(model, times(1)).addAttribute("customer", customer);
        verifyNoMoreInteractions(customerService);
        verifyNoMoreInteractions(model);
    }

    @Test
    void ensureAddCustomerSubmissionFormRoutingIsCorrect() {
        //given
        Long id = 100l;
        Customer c = defaultCustomer(id);
        given(customerService.createCustomer(c)).willReturn(c);
        BindingResult result = new BeanPropertyBindingResult(c, "customer");

        //when
        String view = customerController.handleAddCustomerFormSubmission(c,result, model);

        //then
        assertThat(view).isEqualTo("redirect:/customers");
    }
    @Test
    void ensureAddCustomerSubmissionFormRoutingIsCorrectForNonExistingCustomer() {
        //given
        BindingResult result = new BeanPropertyBindingResult(null, "customer");
        result.addError(new ObjectError("customer", "Customer must not be empty"));

        //when
        String view = customerController.handleAddCustomerFormSubmission(null, result, model);

        //then
        assertThat(view).isEqualTo("customers/add");
        verifyNoMoreInteractions(customerService);
        verifyNoMoreInteractions(model);
    }

    @Test
    void ensureEditCustomersViewRoutingIsCorrect() {
        //given
        Long id = 100l;
        Customer customer = defaultCustomer(id);
        given(customerService.getCustomer(id)).willReturn(Optional.of(customer));


        //when
        String view = customerController.showEditCustomerForm(id, model);

        //then
        assertThat(view).isEqualTo("customers/edit");
        verify(model, times(1)).addAttribute("customer", customer);
        verifyNoMoreInteractions(customerService);
        verifyNoMoreInteractions(model);
    }
    @Test
    void ensureEditCustomersViewRoutingIsCorrectForNonExistingCustomer() {
        //given
        Long id = 100l;

        //when
        String view = customerController.showEditCustomerForm(id, model);

        //then
        assertThat(view).isEqualTo("redirect:/customers");
    }

    @Test
    void ensureEditCustomerSubmissionFormRoutingIsCorrect() {
        //given
        Long id = 100l;
        Customer c = defaultCustomer(id);

        BindingResult result = new BeanPropertyBindingResult(c, "customer");

        //when
        String view = customerController.handleEditCustomerFormSubmission(id, c,result, model);

        //then
        assertThat(view).isEqualTo("redirect:/customers");
    }
    @Test
    void ensureEditCustomerSubmissionFormRoutingIsCorrectForNonExistingCustomer() {
        //given
        Long id = 100l;
        BindingResult result = new BeanPropertyBindingResult(null, "customer");
        result.addError(new ObjectError("customer", "Customer must not be empty"));

        //when
        String view = customerController.handleEditCustomerFormSubmission(id, null,result, model);

        //then
        assertThat(view).isEqualTo("customers/edit");
        verifyNoMoreInteractions(customerService);
        verifyNoMoreInteractions(model);
    }



    @Test
    void ensureDeleteRoutingIsCorrect(){

        //given
        Long id = 100l;
        Customer c = defaultCustomer(id);

        //when
        String view = customerController.deleteCustomer(id, model);

        //then
        assertThat(view).isEqualTo("redirect:/customers");
    }
}