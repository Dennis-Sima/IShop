package at.spengergasse.IShop.service;

import at.spengergasse.IShop.domain.Customer;
import at.spengergasse.IShop.foundation.NamedLoggerFactory;
import at.spengergasse.IShop.persistence.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor

//@Slf4j
@Service
@Transactional(readOnly = true)
public class CustomerService {

    protected  final Logger log = LoggerFactory.getLogger(this.getClass());
    protected final Logger DATA_QUALITY_LOGGER = NamedLoggerFactory.getDataQualityLogger();
    private final CustomerRepository customerRepository;

    @Transactional(readOnly = false)
    public Customer createCustomer(@Valid @NotBlank String fName, String lName, String phone, Date gebDatum, String email, String bankDetails, boolean newsletter, double credit, String street, Long zipcode, String city, String country, String houseNr)
    {
        log.debug("beginning createCustomer with firstname={} and lastname={}", fName, lName);
        Customer customer = Customer.builder()
                                    .first_name(fName)
                                    .last_name(lName)
                                    .gebdate(gebDatum)
                                    .email(email)
                                    .phone(phone)
                                    .bank_details(bankDetails)
                                    .newsletter(newsletter)
                                    .credit(credit)
                                    .street(street)
                                    .zipcode(zipcode)
                                    .city(city)
                                    .country(country)
                                    .house_nr(houseNr)
                                    .build();

        return customerRepository.save(customer);
    }

    @Transactional(readOnly = false)
    public Customer createCustomer(Customer customer)
    {
        return customerRepository.save(customer);
    }

    @Transactional(readOnly = false)
    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }

    @Transactional(readOnly = false)
    public void updateCustomer(Long id, final Customer updatedCustomer) {
        log.debug("beginning updateCustomer with id={}", id);

        Optional<Customer> customer = customerRepository.findById(id);

        if(customer.isEmpty()) {
            NamedLoggerFactory.getDataQualityLogger().warn("Cannot load Customer with id={}, id");
            return;

        }
        customer.map(c -> {
                              c.setFirst_name(updatedCustomer.getFirst_name());
                              c.setLast_name(updatedCustomer.getLast_name());
                              c.setGebdate(updatedCustomer.getGebdate());
                              c.setEmail(updatedCustomer.getEmail());
                              c.setPhone(updatedCustomer.getPhone());
                              c.setBank_details(updatedCustomer.getBank_details());
                              c.setNewsletter(updatedCustomer.getNewsletter());
                              c.setCredit(updatedCustomer.getCredit());
                              c.setStreet(updatedCustomer.getStreet());
                              c.setZipcode(updatedCustomer.getZipcode());
                              c.setCity(updatedCustomer.getCity());
                              c.setCountry(updatedCustomer.getCountry());
                              c.setHouse_nr(updatedCustomer.getHouse_nr());
                              return c;
                          })
                          .map(customerRepository::save);
    }

    public List<Customer> getCustomers()
    {
        return customerRepository.findAll();
    }

    public Optional<Customer> getCustomer(Long id)
    {
        return customerRepository.findById(id);
    }



}
