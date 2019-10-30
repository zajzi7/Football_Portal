package pl.dominik.football.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.dominik.football.domain.entity.Match;
import pl.dominik.football.domain.entity.Round;
import pl.dominik.football.domain.entity.Season;
import pl.dominik.football.domain.repository.RoundRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class RoundServiceImpl implements RoundService {

    @Autowired
    RoundRepository roundRepository;

    @Autowired
    SeasonService seasonService;

    @Autowired
    AdminConfigService adminConfigService;

    @PersistenceContext
    EntityManager em;

    public void assignRoundToSeasonWithId(Round round, int seasonId) {
        Season season = seasonService.getSeasonById(seasonId);

        round.setSeason(season); //Assign round to season
        roundRepository.save(round);
        seasonService.saveSeason(season);
    }

    @Override
    public void createRound(int roundNumber, LocalDate date) {
        Round round = new Round(roundNumber);
        round.setRoundStartDate(date);
        roundRepository.save(round);
    }

    @Override
    public void saveRound(Round round) {
        roundRepository.save(round);
    }

    @Override
    public Round getRoundByInt(int roundNumber) {
        return roundRepository.getRoundByRoundNumberEquals(roundNumber);
    }

    @Override
    public Round getRoundBySeasonIdAndRoundNumber(int seasonId, int roundNumber) {
        return roundRepository.getRoundBySeasonIdAndRoundNumber(seasonId, roundNumber);
    }

    @Override
    public Round getRoundById(int id) {
        Optional<Round> result = roundRepository.findById(id);

        Round round = null;

        if (result.isPresent()) {
            round = result.get();
        } else throw new RuntimeException("Round not found, ID: " + id);

        return round;
    }

    @Override
    public List<Round> getRoundsBySeasonId(int id) {
        return roundRepository.getRoundsBySeason_Id(id);
    }

    @Override
    public void deleteRound(int id) {
        roundRepository.delete(getRoundById(id));
    }

    @Override
    @Transactional
    public int getSeasonIdByRoundId(int roundId) {
        return em.createQuery("select r.season.id from Round r where r.id=:roundId", Integer.class)
                .setParameter("roundId", roundId)
                .getSingleResult();
    }

    @Override
    public LocalDate generateNextRoundDefaultDate(Season season) {
        //Get all the rounds from the season
        List<Round> rounds = getRoundsBySeasonId(season.getId());

        if (rounds.size() > 0) { //If there is more than one Round in Season created so far

            //Sort list by the roundNumber
            rounds.sort((o1, o2) -> ((Integer) o2.getRoundNumber()).compareTo(o1.getRoundNumber()));

            //Get first(round with the biggest roundNumber) element
            Round lastRound = rounds.get(0);

            //Return last round date + 1 week
            return lastRound.getRoundStartDate().plusWeeks(1);
        }

        return null;
    }

    @Override
    public boolean roundHasOnlyEmptyMatches(Round round) {
        boolean flag = true;
        for (Match m : round.getMatches()) {
            if (m.getHomeScore() != null && m.getAwayScore() != null && m.getHomeTeam() != null && m.getAwayTeam() != null) {
                flag = false;
                break;
            }
        }
        return flag;
    }

    @Override
    public Round getLastRound(Season season) {
        List<Round> rounds = roundRepository.findLastRound(season, LocalDate.now());

        try {
            for (Round r : rounds) {
                //Check if the round has matches with results
                if (r.getMatches().size() == 0) {
                    continue;
                }
                if (roundHasOnlyEmptyMatches(r) == true) {
                    continue;
                } else {
                    return r;
                }
            }
        } catch (NullPointerException e) {
            //If the round search fails then look for another way
            try {
                //Sort list by the roundNumber
                rounds.sort((o1, o2) -> ((Integer) o2.getRoundNumber()).compareTo(o1.getRoundNumber()));

                //Get first(round with the biggest roundNumber) element
                return rounds.get(0);
            } catch (NullPointerException | ArrayIndexOutOfBoundsException ex) {
                return null;
            }
        }
        return null;
    }

}