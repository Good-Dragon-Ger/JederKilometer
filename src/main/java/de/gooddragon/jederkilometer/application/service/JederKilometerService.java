package de.gooddragon.jederkilometer.application.service;

import de.gooddragon.jederkilometer.application.service.repository.*;
import de.gooddragon.jederkilometer.domain.model.Aufzeichnung;
import de.gooddragon.jederkilometer.domain.model.Sportart;
import de.gooddragon.jederkilometer.domain.model.Sportler;
import de.gooddragon.jederkilometer.domain.model.Team;
import de.gooddragon.jederkilometer.domain.model.strava.HashMapDaten;
import de.gooddragon.jederkilometer.domain.model.strava.Zeitraum;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class JederKilometerService {

    private final ActivityRepository activityRepository;
    private final HashDataRepository hashDataRepository;
    private final SportRepository sportRepository;
    private final TeamsRepository teamsRepository;
    private final UserRepository userRepository;
    private final EventTimeRepository eventTimeRepository;
    private final JederKilometerService self;

    public JederKilometerService(ActivityRepository activityRepository,
                                 HashDataRepository hashDataRepository,
                                 SportRepository sportRepository,
                                 TeamsRepository teamsRepository,
                                 UserRepository userRepository,
                                 EventTimeRepository eventTimeRepository,
                                 @Lazy JederKilometerService self){
        this.activityRepository = activityRepository;
        this.hashDataRepository = hashDataRepository;
        this.sportRepository = sportRepository;
        this.teamsRepository = teamsRepository;
        this.userRepository = userRepository;
        this.eventTimeRepository = eventTimeRepository;
        this.self = self;
    }

    public List<Aufzeichnung> alleAufzeichnungen() {
        return activityRepository.findAll();
    }

    public void speicherAufzeichnung(Aufzeichnung aufzeichnung) {
        try {
            self.saveAufzeichnung(aufzeichnung);
        } catch (Exception e) {
            System.err.println("Kann Aufzeichnung nicht schreiben");
        }
    }

    @Transactional
    protected void saveAufzeichnung(Aufzeichnung aufzeichnung) {
        activityRepository.save(aufzeichnung);
    }

    public List<HashMapDaten> alleHashDaten() {
        return hashDataRepository.findAll();
    }

    public void speicherHash(HashMapDaten hash) {
        try {
            self.saveHash(hash);
        } catch (Exception e) {
            System.err.println("Kann Hash nicht schreiben");
        }
    }

    @Transactional
    protected void saveHash(HashMapDaten hash) {
        hashDataRepository.save(hash);
    }

    public List<Sportart> alleSportarten() {
        return sportRepository.findAll();
    }

    public void speicherSportart(Sportart sportart) {
        try {
            self.saveSport(sportart);
        } catch (Exception e) {
            System.err.println("Kann Sportart nicht schreiben");
        }
    }

    @Transactional
    protected void saveSport(Sportart sportart) {
        sportRepository.save(sportart);
    }

    public Sportart findeSportartDurchSportart(String sportart) {
        return sportRepository.findBySportart(sportart);
    }

    public Sportart findeSportartDurchId(UUID sportart) {
        return sportRepository.findByUuid(sportart);
    }

    public List<Team> alleTeams() {
        return teamsRepository.findAll();
    }

    public void speicherTeam(Team team) {
        try {
            self.saveTeam(team);
        }
        catch (Exception e) {
            System.err.println("Kann Team nicht schreiben");
        }
    }

    @Transactional
    protected void saveTeam(Team team) {
        teamsRepository.save(team);
    }

    public List<Sportler> alleSportler() {
        return userRepository.findAll();
    }

    public Sportler alleSportlerMitUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public void speicherSportler(Sportler sportler) {
        try {
            self.saveSportler(sportler);
        }
        catch (Exception e) {
            System.err.println("Kann Sportler nicht schreiben");
        }
    }

    @Transactional
    protected void saveSportler(Sportler sportler) {
        userRepository.save(sportler);
    }

    public List<Aufzeichnung> findeAufzeichnungenDurchKategorie(String kategorie) {
        List<Sportart> sportarten = sportRepository.findByCategory(kategorie);
        List<Aufzeichnung> all = activityRepository.findAll();
        List<Aufzeichnung> aufzeichnungen = new ArrayList<>();

        for(Aufzeichnung aufzeichnung : all) {
            for(Sportart sportart : sportarten) {
                if(aufzeichnung.sportart().equals(sportart.getId())) {
                    aufzeichnungen.add(aufzeichnung);
                }
            }
        }

        return aufzeichnungen;
    }

    public List<Sportart> findeSportartDurchKategorie(String kategorie) {
        return sportRepository.findByCategory(kategorie);    }

    public Sportler findeSportlerDurchId(UUID id) {
        return userRepository.findByUuid(id);
    }

    public List<Zeitraum> alleEvents() {
        return eventTimeRepository.findAll();
    }

    @Transactional
    public void speicherZeitraum(Zeitraum zeitraum) {
        try {
            self.saveZeitraum(zeitraum);
        }
        catch (Exception e) {
            System.err.println("Kann Zeitraum nicht speichern");
        }
    }

    public void saveZeitraum(Zeitraum zeitraum) {
        eventTimeRepository.save(zeitraum);
    }
}
