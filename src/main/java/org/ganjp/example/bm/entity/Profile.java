package org.ganjp.example.bm.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.ganjp.example.bm.entity.User;
import org.ganjp.example.common.entity.BaseEntity;
import org.ganjp.example.common.enumeration.Gender;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@EqualsAndHashCode(exclude = {"user"})
@ToString(callSuper = true, exclude = "user")
@Entity
@Table(name = "bm_profile")
@JsonPropertyOrder({ "id", "birthdate", "image", "description", "updatedAt", "updatedBy", "createdAt", "createdBy", "status"})
public class Profile extends BaseEntity {
    @Id
    @GeneratedValue(generator = "idGenerator")
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @Column(name = "id")
    private String id;

    @Size(max = 64)
    @Column(name = "first_name", length = 64)
    private String firstName;

    @Size(max = 64)
    @Column(name = "last_name", length = 64)
    private String lastName;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", length = 10)
    private Gender gender;

    @Size(max = 32)
    @Column(name = "nationality", length = 32)
    private String nationality;

    @Temporal(TemporalType.DATE)
    @Column(name = "birthdate")
    private Date birthdate;

    @Lob
    @Basic(fetch=FetchType.LAZY)
    @Column(name = "avatar")
    private byte[] avatar;

    @Size(max = 250)
    private String description;

    // Share primary key
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JsonIgnore
    private User user;

    // Implementing with a Foreign Key in JPA
//    @OneToOne(mappedBy = "profile", fetch = FetchType.LAZY)
//    private User user;

//    @OneToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "user_id", nullable = false)
//    private User user;
}
