package pl.dominik.football.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@NoArgsConstructor
public class Match {

    @Id
    @Getter @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Getter @Setter
    private Integer homeScore; //not int for null possibility

    @Getter @Setter
    private Integer awayScore; //not int for null possibility

    @ManyToOne
    @Getter @Setter
    private Team homeTeam;

    @ManyToOne
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

}
