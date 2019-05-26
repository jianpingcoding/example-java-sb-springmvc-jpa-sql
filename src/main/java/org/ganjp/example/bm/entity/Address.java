package org.ganjp.example.bm.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;
import org.ganjp.example.bm.entity.User;
import org.ganjp.example.common.entity.BaseEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Objects;

@Data
@Entity
//@EqualsAndHashCode(exclude = {"user"})
//@ToString(callSuper = true, exclude = "user")
@Table(name = "bm_address")
@JsonPropertyOrder({ "id", "city", "country", "updatedAt", "updatedBy", "createdAt", "createdBy", "status"})
public class Address extends BaseEntity {
    @Id
    @GeneratedValue(generator = "idGenerator")
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @Column(name = "id")
    private String id;

    @Column(name = "city")
    private String city;

    @Column(name = "country")
    private String country;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getCity(), getCountry());
    }

    @Override
    public boolean equals(Object o) {
        Address address = (Address) o;
        return this.city.equals(address.getCity())
                && this.country.equals(address.getCountry());
    }

    @Override
    public String toString() {
        return "Address{" +
                "id='" + this.id + '\'' +
                ", name='" + this.city + '\'' +
                '}';
    }

}