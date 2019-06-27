package pl.dominik.football.services;

import pl.dominik.football.domain.entity.News;

import java.util.List;

public interface NewsService {

    void save(News news);

    List<News> getAllNews();

    News getById(int id);

    void addNews(String title, String image, String content);
}
