package at.spengergasse.IShop.persistence;

import at.spengergasse.IShop.domain.Order_item;
import at.spengergasse.IShop.domain.Product;
import at.spengergasse.IShop.domain.Shopping_cart;
import at.spengergasse.IShop.domain.Shopping_cart_item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Shopping_cart_itemRepository extends JpaRepository<Shopping_cart_item, Long> {

    List<Shopping_cart_item> findAllByShoppingCartId(long s);
    List<Shopping_cart_item> findAllByProduct(Product product);

    List<Shopping_cart_item> findAllByProductEan(Integer ean);
}
