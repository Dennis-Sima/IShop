package at.spengergasse.IShop.presentation.web;

import at.spengergasse.IShop.domain.Customer;
import at.spengergasse.IShop.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static at.spengergasse.IShop.domain.DomainFixtures.defaultCustomer;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assumptions.assumeThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = CustomerController.class)
class CustomerControllerITest {

    @MockBean
    private CustomerService customerService;
    @MockBean
    private  OrderService orderService;
    @MockBean
    private  OrderItemService orderItemService;
    @MockBean
    private  ShoppingCartService shoppingCartService;
    @MockBean
    private  ShoppingCartItemService shoppingCartItemService;


    @Autowired
    private MockMvc mockMvc;
    @BeforeEach
    void setup(){
        assumeThat(customerService).isNotNull();
        assumeThat(orderService).isNotNull();
        assumeThat(orderItemService).isNotNull();
        assumeThat(shoppingCartService).isNotNull();
        assumeThat(shoppingCartItemService).isNotNull();
    }

    @Test
    void ensureGetCustomersViewRoutingIsCorrect() throws Exception{

        //given
        List<Customer> customers = List.of(defaultCustomer(1l), defaultCustomer(2l));

        given(customerService.getCustomers()).willReturn(customers);

        //expect
        MvcResult result = mockMvc.perform(get("/customers").accept(MediaType.TEXT_HTML))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("customers/browse"))
                .andExpect(model().attribute("customers", customers))
                .andReturn();
        String content = result.getResponse().getContentAsString();
        //and then
        assertThat(content.contains("<h1>Customers</h1>"));
    }

    @Test
    void ensureGetCustomerViewRoutingIsCorrect() throws Exception{

        //given
        Long id = 100l;
        Customer c = defaultCustomer(id);
        given(customerService.getCustomer(id)).willReturn(Optional.of(c));

        //expect
       MvcResult result = mockMvc.perform(get("/customers/detail?id={id}",id).accept(MediaType.TEXT_HTML))
               .andDo(print())
               .andExpect(status().isOk())
               .andExpect(view().name("customers/detail"))
               .andExpect(model().attribute("customer", c))
               .andReturn();
        String content = result.getResponse().getContentAsString();
       //and then
        assertThat(content.contains("<h1 >Customer 100</h1>"));
    }

    @Test
    void ensureGetCustomerViewRoutingIsCorrectForNonExistingCustomer() throws Exception{

        //given
        Long id = 100l;
        when(customerService.getCustomer(id)).thenReturn((Optional.empty()));

        //expect
        MvcResult result = mockMvc.perform(get("/customers/detail?id={id}",id).accept(MediaType.TEXT_HTML))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attribute("customers", Collections.emptyList()))
                .andExpect(view().name("customers/browse"))
                .andReturn();
    }




    @Test
    void ensureDeleteViewRoutingIsCorrect() throws Exception{

        //given
        Long id = 100l;
        Customer c = defaultCustomer(id);
        given(customerService.getCustomer(id)).willReturn(Optional.of(c));

        //expect
        MvcResult result = mockMvc.perform(get("/customers/delete").param("id", String.valueOf(id)).accept(MediaType.TEXT_HTML))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/customers"))
                .andReturn();

    }
}