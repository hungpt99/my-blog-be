package com.hungpt.myblog.dto.request.auth;

import com.hungpt.myblog.dto.request.user.AbstractBaseCreateUserRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@SuperBuilder
public class RegisterRequest extends AbstractBaseCreateUserRequest {
}
