package de.gooddragon.jederkilometer.adapter.web.controller;

import de.gooddragon.jederkilometer.domain.model.Aufzeichnung;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Controller
public class WebController {

    @GetMapping("")
    public String getIndex(Model model) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        List<Aufzeichnung> aufzeichnungen = new ArrayList<>();
        Set<UUID> sportarten = new HashSet<>();
        List<String> labelsPie = new ArrayList<>();
        List<Double> kmPie = new ArrayList<>();
        List <Double> kmBar = List.of(1.0, 2.0, 8.0);
        List <String> labelsBar = List.of(LocalDate.of(2025,5,1).format(formatter), LocalDate.of(2025,5,2).format(formatter), LocalDate.of(2025,5,3).format(formatter));
        model.addAttribute("km", 0);
        model.addAttribute("geld", 5.00 * 0.25 + 6 * 0.6 + "€");
        model.addAttribute("dataPie", kmPie.toArray());
        model.addAttribute("labelsPie", labelsPie.toArray());
        model.addAttribute("dataBar", kmBar.toArray());
        model.addAttribute("labelsBar", labelsBar.toArray());
        return "index";
    }

    @GetMapping("/login")
    public String getLogin(@AuthenticationPrincipal OAuth2User userObject) {
        if (userObject != null) {
            var userRole = userObject.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .findAny()
                    .orElse("");
            if (userRole.equals("ROLE_ADMIN")) {
                return "redirect:/admin";
            }
        }
        return "login";
    }
}
