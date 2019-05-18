package pl.dominik.football.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PreRemove;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@ToString
public class Season {

    @Id
    @Getter @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull(message = "{pl.season.validation.notNull.message}")
    @Size(min = 2, message = "{pl.season.validation.size.message}")
    @Column(unique = true)
    @Getter @Setter @ToString.Exclude
    private String seasonName;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "season", cascade = CascadeType.REMOVE)
    @Getter @Setter @ToString.Exclude
    private List<Round> rounds = new ArrayList<>();

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany(mappedBy = "season")
    @Getter @Setter @ToString.Exclude
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

    public void removeTeam(Team team) {
        this.teams.remove(team);
    }

    @PreRemove
    private void deleteSeasonFromTeams() {
        for (Team t : teams) { //teams
            t.getSeason().remove(this);
        }
    }
}
