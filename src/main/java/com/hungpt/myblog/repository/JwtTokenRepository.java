package com.hungpt.myblog.repository;

import com.hungpt.myblog.entity.JwtToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface JwtTokenRepository extends CrudRepository<JwtToken, UUID> {
    Optional<JwtToken> findByTokenOrRefreshToken(String token, String refreshToken);

    Optional<JwtToken> findByUserIdAndRefreshToken(UUID id, String refreshToken);
}
