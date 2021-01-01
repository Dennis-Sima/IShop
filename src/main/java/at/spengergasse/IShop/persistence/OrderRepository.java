package at.spengergasse.IShop.persistence;

import at.spengergasse.IShop.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByCustomerId(long customer);
}
