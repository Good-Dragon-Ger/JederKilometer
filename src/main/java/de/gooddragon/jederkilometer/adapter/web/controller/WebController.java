package de.gooddragon.jederkilometer.adapter.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class WebController {

    @GetMapping("/")
    public String getIndex(Model model) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        List<Integer> kmPie = List.of(5,4,2);
        List<String> labelsPie = List.of("Fahrrad","Laufen","Wandern");
        List <Integer> kmBar = List.of(1, 2, 8);
        List <String> labelsBar = List.of(LocalDate.of(2025,5,1).format(formatter), LocalDate.of(2025,5,2).format(formatter), LocalDate.of(2025,5,3).format(formatter));
        model.addAttribute("km", 11.00);
        model.addAttribute("geld", 5.00 * 0.25 + 6 * 0.6 + "€");
        model.addAttribute("dataPie", kmPie.toArray());
        model.addAttribute("labelsPie", labelsPie.toArray());
        model.addAttribute("dataBar", kmBar.toArray());
        model.addAttribute("labelsBar", labelsBar.toArray());
        return "index";
    }
}
