package pl.dominik.football.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.dominik.football.utilities.MatchEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@EntityListeners(MatchEntityListener.class)
@NoArgsConstructor
public class Match {

    @Id
    @Getter @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Getter @Setter
    private LocalDate matchDate;

    @Getter @Setter
    private LocalTime matchStartTime;

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

    //===Logical part===

    public boolean someValueIsNull() {
        if (this.homeTeam == null ||
                this.awayTeam == null ||
                this.homeScore == null ||
                this.awayScore == null) {
            return true;
        }
        return false;
    }

    public Match reverseMatch() {
        Match matchReversed = new Match();
        matchReversed.setRound(this.round);
        matchReversed.setHomeTeam(this.awayTeam);
        matchReversed.setAwayTeam(this.homeTeam);
        matchReversed.setHomeScore(this.awayScore);
        matchReversed.setAwayScore(this.homeScore);
        return matchReversed;
    }

}
