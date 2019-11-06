package pl.dominik.football.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.dominik.football.domain.entity.RankingData;
import pl.dominik.football.domain.entity.Round;
import pl.dominik.football.domain.entity.Season;
import pl.dominik.football.domain.entity.Team;
import pl.dominik.football.domain.repository.RankingDataRepository;
import pl.dominik.football.domain.repository.RoundRepository;
import pl.dominik.football.domain.repository.SeasonRepository;
import pl.dominik.football.domain.repository.TeamRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class SeasonServiceImpl implements SeasonService {

    @Autowired
    SeasonRepository seasonRepository;

    @Autowired
    RoundRepository roundRepository;

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    RankingDataRepository rankingDataRepository;

    @Autowired
    H2hService h2hService;

    @Override
    public void createSeason(String seasonName) {
        Season season = new Season(seasonName);
        seasonRepository.save(season);
    }

    @Override
    public void saveSeason(Season season) {
        seasonRepository.save(season);
    }

    @Override
    public List<Season> getSeasonList() {
        return seasonRepository.findAll();
    }

    @Override
    public void deleteSeason(int id) {
        seasonRepository.deleteById(id);
    }

    @Override
    public Season getSeasonById(int id) {

        Optional<Season> result = seasonRepository.findById(id);

        Season season = null;

        if (result.isPresent()) {
            season = result.get();
        } else throw new RuntimeException("Season not found, ID: " + id);

        return season;
    }

    @Override
    public Season getSeason(String seasonName) {
        return seasonRepository.getSeasonBySeasonNameEquals(seasonName);
    }

    @Override
    public void addRound(Round round, Season season) {
        //TODO this method will be removed probably
        season.addRound(round);
        round.setSeason(season);
        roundRepository.save(round);
        seasonRepository.save(season);
    }

    @Override
    public void addTeam(Team team, Season season) {
        season.addTeam(team);
        team.assignToSeason(season);

        RankingData ranking = new RankingData();
        ranking.setTeam(team);
        ranking.setSeason(season);
        ranking = h2hService.generateH2h(ranking);
        rankingDataRepository.save(ranking);

        teamRepository.save(team);
        seasonRepository.save(season);
    }

    @Override
    public Set<Season> getSeasonsContainsTeam(Team team) {
        //Don't delete! Workaround necessary to edit team
        return seasonRepository.getSeasonsByTeamsEquals(team);
    }
}
