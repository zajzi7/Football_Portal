package pl.dominik.football.domain;

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

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "season")
    @Getter @Setter
    private List<Round> rounds = new ArrayList<>();

    public Season(String seasonName) {
        this.seasonName = seasonName;
    }

}
