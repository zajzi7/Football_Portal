package pl.dominik.football.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@ToString
public class Team {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
    @Id
    private int id;

    @Getter @Setter
    private String teamName;

    @ManyToOne
    @Getter @Setter
    private Season season;

    public Team(String teamName) {
        this.teamName = teamName;
    }

}
