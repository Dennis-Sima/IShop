package at.spengergasse.IShop.presentation.api;

import at.spengergasse.IShop.domain.Customer;
import at.spengergasse.IShop.presentation.exceptions.NoDataFoundException;
import at.spengergasse.IShop.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerRestController {

    private final CustomerService customerService;

    @GetMapping
    public HttpEntity<List<Customer>> getAllCustomers(){
        return ResponseEntity.ok(customerService.getCustomers());
    }

    @GetMapping("/{id}")
    public HttpEntity<Customer> getCustomerById(@PathVariable Long id){
        return customerService.getCustomer(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> NoDataFoundException.forTypeAndId(Customer.class, id));
    }


}
