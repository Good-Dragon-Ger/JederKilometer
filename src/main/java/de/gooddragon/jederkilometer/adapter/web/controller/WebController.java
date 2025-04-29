package de.gooddragon.jederkilometer.adapter.web.controller;

import de.gooddragon.jederkilometer.application.service.JederKilometerService;
import de.gooddragon.jederkilometer.domain.model.Aufzeichnung;
import de.gooddragon.jederkilometer.domain.model.Sportart;
import de.gooddragon.jederkilometer.domain.model.Sportler;
import de.gooddragon.jederkilometer.domain.model.Team;
import de.gooddragon.jederkilometer.domain.model.strava.Navigation;
import de.gooddragon.jederkilometer.domain.service.ActivityFilter;
import de.gooddragon.jederkilometer.domain.service.KMBerechnung;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Controller
public class WebController {

    private final KMBerechnung kmBerechnung = new KMBerechnung();
    private final ActivityFilter filter = new ActivityFilter();
    private final JederKilometerService service;

    public WebController(JederKilometerService service) {
        this.service = service;
    }

    @GetMapping("")
    public String getIndex(Model model) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        List<Aufzeichnung> aufzeichnungen = service.alleAufzeichnungen();
        List<String> labelsBar = new ArrayList<>();
        Set<UUID> sportarten = new HashSet<>();
        List<String> labelsPie = new ArrayList<>();
        List <Double> kmBar = new ArrayList<>();
        List<Sportart> aktiv = new ArrayList<>();
        HashMap<String, Set<UUID>> kategorie = new HashMap<>();
        List<Navigation> navigation = new ArrayList<>();
        double money = 0.0;
        List<Double> kmPie = new ArrayList<>();

        navigation.add(new Navigation("Home",""));
        for(Sportart sportart : service.alleSportarten()) {
            if(sportart.getAktiv()) {
                aktiv.add(sportart);
                if(kategorie.containsKey(sportart.getKategorie())) {
                    kategorie.get(sportart.getKategorie()).add(sportart.getId());
                }
                else {
                    Set<UUID> ids = new HashSet<>();
                    ids.add(sportart.getId());
                    kategorie.put(sportart.getKategorie(), ids);
                    navigation.add(new Navigation(sportart.getKategorie(), "/"+sportart.getKategorie()));
                }
            }
        }
        //navigation.add("Archive");

        for(Aufzeichnung aufzeichnung : aufzeichnungen) {
            Sportart art = service.findeSportartDurchId(aufzeichnung.sportart());
            if (art.getAktiv()) {
                if (!labelsBar.contains(aufzeichnung.datum().format(formatter))) {
                    labelsBar.add(aufzeichnung.datum().format(formatter));
                    kmBar.add(kmBerechnung.berechneGesamtKmProTag(
                            filter.filterActivitiesByActiveSportart(aufzeichnungen,
                            aktiv), aufzeichnung.datum()));
                }
                sportarten.add(aufzeichnung.sportart());
                Sportart sportart = service.findeSportartDurchId(aufzeichnung.sportart());
                if (sportart != null && !labelsPie.contains(sportart.getKategorie())) {
                    labelsPie.add(sportart.getKategorie());
                    kmPie.add(kmBerechnung.berechneGesamtKmProKategorie(aufzeichnungen, kategorie.get(sportart.getKategorie())));
                }
            }
        }

        for(UUID sportart : sportarten.stream().toList()) {
            money += priceOfKM(kmBerechnung.berechneGesamtKmProSportart(aufzeichnungen, sportart),service.findeSportartDurchId(sportart).getPreis());
        }

        model.addAttribute("nav", navigation);
        model.addAttribute("km", convertKM(kmBerechnung.berechneAktiveGesamtKm(aufzeichnungen,aktiv)));
        model.addAttribute("geld",  convertAmount(money));
        model.addAttribute("dataPie", kmPie.toArray());
        model.addAttribute("labelsPie", labelsPie.toArray());
        model.addAttribute("dataBar", kmBar.toArray());
        model.addAttribute("labelsBar", labelsBar.toArray());
        return "index";
    }

    @GetMapping("/{kategorie}*")
    public String getSport(@PathVariable String kategorie, Model model) {
        Set<String> category = new HashSet<>();
        List<Navigation> navigation = new ArrayList<>();
        List<Aufzeichnung> aufzeichnungen = service.findeAufzeichnungenDurchKategorie(kategorie);
        List<Sportart> sportarten = service.findeSportartDurchKategorie(kategorie);
        List<Team> teams = service.alleTeams();
        List<String> names = new ArrayList<>();
        List<Double> km = new ArrayList<>();
        List<Double> kmBar = new ArrayList<>();
        List<String[]> teamsArray = new ArrayList<>();
        List<Sportler> sportler = new ArrayList<>();

        for(Aufzeichnung tkm : aufzeichnungen) {
            sportler.add(service.findeSportlerDurchId(tkm.name()));
        }

        navigation.add(new Navigation("Home","/"));
        for(Sportart sportart : service.alleSportarten()) {
            if(sportart.getAktiv() && !category.contains(sportart.getKategorie())) {
                category.add(sportart.getKategorie());
                navigation.add(new Navigation(sportart.getKategorie(), "/"+sportart.getKategorie()));
            }
        }

        for(Team team : teams) {
            HashSet<UUID> member = new HashSet<>();
            for (Sportler user : sportler) {
                if (team.getId().equals(user.getTeam()) && user.getTeam() != null) {
                    member.add(user.getId());
                }
            }
            if (!member.isEmpty()) {
                team.setMember(member);
            }
            km.add(kmBerechnung.berechneGesamtKmTeam(aufzeichnungen, team.getMember()));
            kmBar.add(kmBerechnung.berechneGesamtKmTeam(aufzeichnungen, team.getMember()));
            names.add(team.getName());
            String[] teamArray = new String[2];
            teamArray[0] = team.getName();
            teamArray[1] = km.getLast().toString();
            teamsArray.add(teamArray);
        }

        if (!teamsArray.isEmpty()) {

            teamsArray.sort((a, b) -> {
                double kmA = Double.parseDouble(a[1]);
                double kmB = Double.parseDouble(b[1]);
                return Double.compare(kmB, kmA);
            });

            teamsArray.forEach(team -> team[1] = convertKM(Double.parseDouble(team[1])));

            if(teamsArray.size() > 3) {
                teamsArray = teamsArray.subList(0, 3);
            }
        }

        double money = 0.0;

        for(Sportart sportart : sportarten) {
            money += priceOfKM(kmBerechnung.berechneGesamtKmProSportart(aufzeichnungen, sportart.getId()),sportart.getPreis());
        }

        model.addAttribute("sport", kategorie);
        model.addAttribute("nav", navigation);
        model.addAttribute("km", convertKM(kmBerechnung.berechneAktiveGesamtKm(aufzeichnungen,sportarten)));
        model.addAttribute("geld",  convertAmount(money));
        model.addAttribute("teams", teamsArray);
        model.addAttribute("names", names.toArray());
        model.addAttribute("kmBar", kmBar.toArray());
        return "detail";
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
        return "adminLogin";
    }

    private double priceOfKM(double km, double price) {
        return km * price;
    }

    private String convertAmount(double amount) {
        Locale de = Locale.GERMANY;
        NumberFormat euroFormat = NumberFormat.getCurrencyInstance(de);
        return euroFormat.format(amount);
    }

    private String convertKM(double km){
        Locale de = Locale.GERMANY;
        NumberFormat kmFormat = NumberFormat.getNumberInstance(de);
        return kmFormat.format(km);
    }
}
