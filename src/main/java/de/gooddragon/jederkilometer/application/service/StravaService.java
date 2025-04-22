package de.gooddragon.jederkilometer.application.service;

import de.gooddragon.jederkilometer.domain.model.Aufzeichnung;
import de.gooddragon.jederkilometer.domain.model.Sportart;
import de.gooddragon.jederkilometer.domain.model.Sportler;
import de.gooddragon.jederkilometer.domain.model.strava.EventAufzeichnung;
import de.gooddragon.jederkilometer.domain.model.strava.HashMapDaten;
import de.gooddragon.jederkilometer.domain.model.strava.Token;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class StravaService {

    private final String clientId;
    private final String clientSecret;
    private final String refreshToken;
    private final Long clubId; // Long | The identifier of the club.
    private final Integer page; // Integer | Page number. Defaults to 1.
    private final Integer perPage; // Integer | Number of items per page. Defaults to 30.
    private final WebClient webClient;
    private final HashMap<Integer, Boolean> activitiesHash = new HashMap<>();
    private final JederKilometerService service;
    private String token;
    private boolean tokenExpired = true;

    public StravaService(@Value("${jederKilometer.strava.client-id}") String clientId,
                         @Value("${jederKilometer.strava.client-secret}") String clientSecret,
                         @Value("${jederKilometer.strava.refresh-token}") String refreshToken,
                         @Value("${jederKilometer.strava.clubId}") Long clubId,
                         @Value("${jederKilometer.strava.clubPage}") Integer page,
                         @Value("${jederKilometer.strava.pageEntries}") Integer perPage,
                         JederKilometerService service) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.refreshToken = refreshToken;
        this.clubId = clubId;
        this.page = page;
        this.perPage = perPage;
        this.service = service;
        webClient = WebClient.builder().baseUrl("https://www.strava.com/api/v3").build();
        for (HashMapDaten hash : service.alleHashDaten()) {
            activitiesHash.put(hash.hash(),hash.value());
        }
    }

    @Scheduled(fixedDelay = 1, timeUnit = TimeUnit.MINUTES)
    public void update() {
        if (tokenExpired) {
            getToken();
        }
        List<EventAufzeichnung> activities =  getActivities();
        if (!activities.isEmpty()) {
            save(activities);
        }
    }

    private List<EventAufzeichnung> getActivities() {
        try{
            List<EventAufzeichnung> clubActivity = webClient.get()
                    .uri("/clubs/" + clubId + "/activities?page=" + page + "&per_page=" + perPage)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .retrieve()
                    .bodyToFlux(EventAufzeichnung.class)
                    .collectList()
                    .block(Duration.of(3, ChronoUnit.SECONDS));
            assert clubActivity != null;
            clubActivity.forEach(System.err::println);
            return clubActivity;
        } catch (Exception e) {
            System.err.println("Error fetching activities from Strava: " + e.getMessage());
            tokenExpired = true;
            return Collections.emptyList();
        }
    }

    private void save(List<EventAufzeichnung> activities) {
        List<Sportler> sportler = service.alleSportler();
        sportler.forEach(System.err::println);
        if (!sportler.isEmpty()) {
            HashMapDaten hash = new HashMapDaten(activities.hashCode(),true);
            for (EventAufzeichnung activity : activities) {
                if(activitiesHash.get(hash.hash()) == null) {
                    Sportler user = service.alleSportlerMitUsername(activity.athlete().firstname()+ " "+ activity.athlete().lastname());
                    if (user != null) {
                        service.speicherAufzeichnung(convertToAufzeichnung(activity));
                        activitiesHash.put(hash.hash(), hash.value());
                        service.speicherHash(hash);
                        System.err.println("save to database: " + activities);
                    }
                }
            }
        }
    }

    private Aufzeichnung convertToAufzeichnung(EventAufzeichnung activity) {
        Sportler sportler = service.alleSportlerMitUsername(activity.athlete().firstname()+ " "+ activity.athlete().lastname());
        Sportart sportart = service.findeSportartDurchSportart(activity.sport_type());

        return new Aufzeichnung(sportart.getId(), activity.distance()/1000, LocalDate.now(), sportler.getId());

    }

    private void getToken() {
        try {
            MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
            requestBody.add("client_id", clientId);
            requestBody.add("client_secret", clientSecret);
            requestBody.add("grant_type", "refresh_token");
            requestBody.add("refresh_token", refreshToken);

            Token response = webClient.post()
                    .uri("/oauth/token")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .body(BodyInserters.fromFormData(requestBody))
                    .retrieve()
                    .bodyToMono(Token.class)
                    .block();

            System.err.println(response);
            assert response != null;
            token = response.access_token();
            tokenExpired = false;
        } catch (Exception e) {
            System.err.println("Error while refreshing token: " + e.getMessage());
            tokenExpired = true;
        }
    }
}
