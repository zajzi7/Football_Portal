package pl.dominik.football.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
public class Season {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Getter @Setter
    private String seasonName;

    @OneToMany(fetch = FetchType.EAGER)
    @Getter @Setter
    private List<Round> rounds;

    public void addRound(int roundNumber) {
        rounds.add(new Round(roundNumber));
    }

    public Season(String seasonName) {
        this.seasonName = seasonName;
        this.rounds = new ArrayList<>();
    }

}
