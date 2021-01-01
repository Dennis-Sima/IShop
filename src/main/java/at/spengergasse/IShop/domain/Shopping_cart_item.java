package at.spengergasse.IShop.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@SuppressWarnings("ALL")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity
@Table(name="shopping_cart_item")
public class Shopping_cart_item extends AbstractPersistable<Long> {

    @NotNull
    @Column(name = "version", nullable = false)
    @Version
    private Integer version;

    @NotNull
    @ManyToOne
    private Shopping_cart shoppingCart;

    @NotNull
    @ManyToOne
    private Product product;
}
