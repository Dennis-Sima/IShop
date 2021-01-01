package at.spengergasse.IShop.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.*;
import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity
@Table(name="costumer")
public class Customer extends AbstractPersistable<Long> {

    @NotNull
    @Column(name = "version", nullable = false)
    @Version
    private Integer version;

    @NotNull
    @NotBlank
    @Size(min = 2, max = 45)
    @Column(name = "first_name", nullable = false)
    private String first_name;

    @NotNull
    @Size(min = 2, max = 45)
    @Column(name = "last_name", nullable = false)
    private String last_name;

    @NotNull
    @Column(name = "gebdate", nullable = false)
    private Date gebdate;

    @NotNull
    @Email
    @Size(min = 2, max = 45)
    @Column(name = "email", nullable = false)
    private String email;

    @Size(min = 2, max = 45)
    @Column(name = "phone", nullable = true)
    private String phone;

    @NotNull
    @Size(min = 2, max = 45)
    @Column(name = "bank_details", nullable = false)
    private String bank_details;

    @NotNull
    @Column(name = "newsletter", nullable = false)
    private Boolean newsletter;

    @NotNull
    @Max(999)
    @Column(name = "credit", nullable = false)
    private Double credit;

    @NotNull
    @Size(min = 2, max = 70)
    @Column(name = "street", nullable = false)
    private String street;

    @NotNull
    @Max(999999999)
    @Column(name = "zipcode", nullable = false)
    private Long zipcode;

    @NotNull
    @Column(name = "city", nullable = false)
    private String city;

    @NotNull
    @Column(name = "country", nullable = false)
    private String country;

    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "housenr", nullable = false)
    private String house_nr;

    @Override
    protected void setId(Long id) {
        super.setId(id);
    }
}
