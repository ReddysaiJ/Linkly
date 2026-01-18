package com.java.Linkly.controller;

import com.java.Linkly.ApplicationProperties;
import com.java.Linkly.exceptions.ShortUrlNotFoundException;
import com.java.Linkly.model.*;
import com.java.Linkly.service.ShortUrlService;
import com.java.Linkly.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class HomeController {
    private final ShortUrlService shortUrlService;
    private final ApplicationProperties properties;
    private final UserService userService;

    @GetMapping("/")
    public String home(@RequestParam(defaultValue = "1") Integer page,
                       Model model){
        PagedResult<ShortUrlDto> shortUrls = shortUrlService.findAllPublicShortUrls(page, properties.pageSize());
        model.addAttribute("shortUrls", shortUrls);
        model.addAttribute("baseUrl", properties.baseUrl());
        model.addAttribute("paginationUrl", "/");
        model.addAttribute("createShortUrlForm", new CreateShortUrlForm("", false, null));
        return "index";
    }

    @PostMapping("/short-urls")
    public String createShortUrl(@ModelAttribute("CreateShortUrlForm") @Valid CreateShortUrlForm form,
                          BindingResult bindingResult, RedirectAttributes redirectAttributes,
                          Model model) {
        if(bindingResult.hasErrors()){
            PagedResult<ShortUrlDto> shortUrls = shortUrlService.findAllPublicShortUrls(1, properties.pageSize());
            model.addAttribute("shortUrls", shortUrls);
            model.addAttribute("baseUrl", properties.baseUrl());
            model.addAttribute("paginationUrl", "/");
            return "index";
        }
        try{
            Long userId = userService.getCurrentUserId();
            CreateShortUrlCmd cmd = new CreateShortUrlCmd(form.originalUrl(), form.isPrivate(), form.expiryInDays(), userId);
            var shortUrlDto = shortUrlService.createShortUrl(cmd);
            redirectAttributes.addFlashAttribute("successMessage", "Short URL created successfully "+
                    properties.baseUrl()+"/s/"+shortUrlDto.shortKey());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to create short URL");
        }
        return "redirect:/";
    }

    @GetMapping("/s/{shortKey}")
    public String redirectToOriginalUrl(@PathVariable String shortKey) {
        Long userId = userService.getCurrentUserId();
        Optional<ShortUrlDto> shortUrlDtoOptional = shortUrlService.getOriginalUrl(shortKey, userId);
        if(shortUrlDtoOptional.isEmpty())
            throw new ShortUrlNotFoundException("Invalid short key: "+shortKey);
        ShortUrlDto shortUrlDto = shortUrlDtoOptional.get();
        return "redirect:" + shortUrlDto.originalUrl();
    }

    @GetMapping("/my-urls")
    public String userUrls(@RequestParam(defaultValue = "1") int page, Model model) {
        Long userId = userService.getCurrentUserId();
        PagedResult<ShortUrlDto> myUrls = shortUrlService.getUserUrls(userId, page, properties.pageSize());
        model.addAttribute("shortUrls", myUrls);
        model.addAttribute("baseUrl", properties.baseUrl());
        model.addAttribute("PaginationUrl", "/my-urls");
        return "my-urls";
    }

    @PostMapping("/delete-urls")
    public String deleteUrls(@RequestParam(value = "ids", required = false) List<Long> ids,
                             RedirectAttributes redirectAttributes) {
        if (ids == null || ids.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "No URLs selected for deletion");
            return "redirect:/my-urls";
        }

        try {
            Long userId = userService.getCurrentUserId();
            shortUrlService.deleteUserUrls(ids, userId);
            redirectAttributes.addFlashAttribute("successMessage", "Selected URLs have been deleted successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error deleting URLs: " + e.getMessage());
        }
        return "redirect:/my-urls";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }
}
