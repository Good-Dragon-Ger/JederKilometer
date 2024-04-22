package de.gooddragon.jederkilometer.application.service;

import de.gooddragon.jederkilometer.domain.model.strava.EventAufzeichnung;
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
import java.time.temporal.ChronoUnit;
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
    private String token;

    public StravaService(@Value("${jederKilometer.strava.client-id}") String clientId,
                         @Value("${jederKilometer.strava.client-secret}") String clientSecret,
                         @Value("${jederKilometer.strava.refresh-token}") String refreshToken,
                         @Value("${jederKilometer.strava.clubId}") Long clubId,
                         @Value("${jederKilometer.strava.clubPage}") Integer page,
                         @Value("${jederKilometer.strava.pageEntries}") Integer perPage) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.refreshToken = refreshToken;
        this.clubId = clubId;
        this.page = page;
        this.perPage = perPage;
        webClient = WebClient.builder().baseUrl("https://www.strava.com/api/v3").build();
    }

    @Scheduled(fixedDelay = 1, timeUnit = TimeUnit.MINUTES)
    public void update() {
        getToken();
        save(getActivities());
    }

    private List<EventAufzeichnung> getActivities() {
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
    }

    private void save(List<EventAufzeichnung> activities) {
        System.err.println("save to database: " + activities);
    }

    private void getToken() {
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
    }
}
