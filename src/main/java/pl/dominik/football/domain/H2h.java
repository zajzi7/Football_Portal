package pl.dominik.football.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
public class H2h {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
    @Id
    private int id;

    @OneToOne
    @Getter @Setter
    private Season season;

    @OneToOne
    @Getter @Setter
    private Team teamOne;

    @OneToOne
    @Getter @Setter
    private Team teamTwo;

    @Getter @Setter
    private int pointsH2h;

    @Getter @Setter
    private int goalsDiffH2h;

    @Getter @Setter
    private int goalsAwayH2h;

}
