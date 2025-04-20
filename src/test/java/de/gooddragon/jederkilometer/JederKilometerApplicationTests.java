package de.gooddragon.jederkilometer;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(properties = {"spring.datasource.url=jdbc:h2:mem:strava",
        "spring.datasource.username=test",
        "spring.datasource.password=test",
        "jederKilometer.rollen.admin=dragonempire96",
        "jederKilometer.strava.client-id=1234546",
        "jederKilometer.strava.client-secret=123STRAVA_CLIENT_SECRET",
        "jederKilometer.strava.refresh-token=STRAVA_REFRESH_TOKEN",
        "jederKilometer.strava.clubId=1234556",
        "jederKilometer.strava.clubPage=1",
        "jederKilometer.strava.pageEntries=1"})
@ActiveProfiles({"test"})
class JederKilometerApplicationTests {

    @Test
    void contextLoads() {
    }

}
