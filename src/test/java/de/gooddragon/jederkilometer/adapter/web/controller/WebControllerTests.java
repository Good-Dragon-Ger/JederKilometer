package de.gooddragon.jederkilometer.adapter.web.controller;

import de.gooddragon.jederkilometer.adapter.web.config.MethodSecurityConfiguration;
import de.gooddragon.jederkilometer.adapter.web.config.SecuConfig;
import de.gooddragon.jederkilometer.adapter.web.config.UserConfig;
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
public class WebControllerTests {

    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("Der Status der Index Seite ist 200 OK")
    void test_01() throws Exception {
        mockMvc.perform(get(""))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Für die Index Seite wird die richtige View zurückgegeben.")
    void test_02() throws Exception{
        mockMvc.perform(get(""))
                .andExpect(view().name("index"));
    }
}
