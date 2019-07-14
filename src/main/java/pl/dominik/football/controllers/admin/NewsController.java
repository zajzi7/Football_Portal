package pl.dominik.football.controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.dominik.football.domain.entity.News;
import pl.dominik.football.services.NewsService;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Controller
public class NewsController {

    @Autowired
    NewsService newsService;

    @RequestMapping(value = "/create-news")
    //Form to create new news by admin
    public String createNews(Model model) {
        model.addAttribute("news", new News());
        return "admin/create-news";
    }

    @RequestMapping(value = "/save-news", method = RequestMethod.POST)
    public String saveNews(@ModelAttribute("news") News news) {
        news.setDateTime(LocalDateTime.now(ZoneId.of("Europe/Paris")));
        newsService.save(news);
        return "redirect:/";
    }

}