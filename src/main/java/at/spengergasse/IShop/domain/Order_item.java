package at.spengergasse.IShop.domain;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.net.Inet4Address;
import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity
@Table(name="order_item")
public class Order_item extends AbstractPersistable<Long> {

    @NotNull
    @Column(name = "version", nullable = false)
    @Version
    private Integer version;

    @ManyToOne
    @NotNull
    private Order order;

    @ManyToOne
    @NotNull
    private Product product;
}
