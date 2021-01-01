package at.spengergasse.IShop.persistence;

import at.spengergasse.IShop.domain.Shopping_cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Shopping_cartRepository extends JpaRepository<Shopping_cart, Long> {

    List<Shopping_cart> findAllByCustomerId(long customerId);
}
