package de.gooddragon.jederkilometer.adapter.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class WebController {

    @GetMapping("/")
    public String getIndex(Model model) {
        model.addAttribute("km", 0);
        model.addAttribute("geld", 0.00 * 0.15 + "€");
        model.addAttribute("data", List.of(1, 3, 8).toArray());
        model.addAttribute("labels", List.of("x", "y", "z").toArray());
        return "index";
    }
}
