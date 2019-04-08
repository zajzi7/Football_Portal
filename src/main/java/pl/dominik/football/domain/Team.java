package pl.dominik.football.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
public class Team {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
    @Id
    private int id;

    @Getter @Setter
    private String teamName;

    public Team(String teamName) {
        this.teamName = teamName;
    }

}
