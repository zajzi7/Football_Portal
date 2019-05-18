package pl.dominik.football.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import pl.dominik.football.domain.repository.RankingDataRepository;
import pl.dominik.football.services.H2hService;
import pl.dominik.football.utilities.BeanUtil;
import pl.dominik.football.utilities.RankingDataComponent;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PreRemove;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@ToString
public class Team {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
    @ToString.Exclude
    @Id
    private int id;

    @NotNull(message = "{pl.team.validation.notNull.message}")
    @Size(min = 2, message = "{pl.team.validation.size.message}")
    @Column(unique = true)
    @Getter @Setter
    private String teamName;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "homeTeam")
    @Getter @Setter
    @ToString.Exclude
    private Set<Match> matchHomeTeam; //It is mainly here to help @PreRemove interface

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "awayTeam")
    @Getter @Setter
    @ToString.Exclude
    private Set<Match> matchAwayTeam; //It is mainly here to help @PreRemove interface

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany
    @ToString.Exclude
    @Getter @Setter
    private Set<Season> season = new HashSet<>();

    public Team(String teamName) {
        this.teamName = teamName;
    }

    //===Logical part===

    public void assignToSeason(Season season) {
        this.season.add(season);
    }

    public void removeFromSeason(Season season) {
        this.season.remove(season);
    }

    @PreRemove
    private void deleteFromSeasonAndMatches() {

        RankingDataComponent rankingDataComponent = BeanUtil.getBean(RankingDataComponent.class);
        H2hService h2hService = BeanUtil.getBean(H2hService.class);
        RankingDataRepository rankingDataRepository = BeanUtil.getBean(RankingDataRepository.class);
        Match matchReversed;

        for (Match m : matchAwayTeam) { //Set null on away teams in matches and undo RankingData
            if (m.getAwayTeam() == this) {
                rankingDataComponent.undoMatch(m, true);
                matchReversed = m.reverseMatch();
                rankingDataComponent.undoMatch(matchReversed, false);
                m.setAwayTeam(null);
            }
            if (m.getHomeTeam() == this) {
                rankingDataComponent.undoMatch(m, true);
                matchReversed = m.reverseMatch();
                rankingDataComponent.undoMatch(matchReversed, false);
                m.setHomeTeam(null);
            }
        }

        for (Match m : matchHomeTeam) { //Set null on home teams in matches and undo RankingData
            if (m.getAwayTeam() == this) {
                rankingDataComponent.undoMatch(m, true);
                matchReversed = m.reverseMatch();
                rankingDataComponent.undoMatch(matchReversed, false);
                m.setAwayTeam(null);
            }
            if (m.getHomeTeam() == this) {
                rankingDataComponent.undoMatch(m, true);
                matchReversed = m.reverseMatch();
                rankingDataComponent.undoMatch(matchReversed, false);
                m.setHomeTeam(null);
            }
        }

        for (Season s : season) { //Remove from season
            s.getTeams().remove(this);
        }

        //Remove from H2h
        for (H2h h2h : h2hService.getH2hsByOppositeTeam(this)) {
            if (h2h != null) {
                h2hService.delete(h2h);
            }
        }

        //Remove from RankingData
        RankingData ranking = rankingDataRepository.getRankingDataByTeam(this);
        if (ranking != null) {
            rankingDataRepository.delete(ranking);
        }
    }
}