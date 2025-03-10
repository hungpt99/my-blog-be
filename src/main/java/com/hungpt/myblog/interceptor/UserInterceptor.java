package com.hungpt.myblog.interceptor;

import com.hungpt.myblog.annotation.UserController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class UserInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod handlerMethod) {
            UserController userOnly = handlerMethod.getMethodAnnotation(UserController.class);
            if (userOnly == null) {
                userOnly = handlerMethod.getBeanType().getAnnotation(UserController.class);
            }

            if (userOnly != null) {
                // TODO: Replace with your actual user authentication logic
                boolean isUser = checkIfUserIsUser(request);
                if (!isUser) {
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    response.getWriter().write("Access Denied: Users only");
                    return false;
                }
            }
        }
        return true;
    }

    private boolean checkIfUserIsUser(HttpServletRequest request) {
        // Example: Check if a user has an "USER" role (replace with actual logic)
        String userRole = (String) request.getSession().getAttribute("userRole");
        return "USER".equals(userRole);
    }
}
