package pl.dominik.football.domain.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import pl.dominik.football.domain.entity.Image;

public interface ImageRepository extends PagingAndSortingRepository<Image, Integer> {

    Image findByName(String name);

}
