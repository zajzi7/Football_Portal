package pl.dominik.football.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.dominik.football.domain.entity.Season;
import pl.dominik.football.domain.entity.Team;
import pl.dominik.football.domain.entity.UserConfig;

@Repository
public interface UserConfigRepository extends JpaRepository<UserConfig, Integer> {

    UserConfig findByIdEquals(int id);

    //Get current season
    @Query("select uc.currentSeason from UserConfig uc where uc.id=1")
    Season getCurrentSeason();

    @Query("select uc.favouriteTeam from UserConfig uc where uc.id=1")
    Team getFavouriteTeam();

}
