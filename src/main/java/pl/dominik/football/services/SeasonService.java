package pl.dominik.football.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.dominik.football.domain.repository.SeasonRepository;

@Service
public class SeasonService {

    @Autowired
    SeasonRepository seasonRepository;

}
