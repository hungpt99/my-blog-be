package com.hungpt.myblog.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)  // This enables the Auditing functionality in Spring Data JPA
@Where(clause = "deleted_at IS NULL") // Optional: Soft delete mechanism
public abstract class AbstractBaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;  // Auto-populated on entity creation

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;  // Auto-populated on entity update

    @CreatedBy
    @Column(name = "created_by", nullable = false, updatable = false)
    private String createdBy;  // Auto-populated with the username of the creator

    @LastModifiedBy
    @Column(name = "updated_by", nullable = false)
    private String updatedBy;  // Auto-populated with the username of the last modifier

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;  // Timestamp for soft deletion (if applicable)

    @Column(name = "deleted_by")
    private String deletedBy;  // Stores who deleted the entity (if applicable)

    @Column(name = "status", nullable = false)
    private Boolean isDeleted;  // For example: ACTIVE, INACTIVE, DELETED, etc.

    // Soft delete logic: mark the entity as deleted instead of physically removing it
    public void softDelete(String deletedBy) {
        this.deletedAt = LocalDateTime.now();  // Set the deletion timestamp
        this.deletedBy = deletedBy;  // Set the user who performed the deletion
        this.isDeleted = false;
    }
}
