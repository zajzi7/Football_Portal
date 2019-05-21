package pl.dominik.football.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.dominik.football.domain.entity.H2h;
import pl.dominik.football.domain.entity.RankingData;
import pl.dominik.football.domain.entity.Season;
import pl.dominik.football.domain.entity.Team;
import pl.dominik.football.domain.repository.H2hRepository;
import pl.dominik.football.domain.repository.RankingDataRepository;

import java.util.List;

@Service
public class H2hServiceImpl implements H2hService {

    @Autowired
    TeamService teamService;

    @Autowired
    RankingDataRepository rankingDataRepository;

    @Autowired
    H2hRepository h2hRepository;

    @Override
    public void saveH2h(H2h h2h) {
        h2hRepository.save(h2h);
    }

    @Override
    public void deleteById(int id) {
        h2hRepository.deleteById(id);
    }

    @Override
    public void delete(H2h h2h) {
        h2hRepository.delete(h2h);
    }

    @Override
    public List<H2h> getH2hsByOppositeTeam(Team team) {
        return h2hRepository.getH2hsByOppositeTeam(team);
    }

    @Override
    public List<H2h> getH2hsBySeason(Season season) {
        return h2hRepository.getH2hsBySeason(season);
    }

    @Override
    public List<H2h> getH2hsByOppositeTeamAndSeason(Team team, Season season) {
        return h2hRepository.getH2hsByOppositeTeamAndSeason(team, season);
    }

    @Override
    public RankingData generateH2h(RankingData ranking) {
        List<Team> teamsFromSeason = teamService.getTeamsBySeasonId(ranking.getSeason().getId());
        Team team = ranking.getTeam(); //Ranking team

        if (teamsFromSeason.size() > 0) {

            /* (there is no need to subtract the upcoming team from the "teamsFromSeason",
                because the upcoming team is not yet in the database.
                The upcoming team is saved after creating the ranking)*/

            //The incoming team has a new h2h to each existing team(but not for himself)
            for (Team t : teamsFromSeason) {
                H2h h2h = new H2h();
                h2h.setRanking(ranking);
                h2h.setSeason(ranking.getSeason());
                h2h.setOppositeTeam(t);
                ranking.addH2h(h2h);
                rankingDataRepository.save(ranking);
                h2hRepository.save(h2h);
            }

            //Every existing team has a new h2h record in the database with an incoming team
            for (Team t : teamsFromSeason) {

                RankingData rankingOfExistingTeam =
                        rankingDataRepository.getRankingDataByTeamIdAndSeasonId(
                                t.getId(), ranking.getSeason().getId());

                H2h h2h = new H2h();
                h2h.setRanking(rankingOfExistingTeam);
                h2h.setSeason(ranking.getSeason());
                h2h.setOppositeTeam(team);
                rankingOfExistingTeam.addH2h(h2h);
                rankingDataRepository.save(rankingOfExistingTeam);
                h2hRepository.save(h2h);
            }
        }
        return ranking;
    }

    @Override
    public H2h getH2hByOppositeTeamAndRanking(Team team, RankingData ranking) {
        return h2hRepository.getH2hByOppositeTeamAndRanking(team, ranking);
    }

}