package pl.dominik.football.domain.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.dominik.football.domain.Player;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class PlayerRepository {

    @PersistenceContext
    EntityManager em;

    @Transactional
    public void createPlayer(String firstName, String lastName) {
        Player player = new Player(firstName, lastName);
        em.persist(player);
    }
}
