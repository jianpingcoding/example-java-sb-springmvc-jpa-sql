/**
 *
 * @GenericGenerator(name="jpa-uuid", strategy="uuid") @GeneratedValue(generator = "jpa-uuid"), @GeneratedValue(strategy= GenerationType.AUTO)
 */
package org.ganjp.example.bm.entity;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.ganjp.example.common.entity.BaseEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
//@EqualsAndHashCode(exclude = {"roles", "addresses"})
//@ToString(callSuper = true, exclude = "addresses")
@Entity
@Table(name = "bm_user")
@JsonPropertyOrder({ "id", "name", "mobileNumber", "email", "password", "profile", "addresses", "roles", "updatedAt", "updatedBy", "createdAt", "createdBy", "status"})
public class User extends BaseEntity {
    @Id
    @GeneratedValue(generator = "idGenerator")
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @Column(name = "id", length = 32)
    private String id;

    @Size(max = 32)
    @Column(name = "name", length = 32, unique = true)
    private String name;

    @Size(max = 32)
    @Column(name = "mobile_number", length = 32, unique = true)
    private String mobileNumber;

    @Size(max = 100)
    @Column(name = "email", length = 100, unique = true)
    private String email;

    @NotEmpty(message="Password is required")
    @Size(max=128)
    @Column(name = "password", length = 128)
    private String password;

    // Using a Shared Primary Key
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "user")
    private Profile profile;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "user")
    private Set<Address> addresses = new HashSet<>();

    @ManyToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "bm_user_role",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles = new HashSet<>();


    // Implementing with a Foreign Key in JPA
//    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    @JoinColumn(name = "profile_id", referencedColumnName = "id")
//    private Profile profile;

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getName(), getMobileNumber(), getEmail(), getPassword());
    }

    @Override
    public boolean equals(Object o) {
        User user = (User) o;
        return this.name.equals(user.getName())
                && this.mobileNumber.equals(user.getMobileNumber())
                && this.email.equals(user.getEmail())
                && this.password.equals(user.getPassword());
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + this.id + '\'' +
                ", name='" + this.name + '\'' +
                '}';
    }
}