//package pl.dominik.football.services;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import pl.dominik.football.domain.entity.Player;
//import pl.dominik.football.domain.repository.PlayerRepository;
//
//import java.util.List;
//
//@Service
//public class PlayerServiceImpl implements PlayerService {
//
//    @Autowired
//    PlayerRepository playerRepository;
//
//    @Override
//    public void createPlayer(String firstName, String lastName) {
//        Player player = new Player(firstName, lastName);
//        playerRepository.save(player);
//    }
//
//    @Override
//    public void deletePlayer(int id) {
//        playerRepository.deleteById(id);
//    }
//
//    @Override
//    public List<Player> getAllPlayers() {
//        return playerRepository.findAll();
//    }
//
//
//}
