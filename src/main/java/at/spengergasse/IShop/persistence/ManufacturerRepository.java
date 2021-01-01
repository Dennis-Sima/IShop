package at.spengergasse.IShop.persistence;

import at.spengergasse.IShop.domain.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ManufacturerRepository  extends JpaRepository<Manufacturer,Integer> {

    List<Manufacturer> findAllByRating(int rating);

}