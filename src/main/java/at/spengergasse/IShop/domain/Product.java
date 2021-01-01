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

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity
@Table(name="product")
public class Product  {

    @NotNull
    @Column(name = "version", nullable = false)
    @Version
    private Integer version;

    @NotNull
    @Id
    @Column(name = "ean", nullable = false)
    private Integer ean;

    @NotNull
    @Size(min = 5, max = 45)
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Max(999)
    @Column(name = "price", nullable = false)
    private Double price;

    @NotNull
    @Max(999)
    @Column(name = "shipping_cost", nullable = false)
    private Double shipping_cost;

    @Column(name = "picture", nullable = true)
    private String picture;

    @NotNull
    @Max(999)
    @Column(name = "weight", nullable = false)
    private Double weight;

    @Max(999)
    @Column(name = "length", nullable = true)
    private Double length;

    @Max(999)
    @Column(name = "height", nullable = true)
    private Double height;

    @Enumerated(EnumType.STRING)
    @Column(name = "category_nr", nullable = false)
    private Category category;

    @NotNull
    @ManyToOne
    private Manufacturer manufacturer;
}
