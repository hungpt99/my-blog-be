package com.hungpt.myblog.interceptor;

import com.hungpt.myblog.annotation.AdminController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AdminInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod handlerMethod) {
            AdminController adminOnly = handlerMethod.getMethodAnnotation(AdminController.class);
            if (adminOnly == null) {
                adminOnly = handlerMethod.getBeanType().getAnnotation(AdminController.class);
            }

            if (adminOnly != null) {
                // TODO: Replace with your actual admin authentication logic
                boolean isAdmin = checkIfUserIsAdmin(request);
                if (!isAdmin) {
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    response.getWriter().write("Access Denied: Admins only");
                    return false;
                }
            }
        }
        return true;
    }

    private boolean checkIfUserIsAdmin(HttpServletRequest request) {
        // Example: Check if a user has an "ADMIN" role (replace with actual logic)
        String userRole = (String) request.getSession().getAttribute("userRole");
        return "ADMIN".equals(userRole);
    }
}
