package de.gooddragon.jederkilometer.adapter.web.controller;

import de.gooddragon.jederkilometer.adapter.web.config.MethodSecurityConfiguration;
import de.gooddragon.jederkilometer.adapter.web.config.SecuConfig;
import de.gooddragon.jederkilometer.adapter.web.config.UserConfig;
import de.gooddragon.jederkilometer.helper.WithMockOAuth2User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest
@Import({SecuConfig.class, MethodSecurityConfiguration.class, UserConfig.class})
public class AdminControllerTests {

    @Autowired
    MockMvc mockMvc;

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
}
