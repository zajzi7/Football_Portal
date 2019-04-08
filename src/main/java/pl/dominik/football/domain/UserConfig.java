package pl.dominik.football.domain;

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
    private int id = 1;

    @Getter @Setter
    @OneToOne(cascade = CascadeType.ALL)
    private Season currentSeason;

}
