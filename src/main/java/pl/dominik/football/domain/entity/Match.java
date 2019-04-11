package pl.dominik.football.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@ToString
public class Match {

    @Id
    @Getter @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Getter @Setter
    private int homeScore;

    @Getter @Setter
    private int awayScore;

    @OneToOne
    @Getter @Setter @ToString.Exclude
    private Team homeTeam;

    @OneToOne
    @Getter @Setter @ToString.Exclude
    private Team awayTeam;

    @ManyToOne
    @Getter @Setter @ToString.Exclude
    private Round round;

    public Match(Team homeTeam, Team awayTeam, int homeScore, int awayScore, Round round) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeScore = homeScore;
        this.awayScore = awayScore;
        this.round = round;
    }
}
