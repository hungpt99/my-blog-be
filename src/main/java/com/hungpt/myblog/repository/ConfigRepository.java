package com.hungpt.myblog.repository;

import com.hungpt.myblog.entity.Config;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ConfigRepository extends JpaRepository<Config, UUID> {
    List<Config> findByKey(String key);
}
