package com.hungpt.myblog.annotation;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.lang.annotation.*;

@Target(ElementType.TYPE) // Only for classes
@Retention(RetentionPolicy.RUNTIME)
@Documented
@RestController
@RequestMapping(UserController.USER_PREFIX) // Apply default prefix
public @interface UserController {
    String USER_PREFIX = "/user"; // Prefix for all user controllers
}
