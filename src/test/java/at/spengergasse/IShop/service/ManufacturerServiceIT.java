package at.spengergasse.IShop.service;

import at.spengergasse.IShop.domain.Customer;
import at.spengergasse.IShop.domain.Manufacturer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static at.spengergasse.IShop.domain.DomainFixtures.defaultCustomer;
import static at.spengergasse.IShop.domain.DomainFixtures.defaultManufacturer;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ManufacturerServiceIT {

    @Autowired
    ManufacturerService manufacturerService;

    @Test
    void ensureCreatingManufacturerWorksProperly(){
        // given
        Integer manufacturer_id =  93487;
        String name = "Microsoft";
        String headquarter = "New York";
        String phone = "+43 660 47584";
        String email = "office@outlook.com";
        String payment_note = "only cash";
        Integer rating = 5;
        // when
        Manufacturer createdManufacturer = manufacturerService.createManufacturer(manufacturer_id,name,headquarter,phone,email,payment_note,rating);

        // then
        assertThat(createdManufacturer).isNotNull();
        assertThat(createdManufacturer.getManufacturer_id()).isEqualTo(manufacturer_id);
        assertThat(createdManufacturer.getEmail()).isEqualTo(email);
        assertThat(createdManufacturer.getName()).isEqualTo(name);
        assertThat(createdManufacturer.getRating()).isEqualTo(rating);
    }
    @Test
    void ensureCreatingManufacturer2WorksProperly(){

        //given
        Manufacturer manufacturer = defaultManufacturer(1233);

        // when
        Manufacturer createdManufacturer = manufacturerService.createManufacturer(manufacturer);

        // then
        assertThat(createdManufacturer).isNotNull();
        assertThat(createdManufacturer.getManufacturer_id()).isEqualTo(manufacturer.getManufacturer_id());
        assertThat(createdManufacturer.getEmail()).isEqualTo(manufacturer.getEmail());
        assertThat(createdManufacturer.getName()).isEqualTo(manufacturer.getName());
        assertThat(createdManufacturer.getRating()).isEqualTo(manufacturer.getRating());
    }
    @Test
    void ensureDeleteManufacturerWorksProperly(){
        //given
        Manufacturer createdManufacturer = manufacturerService.createManufacturer(defaultManufacturer(1235));

        // when
        manufacturerService.deleteManufacturer(createdManufacturer.getManufacturer_id());

        // then
        assertThat(manufacturerService.getManufacturer(createdManufacturer.getManufacturer_id())).isEqualTo(Optional.empty());
    }
    @Test
    void ensureUpdateManufacturerWorksProperly(){
        //given
        Manufacturer createdManufacturer = manufacturerService.createManufacturer(defaultManufacturer(3245));
        Manufacturer updateManufacturer = createdManufacturer;
        updateManufacturer.setName("Apple");

        // when
        manufacturerService.updateManufacturer(createdManufacturer.getManufacturer_id(),updateManufacturer);
        Manufacturer newManufacturer = manufacturerService.getManufacturer(updateManufacturer.getManufacturer_id()).get();

        // then
        assertThat(newManufacturer).isNotNull();
        assertThat(newManufacturer.getManufacturer_id()).isEqualTo(createdManufacturer.getManufacturer_id());
        assertThat(newManufacturer.getName()).isEqualTo("Apple");

    }


    @Test
    void ensureGetManufacturersWorksProperly(){
        //given
        Manufacturer createdManufacturer1 = manufacturerService.createManufacturer(defaultManufacturer(3456));
        Manufacturer createdManufacturer2 = manufacturerService.createManufacturer(defaultManufacturer(345345));

        //when
        List<Manufacturer> savedManufacturer = manufacturerService.getManufacturers();


        // then
        assertThat(savedManufacturer).isNotNull().contains(createdManufacturer1);
        assertThat(savedManufacturer).isNotNull().contains(createdManufacturer2);

    }
    @Test
    void ensureGetManufacturerWorksProperly(){
        // when
        Manufacturer createdManufacturer = manufacturerService.createManufacturer(defaultManufacturer(84356));
        Manufacturer savedManufacturer = manufacturerService.getManufacturer(createdManufacturer.getManufacturer_id()).get();


        // then
        assertThat(savedManufacturer).isNotNull();
        assertThat(savedManufacturer.getManufacturer_id()).isEqualTo(createdManufacturer.getManufacturer_id());
        assertThat(savedManufacturer.getEmail()).isEqualTo(createdManufacturer.getEmail());
        assertThat(savedManufacturer.getName()).isEqualTo(createdManufacturer.getName());
        assertThat(savedManufacturer.getRating()).isEqualTo(createdManufacturer.getRating());

    }
}