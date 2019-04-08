package pl.dominik.football.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.dominik.football.justfortestsomething.TeamRepository;

@Service
public class TeamService {

    @Autowired
    TeamRepository teamRepository;


}
