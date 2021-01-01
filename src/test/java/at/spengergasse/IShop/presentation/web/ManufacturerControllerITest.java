package at.spengergasse.IShop.presentation.web;

import at.spengergasse.IShop.domain.Customer;
import at.spengergasse.IShop.domain.Manufacturer;
import at.spengergasse.IShop.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static at.spengergasse.IShop.domain.DomainFixtures.defaultCustomer;
import static at.spengergasse.IShop.domain.DomainFixtures.defaultManufacturer;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assumptions.assumeThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ManufacturerController.class)
class ManufacturerControllerITest {

    @MockBean
    private ManufacturerService manufacturerService;
    @MockBean
    private  ProductService productService;
    @MockBean
    private  OrderItemService orderItemService;
    @MockBean
    private  ShoppingCartItemService shoppingCartItemService;


    @Autowired
    private MockMvc mockMvc;
    @BeforeEach
    void setup(){
        assumeThat(manufacturerService).isNotNull();
        assumeThat(productService).isNotNull();
        assumeThat(orderItemService).isNotNull();
        assumeThat(shoppingCartItemService).isNotNull();
    }

    @Test
    void ensureGetManufacturersViewRoutingIsCorrect() throws Exception{

        //given
        List<Manufacturer> manufacturers = List.of(defaultManufacturer(12234), defaultManufacturer(2322));

        given(manufacturerService.getManufacturers()).willReturn(manufacturers);

        //expect
        MvcResult result = mockMvc.perform(get("/manufacturers").accept(MediaType.TEXT_HTML))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("manufacturers/browse"))
                .andExpect(model().attribute("manufacturers", manufacturers))
                .andReturn();
        String content = result.getResponse().getContentAsString();
        //and then
        assertThat(content.contains("<h1>Manufacturers</h1>"));
    }

    @Test
    void ensureGetManufacturerViewRoutingIsCorrect() throws Exception{

        //given
        Integer id = 1234;
        Manufacturer manufacturer = defaultManufacturer(id);
        given(manufacturerService.getManufacturer(id)).willReturn(Optional.of(manufacturer));

        //expect
       MvcResult result = mockMvc.perform(get("/manufacturers/detail?id={id}",id).accept(MediaType.TEXT_HTML))
               .andDo(print())
               .andExpect(status().isOk())
               .andExpect(view().name("manufacturers/detail"))
               .andExpect(model().attribute("manufacturer", manufacturer))
               .andReturn();
        String content = result.getResponse().getContentAsString();
       //and then
        assertThat(content.contains("<h1 >Manufacturer 100</h1>"));
    }

    @Test
    void ensureGetManufacturerViewRoutingIsCorrectForNonExistingCustomer() throws Exception{

        //given
        Integer id = 1234;
        when(manufacturerService.getManufacturer(id)).thenReturn((Optional.empty()));

        //expect
        MvcResult result = mockMvc.perform(get("/manufacturers/detail?id={id}",id).accept(MediaType.TEXT_HTML))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attribute("manufacturers", Collections.emptyList()))
                .andExpect(view().name("manufacturers/browse"))
                .andReturn();
    }




    @Test
    void ensureDeleteViewRoutingIsCorrect() throws Exception{

        //given
        Integer id = 1234;
       Manufacturer manufacturer = defaultManufacturer(id);
        given(manufacturerService.getManufacturer(id)).willReturn(Optional.of(manufacturer));

        //expect
        MvcResult result = mockMvc.perform(get("/manufacturers/delete").param("id", String.valueOf(id)).accept(MediaType.TEXT_HTML))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/manufacturers"))
                .andReturn();

    }
}