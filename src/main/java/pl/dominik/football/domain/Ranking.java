package pl.dominik.football.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
public class Ranking {

    @Id
    @Getter @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @Getter @Setter
    private Season season;

    @OneToMany
    @Getter @Setter
    private List<Team> teams = new ArrayList<>();

    public Ranking(Season season) {
        this.season = season;
    }

}
