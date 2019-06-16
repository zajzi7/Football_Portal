package pl.dominik.football.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
@NoArgsConstructor
public class UserConfig {

    @Id
    @Getter
    private int id = 1;

    @Getter @Setter
    @OneToOne(cascade = CascadeType.ALL)
    private Season currentSeason;

    @Getter @Setter
    @OneToOne
    private Team favouriteTeam;

}
