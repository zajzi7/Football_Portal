package pl.dominik.football.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@NoArgsConstructor
@ToString
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"round_number", "season_id"})})
public class Round {

    //Class to store single rounds. Also known as matchday(all matches of the week; usually played in one day)
    @Id
    @Getter @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "round", cascade = CascadeType.REMOVE)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @Getter @Setter
    private List<Match> matches = new ArrayList<>();

    @ManyToOne
    @Getter @Setter
    @JoinColumn(name = "season_id")
    private Season season;

    //Field to store round number in the current season(example: Round 1, season 2019/2020)
    @Column(name = "round_number")
    @Min(value = 1, message = "{pl.round.validation.min}")
    @Max(value = 99, message = "{pl.round.validation.max}")
    @Getter @Setter
    private int roundNumber;

    @Getter @Setter
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "{pl.round.validation.date.notNull.message}")
    private LocalDate roundStartDate;

    public Round(int roundNumber) {
        this.roundNumber = roundNumber;
    }

    public Round(LocalDate date) {
        this.roundStartDate = date;
    }

    //TODO add multiple rounds
}
