package at.spengergasse.IShop.persistence;

import at.spengergasse.IShop.domain.Order_item;
import at.spengergasse.IShop.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Order_itemRepository extends JpaRepository<Order_item, Long> {

    List<Order_item> findAllByOrderId(long id);
    List<Order_item> findAllByProduct(Product product);

   List<Order_item> findAllByProductEan(Integer ean);
}
