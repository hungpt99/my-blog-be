package com.hungpt.myblog.controller.admin;

import com.hungpt.myblog.constants.ApiConstants;
import com.hungpt.myblog.controller.AbstractBaseController;
import com.hungpt.myblog.dto.response.DashboardResponse;
import com.hungpt.myblog.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for Dashboard-related APIs, providing CMS statistics.
 */
@RestController
@RequestMapping(ApiConstants.API_ADMIN_PREFIX + ApiConstants.API_DASHBOARD_PREFIX)
@RequiredArgsConstructor
public class DashboardController extends AbstractBaseController {

    private final DashboardService dashboardService;

    /**
     * Get an overview of the CMS blog statistics, such as posts, comments, views, and more.
     *
     * @return ResponseEntity containing the dashboard statistics.
     */
    @GetMapping(ApiConstants.API_DASHBOARD_OVERVIEW)
    public ResponseEntity<DashboardResponse> getDashboardOverview() {
        // Fetch the statistics for the dashboard
        DashboardResponse stats = dashboardService.getDashboardStatistics();

        // Return the statistics wrapped in ResponseEntity
        return ResponseEntity.ok(stats);
    }
}
