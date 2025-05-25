package com.xlei.aiagent.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author https://github.com/xuzhixing181
 */
@RestController
@RequestMapping("/healthy")
public class HealthyController {

    @GetMapping
    public String healthCheck() {
        return "ok";
    }
}
