package at.spengergasse.IShop.persistence;

import at.spengergasse.IShop.domain.Category;
import at.spengergasse.IShop.domain.Manufacturer;
import at.spengergasse.IShop.domain.Product;
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
import static at.spengergasse.IShop.domain.DomainFixtures.defaultProduct;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assumptions.assumeThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ManufacturerRepository manufacturerRepository;

    @BeforeEach
    void setup(){
        assumeThat(productRepository).isNotNull();
    }

    @Test
    void ensureSavingAndReReadingWorksProperly() {
        //given
        Manufacturer m = defaultManufacturer();
        Manufacturer savedManufacturer = manufacturerRepository.save(m);

        Product p = defaultProduct(savedManufacturer);

        //when
        Product saved2 = productRepository.save(p);

        //then
        assertThat(saved2).isNotNull();
        assertThat(saved2.getEan()).isNotNull().isEqualTo(02357);
        assertThat(saved2.getVersion()).isNotNull();

    }
    @Test
    void findAllByRating() {
        //given
        Manufacturer m = defaultManufacturer();
        Manufacturer savedManufacturer = manufacturerRepository.save(m);

        Product p = defaultProduct(savedManufacturer);
        productRepository.save(p);

        //when
        List<Product> products = productRepository.findAllByNameLike("Iphone%");

        //then
        assertThat(products).isNotNull().contains(p);
    }
    @Test
    void findAllByManufacturerName(){
        //given
        Manufacturer m = defaultManufacturer();
        Manufacturer savedManufacturer = manufacturerRepository.save(m);

        Product p = defaultProduct(savedManufacturer);
        productRepository.save(p);

        //when
        List<Product> products = productRepository.findAllByManufacturerName("Microsoft");

        //then
        assertThat(products).isNotNull().contains(p);
    }
}