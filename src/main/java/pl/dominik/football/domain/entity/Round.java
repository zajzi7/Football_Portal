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
public class Round {

    //Class to store single rounds. Also known as matchday(all matches of the week; usually played in one day)

    @Id
    @Getter @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    //Field to store round number in the current season(example: Round 1, season 2019/2020)
    @Getter @Setter
    private int roundNumber;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "round", cascade = CascadeType.REMOVE)
    @Getter @Setter
    private List<Match> matches = new ArrayList<>();

    @ManyToOne
    @Getter @Setter
    private Season season;

    public Round(int roundNumber) {
        this.roundNumber = roundNumber;
    }
}
