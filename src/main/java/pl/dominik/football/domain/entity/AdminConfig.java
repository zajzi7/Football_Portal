package pl.dominik.football.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToOne;

@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@NoArgsConstructor
public class AdminConfig {

    @Id
    @Getter
    private int id = 1;

    @Getter @Setter
    @OneToOne(cascade = CascadeType.ALL)
    private Season currentSeason;

    @Getter @Setter
    @OneToOne
    private Team favouriteTeam;

    @Getter @Setter
    private boolean importantMessageFlag;

    @Getter @Setter
    @Lob
    private String importantMessageContent;

}
