package pl.dominik.football.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import pl.dominik.football.domain.repository.RankingDataRepository;
import pl.dominik.football.services.H2hService;
import pl.dominik.football.utilities.BeanUtil;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PreRemove;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@NoArgsConstructor
@ToString
public class Season {

    @Id
    @Getter @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "season", cascade = CascadeType.REMOVE)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @Getter @Setter
    @ToString.Exclude
    private List<Round> rounds = new ArrayList<>();

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany(mappedBy = "season")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @Getter @Setter
    @ToString.Exclude
    private Set<Team> teams = new HashSet<>();

    @NotNull(message = "{pl.season.validation.notNull.message}")
    @Size(min = 2, message = "{pl.season.validation.size.message}")
    @Column(unique = true)
    @Getter @Setter
    @ToString.Exclude
    private String seasonName;

    public Season(String seasonName) {
        this.seasonName = seasonName; //constructor to delete in the future
    }

    //===Logical part===

    public void addRound(Round round) {
        this.rounds.add(round);
    }

    public void addTeam(Team team) {
        this.teams.add(team);
    }

    public void removeTeam(Team team) {
        this.teams.remove(team);
    }

    @PreRemove
    private void deleteSeason() {
        //Teams
        for (Team t : teams) {
            t.getSeason().remove(this);
        }

        //H2h
        H2hService h2hService = BeanUtil.getBean(H2hService.class);
        List<H2h> h2hsBySeason = h2hService.getH2hsBySeason(this);
        if (h2hsBySeason != null) {
            for (H2h h2h : h2hsBySeason) {
                h2hService.delete(h2h);
            }
        }

        //RankingData
        RankingDataRepository rankingDataRepository = BeanUtil.getBean(RankingDataRepository.class);
        List<RankingData> rankingList = rankingDataRepository.getRankingDataBySeasonId(this.getId());

        if (rankingList != null) {
            for (RankingData ranking : rankingList) {
                ranking.setH2h(null);
                rankingDataRepository.delete(ranking);
            }
        }
    }
}
