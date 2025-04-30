package de.gooddragon.jederkilometer.application.service;

import de.gooddragon.jederkilometer.application.service.repository.*;
import de.gooddragon.jederkilometer.domain.model.Aufzeichnung;
import de.gooddragon.jederkilometer.domain.model.Sportart;
import de.gooddragon.jederkilometer.domain.model.Sportler;
import de.gooddragon.jederkilometer.domain.model.Team;
import de.gooddragon.jederkilometer.domain.model.strava.HashMapDaten;
import de.gooddragon.jederkilometer.domain.model.strava.Zeitraum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class JederKilometerServiceTests {

    private static final LocalDate STAMP = LocalDate.now();
    ActivityRepository activityRepository = mock(ActivityRepository.class);
    HashDataRepository hashDataRepository = mock(HashDataRepository.class);
    SportRepository sportRepository = mock(SportRepository.class);
    TeamsRepository teamsRepository = mock(TeamsRepository.class);
    UserRepository userRepository = mock(UserRepository.class);
    EventTimeRepository eventTimeRepository = mock(EventTimeRepository.class);
    JederKilometerService self = mock(JederKilometerService.class);
    JederKilometerService service;

    UUID id;

    @BeforeEach
    void setUp() {
        service = new JederKilometerService(activityRepository, hashDataRepository, sportRepository, teamsRepository, userRepository, eventTimeRepository, self);
        id = UUID.randomUUID();
    }

    @Test
    @DisplayName("Wir können Aktivitäten hinzufügen")
    void test_01() {
        Aufzeichnung aufzeichnung = new Aufzeichnung(id, UUID.randomUUID(), 10.0, STAMP, UUID.randomUUID());
        when(activityRepository.save(any())).thenReturn(aufzeichnung);

        service.saveAufzeichnung(aufzeichnung);

        ArgumentCaptor<Aufzeichnung> captor = ArgumentCaptor.forClass(Aufzeichnung.class);
        verify(activityRepository).save(captor.capture());
        Aufzeichnung savedAufzeichnung = captor.getValue();
        assertThat(savedAufzeichnung.km()).isEqualTo(aufzeichnung.km());
    }

    @Test
    @DisplayName("Wir können Aktivitäten laden")
    void test_02() {
        Aufzeichnung aufzeichnung1 = new Aufzeichnung(id, UUID.randomUUID(), 10.0, STAMP, UUID.randomUUID());
        Aufzeichnung aufzeichnung2 = new Aufzeichnung(UUID.randomUUID(), UUID.randomUUID(), 20.0, STAMP, UUID.randomUUID());
        when(activityRepository.findAll()).thenReturn(List.of(aufzeichnung1, aufzeichnung2));

        assertThat(activityRepository.findAll().size()).isEqualTo(2);
    }

    @Test
    @DisplayName("Wir können HashDaten hinzufügen")
    void test_03() {
        HashMapDaten hashMapDaten = new HashMapDaten(id.hashCode(), true);
        when(hashDataRepository.save(any())).thenReturn(hashMapDaten);

        service.saveHash(hashMapDaten);

        ArgumentCaptor<HashMapDaten> captor = ArgumentCaptor.forClass(HashMapDaten.class);
        verify(hashDataRepository).save(captor.capture());
        HashMapDaten savedHashMapDaten = captor.getValue();
        assertThat(savedHashMapDaten.hash()).isEqualTo(hashMapDaten.hash());
    }

    @Test
    @DisplayName("Wir können HashDaten laden")
    void test_04() {
        HashMapDaten hashMapDaten1 = new HashMapDaten(UUID.randomUUID().hashCode(),true);
        HashMapDaten hashMapDaten2 = new HashMapDaten(UUID.randomUUID().hashCode(), false);
        when(hashDataRepository.findAll()).thenReturn(List.of(hashMapDaten1, hashMapDaten2));

        assertThat(hashDataRepository.findAll().size()).isEqualTo(2);
    }

    @Test
    @DisplayName("Wir können Sportarten hinzufügen")
    void test_05() {
        Sportart sportart = new Sportart(id, "Laufen", 0.6, "Laufen",false);
        when(sportRepository.save(any())).thenReturn(sportart);

        service.saveSport(sportart);

        ArgumentCaptor<Sportart> captor = ArgumentCaptor.forClass(Sportart.class);
        verify(sportRepository).save(captor.capture());
        Sportart savedSportArtDaten = captor.getValue();
        assertThat(savedSportArtDaten.getSport()).isEqualTo(sportart.getSport());
    }

    @Test
    @DisplayName("Wir können Sportarten laden")
    void test_06() {
        Sportart sportart1 = new Sportart(id, "Laufen", 0.6, "Laufen",false);
        Sportart sportart2 = new Sportart(id, "Laufen", 0.6, "Laufen",false);
        when(sportRepository.findAll()).thenReturn(List.of(sportart1, sportart2));

        assertThat(sportRepository.findAll().size()).isEqualTo(2);
    }

    @Test
    @DisplayName("Wir können Teams hinzufügen")
    void test_07() {
        Team team = new Team(id, "YokoRaiders", new HashSet<>());
        when(teamsRepository.save(any())).thenReturn(team);

        service.saveTeam(team);

        ArgumentCaptor<Team> captor = ArgumentCaptor.forClass(Team.class);
        verify(teamsRepository).save(captor.capture());
        Team savedTeam = captor.getValue();
        assertThat(savedTeam.getId()).isEqualTo(team.getId());
    }

    @Test
    @DisplayName("Wir können Teams laden")
    void test_08() {
        Team team1 = new Team(id, "YokoRaiders", new HashSet<>());
        Team team2 = new Team(id, "YokoRaiders", new HashSet<>());
        when(teamsRepository.findAll()).thenReturn(List.of(team1, team2));

        assertThat(teamsRepository.findAll().size()).isEqualTo(2);
    }

    @Test
    @DisplayName("Wir können Sportler hinzufügen")
    void test_09() {
        Sportler sportler = new Sportler(id, "Max M.", "Max Mustermann", UUID.randomUUID(), new HashSet<>());
        when(userRepository.save(any())).thenReturn(sportler);

        service.saveSportler(sportler);

        ArgumentCaptor<Sportler> captor = ArgumentCaptor.forClass(Sportler.class);
        verify(userRepository).save(captor.capture());
        Sportler savedHashMapDaten = captor.getValue();
        assertThat(savedHashMapDaten.getId()).isEqualTo(sportler.getId());
    }

    @Test
    @DisplayName("Wir können Sportler laden")
    void test_10() {
        Sportler sportler1 = new Sportler(id, "Max M.", "Max Mustermann", UUID.randomUUID(), new HashSet<>());
        Sportler sportler2 = new Sportler(id, "Max M.", "Max Mustermann", UUID.randomUUID(), new HashSet<>());
        when(userRepository.findAll()).thenReturn(List.of(sportler1, sportler2));

        assertThat(userRepository.findAll().size()).isEqualTo(2);
    }

    @Test
    @DisplayName("Wir können Zeiträume hinzufügen")
    void test_11() {
        LocalDate start = LocalDate.now();
        LocalDate end = LocalDate.now().plusDays(1);

        Zeitraum zeitraum = new Zeitraum(id, start, end);
        when(eventTimeRepository.save(any())).thenReturn(zeitraum);

        service.saveZeitraum(zeitraum);

        ArgumentCaptor<Zeitraum> captor = ArgumentCaptor.forClass(Zeitraum.class);
        verify(eventTimeRepository).save(captor.capture());
        Zeitraum savedHashMapDaten = captor.getValue();
        assertThat(savedHashMapDaten.getId()).isEqualTo(zeitraum.getId());
    }

    @Test
    @DisplayName("Wir können Zeiträume laden")
    void test_12() {

        LocalDate start = LocalDate.now();
        LocalDate end = LocalDate.now().plusDays(1);

        Zeitraum zeitraum1 = new Zeitraum(id, start, end);
        Zeitraum zeitraum2 = new Zeitraum(id, start.minusDays(1), end);
        when(eventTimeRepository.findAll()).thenReturn(List.of(zeitraum1, zeitraum2));

        assertThat(eventTimeRepository.findAll().size()).isEqualTo(2);
    }
}