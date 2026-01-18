package com.java.Linkly.controller;

import com.java.Linkly.ApplicationProperties;
import com.java.Linkly.model.PagedResult;
import com.java.Linkly.model.ShortUrlDto;
import com.java.Linkly.service.ShortUrlService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    private final ShortUrlService shortUrlService;
    private final ApplicationProperties properties;

    @GetMapping("/dashboard")
    public String adminDashboard(@RequestParam(defaultValue = "1") int page, Model model) {
        PagedResult<ShortUrlDto> allUrls = shortUrlService.findAllShortUrls(page, properties.pageSize());
        model.addAttribute("shortUrls", allUrls);
        model.addAttribute("baseUrl", properties.baseUrl());
        model.addAttribute("paginationUrl", "/admin/dashboard");
        return "admin-dashboard";
    }
}
