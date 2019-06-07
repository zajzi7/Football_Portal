package pl.dominik.football.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.dominik.football.domain.entity.News;

public interface NewsRepository extends JpaRepository<News, Integer> {



}
