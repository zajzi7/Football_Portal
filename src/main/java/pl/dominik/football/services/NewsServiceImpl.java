package pl.dominik.football.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.dominik.football.domain.entity.News;
import pl.dominik.football.domain.repository.NewsRepository;

import java.util.List;

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
    public void addNews(String title, String image, String content) {
        News news = new News();
        news.setTitle(title);
        news.setContent(content);
        news.setMainImageSource(image);
        newsRepository.save(news);
    }


}
