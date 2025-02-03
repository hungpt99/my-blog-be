package com.hungpt.myblog.controller;

import com.hungpt.myblog.controller.admin.DashboardController;
import com.hungpt.myblog.service.MessageSourceService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@Tag("unit")
@ExtendWith(MockitoExtension.class)
@DisplayName("Unit tests for DashboardController")
class DashboardControllerTest {
    @InjectMocks
    private DashboardController dashboardController;

    @Mock
    private MessageSourceService messageSourceService;


}
