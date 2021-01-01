package at.spengergasse.IShop.presentation.web;

import at.spengergasse.IShop.domain.Customer;
import at.spengergasse.IShop.domain.Manufacturer;
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

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static at.spengergasse.IShop.domain.DomainFixtures.defaultCustomer;
import static at.spengergasse.IShop.domain.DomainFixtures.defaultManufacturer;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assumptions.assumeThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ManufacturerControllerTest {

    @Mock
    private  ManufacturerService manufacturerService;
    @Mock
    private  OrderItemService orderItemService;
    @Mock
    private  ShoppingCartItemService shoppingCartItemService;
    @Mock
    private  ProductService productService;
    @Mock
    private ManufacturerController manufacturerController;

    @Mock
    private Model model;

    @MockBean
    private BindingResult bindingResult;

    @BeforeEach
    void setup(){
        assumeThat(manufacturerService).isNotNull();
        assumeThat(orderItemService).isNotNull();
        assumeThat(shoppingCartItemService).isNotNull();
        assumeThat(productService).isNotNull();
        assumeThat(model).isNotNull();
        manufacturerController = new ManufacturerController(manufacturerService,orderItemService,shoppingCartItemService,productService);
    }

    @Test
    void ensureViewRoutingGetManufacturerIsCorrect(){

        //given
        Integer id = 2344;
        Manufacturer manufacturer = defaultManufacturer(id);
        given(manufacturerService.getManufacturer(id)).willReturn(Optional.of(manufacturer));

        //when
        String view = manufacturerController.getManufacturer(id, model);

        //then
        assertThat(view).isEqualTo("manufacturers/detail");
        verify(model, times(1)).addAttribute("manufacturer", manufacturer);
        verifyNoMoreInteractions(manufacturerService);
        verifyNoMoreInteractions(model);


    }

    @Test
    void ensureViewRoutingGetManufacturerIsCorrectForNonExistingCustomer(){

        //given
        Integer id = 2344;
        given(manufacturerService.getManufacturer(id)).willReturn(Optional.empty());

        //when
        String view = manufacturerController.getManufacturer(id, model);

        //then
        assertThat(view).isEqualTo("manufacturers/browse");
        verify(model, times(1)).addAttribute("manufacturers", Collections.emptyList());
        verifyNoMoreInteractions(manufacturerService);
        verifyNoMoreInteractions(model);
    }

    @Test
    void ensureViewRoutingGetManufacturersIsCorrect() {
        //given
        List<Manufacturer> manufacturers = List.of(defaultManufacturer(9365), defaultManufacturer(93563));
        when(manufacturerService.getManufacturers()).thenReturn(manufacturers);


        //when
        String view = manufacturerController.getAllManufacturers(model);

        //then
        assertThat(view).isEqualTo("manufacturers/browse");
        verify(model, times(1)).addAttribute("manufacturers", manufacturers);
        verifyNoMoreInteractions(manufacturerService);
        verifyNoMoreInteractions(model);
    }
    @Test
    void ensureViewRoutingAddManufacturersIsCorrect() {
        //given
        Manufacturer manufacturer = new Manufacturer();

        //when
        String view = manufacturerController.showAddManufacturerForm(model);

        //then
        assertThat(view).isEqualTo("manufacturers/add");
        verify(model, times(1)).addAttribute("manufacturer", manufacturer);
        verifyNoMoreInteractions(manufacturerService);
        verifyNoMoreInteractions(model);
    }

    @Test
    void ensureAddManufacturerSubmissionFormRoutingIsCorrect() {
        //given
        Integer id = 2344;
        Manufacturer manufacturer = defaultManufacturer(id);
        given(manufacturerService.createManufacturer(manufacturer)).willReturn(manufacturer);
        BindingResult result = new BeanPropertyBindingResult(manufacturer, "manufacturer");

        //when
        String view = manufacturerController.handleAddManufacturerFormSubmission(manufacturer,result, model);

        //then
        assertThat(view).isEqualTo("redirect:/manufacturers");
    }
    @Test
    void ensureAddManufacturerSubmissionFormRoutingIsCorrectForNonExistingCustomer() {
        //given
        BindingResult result = new BeanPropertyBindingResult(null, "manufacturer");
        result.addError(new ObjectError("manufacturer", "Manufacturer must not be empty"));

        //when
        String view = manufacturerController.handleAddManufacturerFormSubmission(null, result, model);

        //then
        assertThat(view).isEqualTo("manufacturers/add");
        verifyNoMoreInteractions(manufacturerService);
        verifyNoMoreInteractions(model);
    }

    @Test
    void ensureEditManufacturerViewRoutingIsCorrect() {
        //given
        Integer id = 2344;
        Manufacturer manufacturer = defaultManufacturer(id);
        given(manufacturerService.getManufacturer(id)).willReturn(Optional.of(manufacturer));


        //when
        String view = manufacturerController.showEditManufacturerForm(id, model);

        //then
        assertThat(view).isEqualTo("manufacturers/edit");
        verify(model, times(1)).addAttribute("manufacturer", manufacturer);
        verifyNoMoreInteractions(manufacturerService);
        verifyNoMoreInteractions(model);
    }
    @Test
    void ensureEditManufacturerViewRoutingIsCorrectForNonExistingCustomer() {
        //given
        Integer id = 2344;

        //when
        String view = manufacturerController.showEditManufacturerForm(id, model);

        //then
        assertThat(view).isEqualTo("redirect:/manufacturers");
    }

    @Test
    void ensureEditManufacturerSubmissionFormRoutingIsCorrect() {
        //given
        Integer id = 2344;
        Manufacturer manufacturer = defaultManufacturer(id);

        BindingResult result = new BeanPropertyBindingResult(manufacturer, "manufacturer");

        //when
        String view = manufacturerController.handleEditManufacturerFormSubmission(id, manufacturer,result, model);

        //then
        assertThat(view).isEqualTo("redirect:/manufacturers");
    }
    @Test
    void ensureEditManufacturerSubmissionFormRoutingIsCorrectForNonExistingCustomer() {
        //given
        Integer id = 2344;
        BindingResult result = new BeanPropertyBindingResult(null, "manufacturer");
        result.addError(new ObjectError("manufacturer", "Manufacturer must not be empty"));

        //when
        String view = manufacturerController.handleEditManufacturerFormSubmission(id, null,result, model);

        //then
        assertThat(view).isEqualTo("manufacturers/edit");
        verifyNoMoreInteractions(manufacturerService);
        verifyNoMoreInteractions(model);
    }
}