package com.hungpt.myblog.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * Entity representing a Tag (could be associated with posts or content).
 */
@Entity
@Table(name = "tags")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Tag extends AbstractBaseEntity {

    // The name of the tag
    @Column(nullable = false, unique = true, length = 255)
    private String name;

}
