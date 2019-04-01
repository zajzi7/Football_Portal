package pl.dominik.football.domain.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.dominik.football.domain.Player;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class PlayerRepository {

    @PersistenceContext
    EntityManager em;

    @Transactional
    public void createPlayer(String firstName, String lastName) {
        Player player = new Player(firstName, lastName);
        em.persist(player);
    }

    @Transactional
    public void deletePlayer(int id) {
        em.remove(id);
    }

    @Transactional
    public void updatePlayer() {
        //TODO
    }

    public List<Player> getAllPlayers() {
        return em.createQuery("from Player", Player.class).getResultList();
    }

}
