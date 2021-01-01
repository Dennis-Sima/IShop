package at.spengergasse.IShop.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@SuppressWarnings("ALL")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity
@Table(name="shopping_cart")
public class Shopping_cart  extends AbstractPersistable<Long> {

    @NotNull
    @Column(name = "version", nullable = false)
    @Version
    private Integer version;


    @ManyToOne
    private Customer customer;

    @OneToMany(mappedBy = "shoppingCart")
    private List<Shopping_cart_item> shopping_cart_items;
}
