package at.spengergasse.IShop.service;

import at.spengergasse.IShop.domain.Customer;
import at.spengergasse.IShop.domain.Manufacturer;
import at.spengergasse.IShop.persistence.CustomerRepository;
import at.spengergasse.IShop.persistence.ManufacturerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor

@Service
@Transactional(readOnly = true)
public class ManufacturerService {

    private final ManufacturerRepository manufacturerRepository;

    @Transactional(readOnly = false)
    public Manufacturer createManufacturer(@Valid @NotBlank Integer manufacturer_id,String name, String headquarter, String phone, String email, String payment_note, int rating)
    {
        Manufacturer manufacturer = Manufacturer.builder()
                                    .manufacturer_id(manufacturer_id)
                                    .name(name)
                                    .headquarter(headquarter)
                                    .phone(phone)
                                    .email(email)
                                    .payment_note(payment_note)
                                    .rating(rating)
                                    .build();

        return manufacturerRepository.save(manufacturer);
    }

    @Transactional(readOnly = false)
    public Manufacturer createManufacturer(Manufacturer manufacturer)
    {
        return manufacturerRepository.save(manufacturer);
    }

    @Transactional(readOnly = false)
    public void deleteManufacturer(Integer id) {
        manufacturerRepository.deleteById(id);
    }

    @Transactional(readOnly = false)
    public void updateManufacturer(Integer id, final Manufacturer updatedManufacturer) {
        manufacturerRepository.findById(id)
                          .map(manufacturer -> {
                              manufacturer.setManufacturer_id(updatedManufacturer.getManufacturer_id());
                              manufacturer.setName(updatedManufacturer.getName());
                              manufacturer.setHeadquarter(updatedManufacturer.getHeadquarter());
                              manufacturer.setPhone(updatedManufacturer.getPhone());
                              manufacturer.setEmail(updatedManufacturer.getEmail());
                              manufacturer.setPayment_note(updatedManufacturer.getPayment_note());
                              manufacturer.setRating(updatedManufacturer.getRating());
                              return manufacturer;
                          })
                          .map(manufacturerRepository::save);
    }

    public List<Manufacturer> getManufacturers()
    {
        return manufacturerRepository.findAll();
    }

    public Optional<Manufacturer> getManufacturer(Integer id)
    {
        return manufacturerRepository.findById(id);
    }

}
