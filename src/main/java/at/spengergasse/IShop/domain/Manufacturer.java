package at.spengergasse.IShop.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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
@Table(name="manufacturer")
public class Manufacturer  {

    @NotNull
    @Column(name = "version", nullable = false)
    @Version
    private Integer version;

    @NotNull
    @Id
    @Column(name = "manufacturer_id", nullable = false)
    private Integer manufacturer_id;

    @NotNull
    @Size(min = 2, max = 45)
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Size(min = 2, max = 100)
    @Column(name = "headquarter", nullable = false)
    private String headquarter;

    @NotNull
    @Size(min = 2, max = 45)
    @Column(name = "phone", nullable = false)
    private String phone;

    @NotNull
    @Size(min = 2, max = 45)
    @Column(name = "email", nullable = false)
    private String email;

    @NotNull
    @Size(min = 2, max = 45)
    @Column(name = "payment_note", nullable = false)
    private String payment_note;

    @NotNull
    @Max(5)
    @Column(name = "rating", nullable = false)
    private Integer rating;

}
