package com.hungpt.myblog.controller.admin;

import com.hungpt.myblog.constants.ApiConstants;
import com.hungpt.myblog.controller.AbstractBaseController;
import com.hungpt.myblog.dto.request.ConfigRequest;
import com.hungpt.myblog.dto.response.ConfigResponse;
import com.hungpt.myblog.dto.response.DetailedErrorResponse;
import com.hungpt.myblog.dto.response.ErrorResponse;
import com.hungpt.myblog.dto.response.SuccessResponse;
import com.hungpt.myblog.dto.response.auth.TokenResponse;
import com.hungpt.myblog.service.ConfigService;
import com.hungpt.myblog.service.MessageSourceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller to manage configuration settings for the application.
 */
@RestController
@RequestMapping(ApiConstants.API_ADMIN_PREFIX + ApiConstants.API_CONFIG_PREFIX)
@RequiredArgsConstructor
public class ConfigController extends AbstractBaseController {

    private final ConfigService configService;

    private final MessageSourceService messageSourceService;

    /**
     * Retrieves the current configuration.
     *
     * @return the current configuration settings.
     */
    @GetMapping
    @Operation(
            summary = "Get config endpoint",
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
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ConfigResponse>> getConfig() {
        return ResponseEntity.ok(configService.getConfigs());
    }

    /**
     * Updates the configuration settings.
     *
     * @param configDto the updated configuration data.
     * @return the updated configuration.
     */
    @PutMapping
    @Operation(
            summary = "Update config endpoint",
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
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<SuccessResponse> updateConfig(@RequestBody @Valid ConfigRequest configDto) {
        configService.updateConfig(configDto);
        return ResponseEntity.ok(SuccessResponse.builder()
                .message(messageSourceService.get("logout_successfully"))
                .build());
    }


}
