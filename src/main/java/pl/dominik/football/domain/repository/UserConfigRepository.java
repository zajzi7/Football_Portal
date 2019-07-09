package pl.dominik.football.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.dominik.football.domain.entity.UserConfig;

@Repository
public interface UserConfigRepository extends JpaRepository<UserConfig, Integer> {

}
