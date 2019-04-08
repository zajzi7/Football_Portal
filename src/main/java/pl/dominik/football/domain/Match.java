package pl.dominik.football.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
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
    @Getter @Setter
    private Team homeTeam;

    @OneToOne
    @Getter @Setter
    private Team awayTeam;

    @ManyToOne
    @Getter @Setter
    private Round round;

    public Match(Team homeTeam, Team awayTeam, int homeScore, int awayScore, Round round) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeScore = homeScore;
        this.awayScore = awayScore;
        this.round = round;
    }

    @Override
    public String toString() {
        return "Match{" +
                "id=" + id +
                ", homeScore=" + homeScore +
                ", awayScore=" + awayScore +
                '}';
    }
}
