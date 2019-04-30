package pl.dominik.football.services;

import pl.dominik.football.domain.entity.Team;

import java.util.List;

public interface TeamService {

    void createTeam(String teamName);

    void saveTeam(Team team);

    void deleteTeam(int id);

    Team getTeamByName(String teamName);

    Team getTeamById(int id);

    List<Team> getTeamsBySeasonId(int seasonId);

    List<Team> getAllTeams();

}