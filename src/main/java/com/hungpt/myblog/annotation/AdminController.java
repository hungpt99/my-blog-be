package com.hungpt.myblog.annotation;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.lang.annotation.*;

@Target(ElementType.TYPE) // Only for classes
@Retention(RetentionPolicy.RUNTIME)
@Documented
@RestController
@RequestMapping(AdminController.ADMIN_PREFIX) // Apply default prefix
public @interface AdminController {
    String ADMIN_PREFIX = "/admin"; // Prefix for all admin controllers
}
