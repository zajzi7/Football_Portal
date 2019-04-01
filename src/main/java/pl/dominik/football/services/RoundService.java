package pl.dominik.football.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.dominik.football.domain.repository.RoundRepository;

@Service
public class RoundService {

    @Autowired
    RoundRepository roundRepository;

}
