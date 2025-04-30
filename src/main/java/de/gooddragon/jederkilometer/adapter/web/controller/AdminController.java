package de.gooddragon.jederkilometer.adapter.web.controller;

import de.gooddragon.jederkilometer.adapter.web.config.AdminOnly;
import de.gooddragon.jederkilometer.application.service.JederKilometerService;
import de.gooddragon.jederkilometer.domain.model.Sportart;
import de.gooddragon.jederkilometer.domain.model.Sportler;
import de.gooddragon.jederkilometer.domain.model.Team;
import de.gooddragon.jederkilometer.domain.model.strava.Zeitraum;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Controller
@AdminOnly
@RequestMapping("/admin")
public class AdminController {

    private final JederKilometerService service;


    public AdminController(JederKilometerService service) {
        this.service = service;
    }

    @GetMapping("")
    public String adminIndex() {
        return "adminIndex";
    }

    @GetMapping("/activity")
    public String activityVerwaltung() {
        return "adminActivityVerwaltung";
    }

    @GetMapping("/user")
    public String userVerwaltung(Model model) {
        List<Sportler> sportler = service.alleSportler();
        model.addAttribute("alleSportler", sportler);
        return "adminUserVerwaltung";
    }

    @PostMapping("/user/neu")
    public String userNeu(Model model, @Valid Sportler sportler, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if(bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("error", "Benutzername oder Name bereits vergeben");
            return "redirect:/admin/user";
        }
        var maybeUser = service.alleSportler().stream()
                .filter(user -> user.getUserName().equals(sportler.getUserName()))
                .findAny();
        if(maybeUser.isPresent()) {
            redirectAttributes.addFlashAttribute("error", "Benutzername existiert bereits");
        }
        else {
            model.addAttribute("form", sportler);
            service.speicherSportler(sportler);
            redirectAttributes.addFlashAttribute("success", "Benutzer erfolgreich angelegt");
        }
        return "redirect:/admin/user";
    }

    @GetMapping("/user/{id}")
    public String userUpdate(Model model, @PathVariable UUID id) {
        Sportler sportler = service.findeSportlerDurchId(id);
        List<Team> teams = service.alleTeams();
        if(sportler == null) {
            return "redirect:/admin/user";
        }
        model.addAttribute("sportler", sportler);
        model.addAttribute("teams", teams);
        return "adminEditUser";
    }

    @PostMapping("/user/{id}/update")
    public String userUpdate(Model model,@PathVariable UUID id, @Valid Sportler sportler, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if(bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("error", "Benutzername oder Name bereits vergeben");
            return "redirect:/admin/user/" + id;
        }
        Sportler athlete = service.findeSportlerDurchId(id);

        if(athlete.getName().equals(sportler.getName())) {
            model.addAttribute("form", sportler);
            athlete.setUserName(sportler.getUserName());
            athlete.setName(sportler.getName());
            athlete.setTeam(sportler.getTeam());
            service.speicherSportler(athlete);
            redirectAttributes.addFlashAttribute("success", "Sportler erfolgreich updated");
        }
        else {
            redirectAttributes.addFlashAttribute("error", "Team ist voll");
        }
        return "redirect:/admin/user";


    }

    @GetMapping("/sport")
    public String sportVerwaltung(Model model) {
        List<Sportart> sport = service.alleSportarten();
        model.addAttribute("sportarten", sport);
        return "adminSportVerwaltung";
    }

    @GetMapping("/sport/{sportart}*")
    public String sportUpdate(Model model, @PathVariable String sportart) {
        Sportart sport = service.findeSportartDurchSportart(sportart);
        if(sport == null) {
            return "redirect:/admin/sport";
        }
        model.addAttribute("sport", sport);
        return "adminEditSport";
    }

    @PostMapping("/sport/{bezeichnung}/update")
    public String sportUpdate(Model model,@PathVariable String bezeichnung, @Valid Sportart sportart, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("error", "Fehler bei der Eingabe. Bitte überprüfen Sie alle Felder.");
            return "redirect:/admin/sport/" + sportart;
        }
        Sportart sport = service.findeSportartDurchSportart(bezeichnung);

        if(sport.getSport().equals(bezeichnung)) {
            model.addAttribute("form", sportart);
            sport.setKategorie(sportart.getKategorie());
            sport.setPreis(sportart.getPreis());
            sport.setAktiv(sportart.getAktiv());
            service.speicherSportart(sport);
            redirectAttributes.addFlashAttribute("success", "Sportart erfolgreich updated");
        }
        else {

            redirectAttributes.addFlashAttribute("error", "Benutzername existiert bereits");
        }
        return "redirect:/admin/sport";
    }

    @GetMapping("/team")
    public String teamVerwaltung(Model model) {
        List<Team> teams = service.alleTeams();
        model.addAttribute("alleTeams", teams);
        List<Sportler> sportler = service.alleSportler();
        for (Team team : teams) {
            Set<UUID> member = new HashSet<>(team.getMember());
            for (Sportler sport : sportler) {
               if (team.getId().equals(sport.getTeam())) {
                    member.add(sport.getTeam());
                }
            }
            if (!member.isEmpty()) {
                team.setMember(member);
            }
        }
        return "adminTeamVerwaltung";
    }

    @PostMapping("/team/neu")
    public String teamNeu(Model model, @Valid Team team, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if(bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("error", "Team bereits vergeben");
            return "redirect:/admin/team";
        }
        var maybeTeam = service.alleTeams().stream()
                .filter(user -> user.getName().equals(team.getName()))
                .findAny();
        if(maybeTeam.isPresent()) {
            redirectAttributes.addFlashAttribute("error", "Team existiert bereits");
        }
        else {
            model.addAttribute("form", team);
            service.speicherTeam(team);
            redirectAttributes.addFlashAttribute("success", "Team erfolgreich angelegt");
        }
        return "redirect:/admin/team";
    }

    @GetMapping("/event")
    public String adminEventVerwaltung(Model model) {
        List<Zeitraum> events = service.alleEvents();
        model.addAttribute("alleEvents", events);
        return "adminEventVerwaltung";
    }

    @PostMapping("/event/neu")
    public String newEvent(Model model, @Valid Zeitraum zeitraum, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("error","Zeitraum schon vorhanden");
            return "redirect:/admin/event";
        }

        var maybeZeitraum = service.alleEvents().stream()
                .filter(event -> event.getStartDate().equals(zeitraum.getStartDate()) && event.getEndDate().equals(zeitraum.getEndDate()))
                .findAny();
        if(maybeZeitraum.isPresent()) {
            redirectAttributes.addFlashAttribute("error", "Zeitraum existiert bereits");
        }
        else {
            model.addAttribute("form", zeitraum);
            service.speicherZeitraum(zeitraum);
            redirectAttributes.addFlashAttribute("success", "Zeitraum erfolgreich angelegt");
        }
        return "redirect:/admin/event";
    }
}
