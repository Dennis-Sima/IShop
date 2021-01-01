package at.spengergasse.IShop.persistence;

import at.spengergasse.IShop.domain.Manufacturer;
import at.spengergasse.IShop.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {

    List<Product> findAllByNameLike(String name);
    List<Product> findAllByManufacturerName(String manufacturer_name);
    List<Product> findAllByManufacturer(Manufacturer manufacturer);

}
