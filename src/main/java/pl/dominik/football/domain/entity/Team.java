package pl.dominik.football.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PreRemove;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
public class Team {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
    @Id
    private int id;

    @Getter @Setter
    private String teamName;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "homeTeam")
    @Getter @Setter
    private Set<Match> matchHomeTeam; //It is here only to help @PreRemove interface

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "awayTeam")
    @Getter @Setter
    private Set<Match> matchAwayTeam; //It is here only to help @PreRemove interface

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany
    @Getter @Setter
    private Set<Season> season = new HashSet<>();

    public Team(String teamName) {
        this.teamName = teamName;
    }

    //===Logical part===

    public void assignToSeason(Season season) {
        this.season.add(season);
    }

    public void removeFromSeason(Season season) {
        this.season.remove(season);
    }

    @PreRemove
    private void deleteFromSeasonAndMatches() {

        for (Match m : matchAwayTeam) { //Set null on away teams in matches
            if (m.getAwayTeam() == this) {
                m.setAwayTeam(null);
            }
            if (m.getHomeTeam() == this) {
                m.setHomeTeam(null);
            }
        }

        for (Match m : matchHomeTeam) { //Set null on home teams in matches
            if (m.getAwayTeam() == this) {
                m.setAwayTeam(null);
            }
            if (m.getHomeTeam() == this) {
                m.setHomeTeam(null);
            }
        }

        for (Season s : season) { //Remove from season
            s.getTeams().remove(this);
        }
    }
}
