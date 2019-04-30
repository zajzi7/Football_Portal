package pl.dominik.football.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PreRemove;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
public class Season {

    @Id
    @Getter @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Getter @Setter
    private String seasonName;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "season", cascade = CascadeType.REMOVE)
    @Getter @Setter
    private List<Round> rounds = new ArrayList<>();

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany(mappedBy = "season")
    @Getter @Setter
    private Set<Team> teams = new HashSet<>();

    public Season(String seasonName) {
        this.seasonName = seasonName; //constructor to delete in the future
    }

    //===Logical part===

    public void addRound(Round round) {
        this.rounds.add(round);
    }

    public void addTeam(Team team) {
        this.teams.add(team);
    }

    @PreRemove
    private void deleteSeasonFromTeams() {
        for (Team t : teams) { //teams
            t.getSeason().remove(this);
        }
    }
}
