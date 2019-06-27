package pl.dominik.football.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.dominik.football.domain.entity.News;
import pl.dominik.football.domain.entity.Season;
import pl.dominik.football.domain.repository.NewsRepository;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@Service
public class NewsServiceImpl implements NewsService{

    @Autowired
    NewsRepository newsRepository;

    @Override
    public void save(News news) {
        newsRepository.save(news);
    }

    @Override
    public List<News> getAllNews() {
        return newsRepository.findAll();
    }

    @Override
    public News getById(int id) {

        Optional<News> result = newsRepository.findById(id);

        News news = null;

        if (result.isPresent()) {
            news = result.get();
        } else throw new RuntimeException("News not found, ID: " + id);

        return news;
    }

    @Override
    public void addNews(String title, String image, String content) {
        News news = new News();
        news.setTitle(title);
        news.setContent(content);
        news.setMainImageSource(image);
        news.setDateTime(LocalDateTime.now(ZoneId.of("Europe/Paris")));
        newsRepository.save(news);
    }


}
