package de.gooddragon.jederkilometer.adapter.web.controller;

import de.gooddragon.jederkilometer.adapter.web.config.MethodSecurityConfiguration;
import de.gooddragon.jederkilometer.adapter.web.config.SecuConfig;
import de.gooddragon.jederkilometer.adapter.web.config.UserConfig;
import de.gooddragon.jederkilometer.application.service.JederKilometerService;
import de.gooddragon.jederkilometer.domain.model.Sportart;
import de.gooddragon.jederkilometer.domain.model.Sportler;
import de.gooddragon.jederkilometer.domain.model.Team;
import de.gooddragon.jederkilometer.domain.model.strava.Zeitraum;
import de.gooddragon.jederkilometer.helper.WithMockOAuth2User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Collections;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest
@Import({SecuConfig.class, MethodSecurityConfiguration.class, UserConfig.class})
public class AdminControllerTests {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    JederKilometerService service;

    @Test
    @WithMockOAuth2User(login = "Max Mustermann")
    @DisplayName("Der Status der Admin Seite ist 403 Forbidden, da der User nicht die Rolle ADMIN hat.")
    void test_01() throws Exception {
        mockMvc.perform(get("/admin"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockOAuth2User(login = "Max Mustermann", roles = {"USER", "ADMIN"})
    @DisplayName("Der Status der Admin Seite ist 200 OK, da der User die Rolle ADMIN hat.")
    void test_02() throws Exception {
        mockMvc.perform(get("/admin"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockOAuth2User(login = "Max Mustermann", roles = {"USER", "ADMIN"})
    @DisplayName("Für die AdminIndex Seite wird die richtige View zurückgegeben.")
    void test_03() throws Exception{
        mockMvc.perform(get("/admin"))
                .andExpect(view().name("adminIndex"));
    }

    @Test
    @WithMockOAuth2User(login = "Max Mustermann", roles = {"USER", "ADMIN"})
    @DisplayName("Der Status der UserVerwaltung ist 200 OK")
    void test_04() throws Exception {
        mockMvc.perform(get("/admin/user"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockOAuth2User(login = "Max Mustermann", roles = {"USER", "ADMIN"})
    @DisplayName("Für die UserVerwaltung wird die richtige View zurück gegeben")
    void test_05() throws Exception {
        mockMvc.perform(get("/admin/user"))
                .andExpect(view().name("adminUserVerwaltung"));
    }

    @Test
    @WithMockOAuth2User(login = "Max Mustermann", roles = {"USER", "ADMIN"})
    @DisplayName("Ein User kann hinzugefügt werden")
    void test_06() throws Exception {
        Sportler sportler = new Sportler("Max M.", "Mustermann");

        when(service.alleSportler()).thenReturn(Collections.emptyList());

        mockMvc.perform(post("/admin/user/neu")
                .flashAttr("sportler", sportler)
                .with(csrf()));

        verify(service).speicherSportler(sportler);
    }

    @Test
    @WithMockOAuth2User(login = "Max Mustermann", roles = {"USER", "ADMIN"})
    @DisplayName("Der Status der Sportverwaltung ist 200 OK")
    void test_07() throws Exception {
        mockMvc.perform(get("/admin/sport"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockOAuth2User(login = "Max Mustermann", roles = {"USER", "ADMIN"})
    @DisplayName("Für die SportartVerwaltung wird die richtige View zurück gegeben")
    void test_08() throws Exception {
        mockMvc.perform(get("/admin/sport"))
                .andExpect(view().name("adminSportVerwaltung"));
    }

    @Test
    @WithMockOAuth2User(login = "Max Mustermann", roles = {"USER", "ADMIN"})
    @DisplayName("Eine Sportart kann angepasst werden")
    void test_09() throws Exception {
        Sportart sportart = new Sportart(UUID.randomUUID(), "Run", 0.0, "Laufen",false);

        when(service.findeSportartDurchSportart(anyString())).thenReturn(sportart);

        mockMvc.perform(post("/admin/sport/Run/update")
                .param("sport", "run")
                .param("preis", "0.5")
                .param("kategorie", "Laufen")
                .param("aktiv", "true")
                .with(csrf()));

        verify(service).speicherSportart(any(Sportart.class));
    }

    @Test
    @WithMockOAuth2User(login = "Max Mustermann", roles = {"USER", "ADMIN"})
    @DisplayName("Der Status der TeamVerwaltung ist 200 OK")
    void test_10() throws Exception {
        mockMvc.perform(get("/admin/team"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockOAuth2User(login = "Max Mustermann", roles = {"USER", "ADMIN"})
    @DisplayName("Für die TeamVerwaltung wird die richtige View zurück gegeben")
    void test_11() throws Exception {
        mockMvc.perform(get("/admin/team"))
                .andExpect(view().name("adminTeamVerwaltung"));
    }

    @Test
    @WithMockOAuth2User(login = "Max Mustermann", roles = {"USER", "ADMIN"})
    @DisplayName("Ein Team kann hinzugefügt werden")
    void test_12() throws Exception {
        Team team = new Team("YokoRaiders");

        when(service.alleTeams()).thenReturn(Collections.emptyList());

        mockMvc.perform(post("/admin/team/neu")
                .flashAttr("team", team)
                .with(csrf()));

        verify(service).speicherTeam(team);
    }

    @Test
    @WithMockOAuth2User(login = "Max Mustermann", roles = {"USER", "ADMIN"})
    @DisplayName("Der Status der EventVerwaltung ist 200 OK")
    void test_13() throws Exception {
        mockMvc.perform(get("/admin/event"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockOAuth2User(login = "Max Mustermann", roles = {"USER", "ADMIN"})
    @DisplayName("Für die EventVerwaltung wird die richtige View zurück gegeben")
    void test_14() throws Exception {
        mockMvc.perform(get("/admin/event"))
                .andExpect(view().name("adminEventVerwaltung"));
    }

    @Test
    @WithMockOAuth2User(login = "Max Mustermann", roles = {"USER", "ADMIN"})
    @DisplayName("Ein Zeitraum kann hinzugefügt werden")
    void test_15() throws Exception {
        Zeitraum zeitraum = new Zeitraum(LocalDate.now().minusDays(1), LocalDate.now().plusDays(1));

        when(service.alleTeams()).thenReturn(Collections.emptyList());

        mockMvc.perform(post("/admin/event/neu")
                .flashAttr("zeitraum", zeitraum)
                .with(csrf()));

        verify(service).speicherZeitraum(zeitraum);
    }
}
