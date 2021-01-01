package at.spengergasse.IShop.presentation.api;

import at.spengergasse.IShop.domain.Customer;
import at.spengergasse.IShop.domain.DomainFixtures;
import at.spengergasse.IShop.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assumptions.assumeThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = CustomerRestController.class)
class CustomerRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @BeforeEach
    void setup(){
        assumeThat(mockMvc).isNotNull();
        assumeThat(customerService).isNotNull();
    }

    @Test
    void ensureFetchingExistingIdWorksProperly() throws Exception {
        //given
        long id = 1l;
        when(customerService.getCustomer(id)).thenReturn((Optional.of(DomainFixtures.defaultCustomer(id))));

        //expect
        mockMvc.perform(get("/api/v1/customers/{id}", id).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.first_name").value("Franz"))
                .andExpect(jsonPath("$.last_name").value("Huber"))
                .andExpect(jsonPath("$.credit").value(13.23))
                .andExpect(jsonPath("$.gebdate").isNotEmpty())
                .andExpect(jsonPath("$.phone").value("+43 660 3948"))
                .andExpect(jsonPath("$.newsletter").value(Boolean.TRUE))
                .andExpect(jsonPath("$.zipcode").value(1050l))
                .andExpect(jsonPath("$.street").value("Spengergasse"))
                .andExpect(jsonPath("$.house_nr").value("11"))
                .andExpect(jsonPath("$.bank_details").value("AT9798 4937 9348"));
    }

    @Test
    void ensureFetchingNotExistingIdWorksProperly() throws Exception {
        //given
        long id = 1l;
        when(customerService.getCustomer(id)).thenReturn((Optional.empty()));

        //expect
        mockMvc.perform(get("/api/v1/customers/{id}", id).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode").value(4711))
                .andExpect(jsonPath("$.message").value("Cannot load instance of type 'Customer' with id: '1'"))
                .andExpect(jsonPath("$.httpStatus").value(400))
                .andExpect(jsonPath("$.documentationUrl").value("https://api.spengergasse.at/v1/customers/_id"));
    }

    @Test
    void ensureFetchingExistingCustomersWorksProperly() throws Exception {
        //given
       List<Customer> customers = List.of(
                Customer.builder()
                        .first_name("Dennis")
                        .last_name("SIMA")
                        .city("Vienna")
                        .country("Austria")
                        .email("SIM18354@spengergasse.at")
                        .credit(13.23)
                        .gebdate(Date.valueOf("2001-11-26"))
                        .phone("+43 660 3948")
                        .newsletter(Boolean.FALSE)
                        .zipcode(1110l)
                        .street("Spengergasse")
                        .house_nr("11")
                        .bank_details("AT9798 4233 9348")
                        .build(),

                Customer.builder()
                        .first_name("Max")
                        .last_name("DONNINGER")
                        .city("Wiener Neustadt")
                        .country("Austria")
                        .email("Donninger@gmail.com")
                        .credit(122.20)
                        .gebdate(Date.valueOf(LocalDate.now()))
                        .phone("+43 660 3948")
                        .newsletter(Boolean.TRUE)
                        .zipcode(2736l)
                        .street("Spengergasse")
                        .house_nr("11")
                        .bank_details("AT9798 4937 9348")
                        .build());
        when(customerService.getCustomers()).thenReturn(customers);

        //expect
        MvcResult m = mockMvc.perform(get("/api/v1/customers").accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].first_name").value("Dennis"))
                .andExpect(jsonPath("$[1].first_name").value("Max"))

                .andReturn();
    }
}