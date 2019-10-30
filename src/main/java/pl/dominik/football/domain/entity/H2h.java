package pl.dominik.football.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@NoArgsConstructor
@ToString
public class H2h {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
    @Id
    private int id;

    @ManyToOne
    @Getter @Setter
    private RankingData ranking;

    @ManyToOne
    @Getter @Setter
    private Season season; //Needed mainly for the RankingData @PreRemove

    @OneToOne
    @Getter @Setter
    private Team oppositeTeam;

    @Getter @Setter
    private int pointsH2h;

    @Getter @Setter
    private int goalsScoredH2h;

    @Getter @Setter
    private int goalsConcededH2h;

    @Getter @Setter
    private int goalsScoredAwayH2h;

}
