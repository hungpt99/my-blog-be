package com.hungpt.myblog.dto.response.user;

import com.hungpt.myblog.dto.response.PaginationResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public class UsersPaginationResponse extends PaginationResponse<UserResponse> {

    protected UsersPaginationResponse(PaginationResponseBuilder<UserResponse, ?, ?> b) {
        super(b);
    }
}
