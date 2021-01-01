package at.spengergasse.IShop.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity
@Table(name="orders") //orders instead of order, because springboot can't prepare statement with order -> maybe a bug in springboot or Intellij
public class Order extends AbstractPersistable<Long> {
    @NotNull
    @Column(name = "version", nullable = false)
    @Version
    private Integer version;

    @NotNull
    @Column(nullable = false, name = "deliveryDate")
    private Date deliveryDate;

    @NotNull
    @Column(nullable = false, name = "purchaseDate")
    private Date purchaseDate;

    @ManyToOne
    private Customer customer;

    @OneToMany(mappedBy = "order")
    private List<Order_item> order_items;

}
