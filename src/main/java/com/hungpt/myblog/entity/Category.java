package com.hungpt.myblog.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * Entity representing a Category (a collection of posts or content).
 */
@Entity
@Table(name = "categories")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Category extends AbstractBaseEntity {

    // The name of the category
    @Column(nullable = false, length = 255)
    private String name;

    // A description of the category
    @Column(nullable = true, columnDefinition = "TEXT")
    private String description;
}
