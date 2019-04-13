package pl.dominik.football.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@ToString
public class Season {

    @Id
    @Getter @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Getter @Setter
    private String seasonName;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "season", cascade = CascadeType.ALL)
    @Getter @Setter
    private List<Round> rounds = new ArrayList<>();

    public Season(String seasonName) {
        this.seasonName = seasonName;
    }

    public void addRound(Round round) {
        this.rounds.add(round);
    }
}
