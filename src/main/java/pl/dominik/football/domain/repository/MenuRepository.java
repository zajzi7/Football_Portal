package pl.dominik.football.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.dominik.football.domain.entity.Menu;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Integer> {



}
