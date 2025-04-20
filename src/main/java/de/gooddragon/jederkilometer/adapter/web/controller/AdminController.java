package de.gooddragon.jederkilometer.adapter.web.controller;

import de.gooddragon.jederkilometer.adapter.web.config.AdminOnly;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AdminOnly
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("")
    public String adminIndex(@AuthenticationPrincipal OAuth2User userObject) {
        var userRole = userObject.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .findAny()
                .orElse("");
        if (userRole.isEmpty()) {
            return "redirect:/login";
        }
        return "adminIndex";
    }
}
