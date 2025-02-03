package com.hungpt.myblog.dto.response;

import com.hungpt.myblog.entity.AbstractBaseEntity;

public interface IResponse<T extends AbstractBaseResponse, E extends AbstractBaseEntity> {
    T fromEntity(E entity);
}
