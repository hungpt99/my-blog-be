package com.hungpt.myblog.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

/**
 * Config entity for storing various configuration settings.
 */
@Entity
@Table(name = "configs")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Config extends AbstractBaseEntity {

    @Column(unique = true, nullable = false)
    private String key;  // The configuration key (e.g., "site_title", "theme_color")

    @Column(nullable = false)
    private String value;  // The value for the configuration (e.g., "My Blog", "#FF5733")

    private String description;  // Optional description to explain the config (e.g., "The title of the blog site")

    private String type;  // The type of config, can be string, boolean, integer, etc. (e.g., "String", "Integer")
}
