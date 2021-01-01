package at.spengergasse.IShop.persistence;

import at.spengergasse.IShop.domain.Manufacturer;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static at.spengergasse.IShop.domain.DomainFixtures.defaultManufacturer;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assumptions.assumeThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class ManufacturerRepositoryTest {

    @Autowired
    private ManufacturerRepository manufacturerRepository;


    @BeforeEach
    void setup(){
        assumeThat(manufacturerRepository).isNotNull();
    }

    @Test
    void ensureSavingAndReReadingWorksProperly() {
        //given
        Manufacturer m = defaultManufacturer();

        //when
        Manufacturer saved = manufacturerRepository.save(m);

        //then
        assertThat(saved).isNotNull();
        assertThat(saved.getManufacturer_id()).isNotNull();
        assertThat(saved.getVersion()).isNotNull();
        assertThat(saved.getManufacturer_id()).isEqualTo(327648);
    }
    @Test
    void findAllByRating() {
        //given
        Manufacturer m = defaultManufacturer();
        manufacturerRepository.save(m);

        //when
        List<Manufacturer> manufacturers = manufacturerRepository.findAllByRating(3);

        //then
        assertThat(manufacturers).isNotNull().contains(m);
    }
}