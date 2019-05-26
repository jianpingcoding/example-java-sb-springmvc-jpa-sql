package org.ganjp.example.bm.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.ganjp.example.bm.entity.User;
import org.ganjp.example.common.entity.BaseEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(exclude = {"users"})
@ToString(callSuper = true, exclude = "users")
@Entity
@Table(name = "bm_role")
@JsonPropertyOrder({ "id", "name", "updatedAt", "updatedBy", "createdAt", "createdBy", "status"})
public class Role extends BaseEntity {
    @Id
    @GeneratedValue(generator = "idGenerator")
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @Column(name = "id", length = 32)
    private String id;

    @Column(name = "name")
    private String name;

    @ManyToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="roles")
    @JsonIgnore
    private Set<User> users = new HashSet<>();
}
