package pl.dominik.football.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.dominik.football.domain.entity.AdminConfig;

@Repository
public interface AdminConfigRepository extends JpaRepository<AdminConfig, Integer> {

}
