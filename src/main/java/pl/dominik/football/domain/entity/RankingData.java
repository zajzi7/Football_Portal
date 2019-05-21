package pl.dominik.football.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import pl.dominik.football.services.H2hService;
import pl.dominik.football.utilities.BeanUtil;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PreRemove;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
public class RankingData {

    @Id
    @Getter @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @Getter @Setter
    private Season season;

    @OneToOne
    @Getter @Setter
    private Team team;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "ranking")
    @Getter @Setter
    private Set<H2h> h2h = new HashSet<>();

    @Getter @Setter
    private int matchesPlayed;

    @Getter @Setter
    private int won;

    @Getter @Setter
    private int drawn;

    @Getter @Setter
    private int lost;

    @Getter @Setter
    private int points;

    @Getter @Setter
    private int goalsScored;

    @Getter @Setter
    private int goalsConceded;

    //TODO bonus points

    public void addH2h(H2h h2h) {
        this.h2h.add(h2h);
    }

    @PreRemove
    private void deleteRankingData() { //remove all H2h assigned to this RankingData
        H2hService h2hService = BeanUtil.getBean(H2hService.class);

        if (this.h2h != null) {
            for (H2h headToHead : this.h2h) {
                h2hService.delete(headToHead);
            }
        }

        List<H2h> h2hsByOppositeTeamAndSeason = h2hService.getH2hsByOppositeTeamAndSeason(this.team, this.season);
        if (h2hsByOppositeTeamAndSeason != null) {
            for (H2h headToHead : h2hsByOppositeTeamAndSeason) {
                h2hService.delete(headToHead);
            }
        }
    }
}
