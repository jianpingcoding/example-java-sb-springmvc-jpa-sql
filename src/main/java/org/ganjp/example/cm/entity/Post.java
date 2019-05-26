package org.ganjp.example.cm.entity;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.ganjp.example.common.entity.BaseEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(exclude = {"tags"})
@ToString(callSuper = true, exclude = "tags")
@Entity
@Table(name = "cm_post")
@JsonPropertyOrder({ "id", "title", "description", "content", "updatedAt", "updatedBy", "createdAt", "createdBy", "status"})
public class Post extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(unique = true)
    private String title;

    @NotNull
    @Size(max = 250)
    private String description;

    @NotNull
    @Basic(fetch=FetchType.EAGER) @Lob
    private String content;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(name = "cm_post_tags",
            joinColumns = { @JoinColumn(name = "post_id", referencedColumnName = "id") },
            inverseJoinColumns = { @JoinColumn(name = "tag_id", referencedColumnName = "id") })
    private Set<Tag> tags = new HashSet<>();

    //define a bidirectional one-to-many mapping
//    @OneToMany(cascade = CascadeType.ALL,
//            fetch = FetchType.LAZY,
//            mappedBy = "post")
//    private Set<Comment> comments = new HashSet<>();
}