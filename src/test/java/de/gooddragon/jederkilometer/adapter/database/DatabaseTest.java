package de.gooddragon.jederkilometer.adapter.database;

import de.gooddragon.jederkilometer.adapter.database.repository.*;
import de.gooddragon.jederkilometer.adapter.database.repository.crud.*;
import de.gooddragon.jederkilometer.application.service.repository.*;
import de.gooddragon.jederkilometer.domain.model.Aufzeichnung;
import de.gooddragon.jederkilometer.domain.model.Sportart;
import de.gooddragon.jederkilometer.domain.model.Sportler;
import de.gooddragon.jederkilometer.domain.model.Team;
import de.gooddragon.jederkilometer.domain.model.strava.HashMapDaten;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJdbcTest
@ActiveProfiles("test")
public class DatabaseTest {

    @Autowired
    SpringDataActivityRepository dataActivityRepository;
    @Autowired
    SpringDataHashDataRepository dataHashDataRepository;
    @Autowired
    SpringDataSportRepository dataSportRepository;
    @Autowired
    SpringDataTeamsRepository dataTeamsRepository;
    @Autowired
    SpringDataUserRepository dataUserRepository;

    ActivityRepository activityRepository;
    HashDataRepository hashDataRepository;
    SportRepository sportRepository;
    TeamsRepository teamsRepository;
    UserRepository userRepository;


    @BeforeEach
    void setup() {
        activityRepository = new ActivityRepositoryImpl(dataActivityRepository);
        hashDataRepository = new HashDataRepositoryImpl(dataHashDataRepository);
        sportRepository = new SportRepositoryImpl(dataSportRepository);
        teamsRepository = new TeamsRepositoryImpl(dataTeamsRepository);
        userRepository = new UserRepositoryImpl(dataUserRepository);
    }

    @Test
    @DisplayName("Ein Team kann gespeichert und geladen werden")
    void test_01() {
        Team team = new Team("testbegriff");
        Team saved = teamsRepository.save(team);
        Team geladen = teamsRepository.findAllByUuid(saved.getId());
        assertThat(geladen.getName()).isEqualTo(saved.getName());
    }

    @Test
    @DisplayName("Wenn es mehrere Teams gibt, werden alle gefunden")
    @Sql("findAllTeams.sql")
    void test_02() {
        List<Team> all = teamsRepository.findAll();
        assertThat(all).hasSize(3);
    }

    @Test
    @DisplayName("User können hinzugefügt und geladen werden")
    void Test_03() {
        Sportler sportler = new Sportler( UUID.randomUUID(),"Max M.", "Max Mustermann", UUID.randomUUID(), new HashSet<>());
        Sportler saved = userRepository.save(sportler);
        Sportler geladen = userRepository.findByUuid(saved.getId());
        assertThat(geladen.getUserName()).isEqualTo(saved.getUserName());
        assertThat(geladen.getName()).isEqualTo(saved.getName());
    }

    @Test
    @DisplayName("Wenn es mehrere Arbeitsplätze gibt, werden alle gefunden")
    @Sql("findAllUsers.sql")
    void test_04() {
        List<Sportler> all = userRepository.findAll();
        assertThat(all).hasSize(3);
    }

    @Test
    @DisplayName("Sportarten können hinzugefügt und geladen werden")
    void test_05() {
        Sportart sport = new Sportart(UUID.randomUUID(), "Laufen", 0.6, "Laufen",false);
        Sportart saved = sportRepository.save(sport);
        Sportart geladen = sportRepository.findByUuid(saved.getId());
        assertThat(geladen.getSport()).isEqualTo(saved.getSport());
        assertThat(geladen.getPreis()).isEqualTo(saved.getPreis());
        assertThat(geladen.getKategorie()).isEqualTo(saved.getKategorie());
        assertThat(geladen.getAktiv()).isEqualTo(saved.getAktiv());
    }

    @Test
    @DisplayName("Wenn es mehrere Sportarten gibt, werden alle gefunden")
    void test_06() {
        List<Sportart> all = sportRepository.findAll();
        assertThat(all).hasSize(50);
    }

    @Test
    @DisplayName("Activities können hinzugefügt und geladen werden")
    void Test_07() {
        Aufzeichnung aufzeichnung = new Aufzeichnung( UUID.randomUUID(), UUID.randomUUID(), 0.8, LocalDate.now(), UUID.randomUUID());
        Aufzeichnung saved = activityRepository.save(aufzeichnung);
        Aufzeichnung geladen = activityRepository.findByUuid(saved.id());
        assertThat(geladen.id()).isEqualTo(saved.id());
        assertThat(geladen.km()).isEqualTo(saved.km());
        assertThat(geladen.sportart()).isEqualTo(saved.sportart());
        assertThat(geladen.datum()).isEqualTo(saved.datum());
    }

    @Test
    @DisplayName("Wenn es mehrere Arbeitsplätze gibt, werden alle gefunden")
    @Sql("findAllActivities.sql")
    void test_08() {
        List<Aufzeichnung> all = activityRepository.findAll();
        assertThat(all).hasSize(3);
    }

    @Test
    @DisplayName("HashData können hinzugefügt und geladen werden")
    void Test_09() {
        HashMapDaten hashMapDaten = new HashMapDaten(12345678, true);
        HashMapDaten saved = hashDataRepository.save(hashMapDaten);
        assertThat(saved.hash()).isEqualTo(hashMapDaten.hash());
        assertThat(saved.value()).isEqualTo(hashMapDaten.value());
    }

    @Test
    @DisplayName("Wenn es mehrere HashData gibt, werden alle gefunden")
    @Sql("findAllHashData.sql")
    void test_10() {
        List<HashMapDaten> all = hashDataRepository.findAll();
        assertThat(all).hasSize(3);
    }
}

