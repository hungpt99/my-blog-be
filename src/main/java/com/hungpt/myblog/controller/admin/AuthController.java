package com.hungpt.myblog.controller.admin;

import com.hungpt.myblog.annotation.AdminController;
import com.hungpt.myblog.constants.ApiConstants;
import com.hungpt.myblog.controller.AbstractBaseController;
import com.hungpt.myblog.dto.request.auth.LoginRequest;
import com.hungpt.myblog.dto.response.DetailedErrorResponse;
import com.hungpt.myblog.dto.response.ErrorResponse;
import com.hungpt.myblog.dto.response.SuccessResponse;
import com.hungpt.myblog.dto.response.auth.TokenResponse;
import com.hungpt.myblog.dto.response.user.UserResponse;
import com.hungpt.myblog.service.AuthService;
import com.hungpt.myblog.service.MessageSourceService;
import com.hungpt.myblog.service.PasswordResetTokenService;
import com.hungpt.myblog.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.hungpt.myblog.util.Constants.SECURITY_SCHEME_NAME;

@RestController
@AdminController()
@RequestMapping(ApiConstants.API_ADMIN_PREFIX + ApiConstants.API_AUTH_PREFIX)
@RequiredArgsConstructor
@Tag(name = "001. Admin Auth", description = "Auth API")
public class AuthController extends AbstractBaseController {
    private final AuthService authService;

    private final UserService userService;

    private final MessageSourceService messageSourceService;

    @PostMapping(ApiConstants.API_VERSION + ApiConstants.API_ADMIN_LOGIN)
    @Operation(
            summary = "Login endpoint",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successful operation",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = TokenResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Bad credentials",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ErrorResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "422",
                            description = "Validation failed",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = DetailedErrorResponse.class)
                            )
                    )
            }
    )
    public ResponseEntity<TokenResponse> login(
            @Parameter(description = "Request body to login", required = true)
            @RequestBody @Validated final LoginRequest request
    ) {
        return ResponseEntity.ok(authService.login(request.getEmail(), request.getPassword(), request.getRememberMe()));
    }


    @GetMapping(ApiConstants.API_VERSION + ApiConstants.API_ADMIN_VERIFY_EMAIL)
    @Operation(
            summary = "E-mail verification endpoint",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successful operation",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = SuccessResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not found verification token",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ErrorResponse.class)
                            )
                    )
            }
    )
    public ResponseEntity<SuccessResponse> emailVerification(
            @Parameter(name = "token", description = "E-mail verification token", required = true)
            @PathVariable("token") final String token
    ) {
        userService.verifyEmail(token);

        return ResponseEntity.ok(SuccessResponse.builder()
                .message(messageSourceService.get("your_email_verified"))
                .build());
    }

    @GetMapping(ApiConstants.API_VERSION + ApiConstants.API_ADMIN_REFRESH_TOKEN)
    @Operation(
            summary = "Refresh endpoint",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successful operation",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = TokenResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad request",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ErrorResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Bad credentials",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ErrorResponse.class)
                            )
                    )
            }
    )
    public ResponseEntity<TokenResponse> refresh(
            @Parameter(description = "Refresh token", required = true)
            @RequestHeader("Authorization") @Validated final String refreshToken
    ) {
        return ResponseEntity.ok(authService.refreshFromBearerString(refreshToken));
    }



    @GetMapping(ApiConstants.API_VERSION + ApiConstants.API_LOGOUT)
    @Operation(
            summary = "Logout endpoint",
            security = @SecurityRequirement(name = SECURITY_SCHEME_NAME),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successful operation",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = SuccessResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad request",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ErrorResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Bad request",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ErrorResponse.class)
                            )
                    )
            }
    )
    public ResponseEntity<SuccessResponse> logout() {
        authService.logout(userService.getUser());

        return ResponseEntity.ok(SuccessResponse.builder()
                .message(messageSourceService.get("logout_successfully"))
                .build());
    }

    @GetMapping(ApiConstants.API_VERSION + "/me")
    @Operation(
            summary = "Get authenticated user information",
            security = @SecurityRequirement(name = SECURITY_SCHEME_NAME),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successful operation",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UserResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ErrorResponse.class)
                            )
                    )
            }
    )
    public ResponseEntity<UserResponse> getAuthenticatedUser() {
        return ResponseEntity.ok(UserResponse.convert(userService.getUser()));
    }
}
