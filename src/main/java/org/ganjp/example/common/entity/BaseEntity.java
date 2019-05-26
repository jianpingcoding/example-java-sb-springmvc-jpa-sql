package org.ganjp.example.common.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.ganjp.example.common.enumeration.Status;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity implements Serializable, Cloneable {
    @Column(name = "created_at", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date createdAt;

    @Column(name = "created_by", nullable = false, updatable = false)
    private String createdBy;

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date updatedAt;

    @Column(name = "updated_by")
    private String updatedBy;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @PrePersist
    void preInsert() {
        if (createdAt == null) createdAt = new Date();
        if (createdBy == null) createdBy = "unknown";
        if (status == null) status = Status.active;
    }

    @PreUpdate()
    void preUpdate() {
        if (updatedAt == null) updatedAt = new Date();
        if (updatedBy == null) updatedBy = "unknown";
    }
}

//@JsonIgnoreProperties(
//    value = {"createdAt", "updatedAt"},
//    allowGetters = true
//)