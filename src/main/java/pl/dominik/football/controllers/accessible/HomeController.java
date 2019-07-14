package pl.dominik.football.controllers.accessible;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.dominik.football.domain.repository.NewsRepository;
import pl.dominik.football.services.NewsService;

@Controller
public class HomeController {

    @Autowired
    NewsRepository newsRepository;

    @Autowired
    NewsService newsService;

    @RequestMapping("/layout")
    public String layout() {
        return "fragments/layout";
    }

    @RequestMapping("/alayout")
    public String adminLayout() {
        return "admin/admin-layout";
    }

    @RequestMapping("/news/{id}")
    public String fullNews(@PathVariable("id") int newsId, Model model) {
        model.addAttribute("news", newsService.getById(newsId));
        return "full-news";
    }

    @RequestMapping("/")
    public String homepage(Model model, @RequestParam(defaultValue = "1") int page) {

        //News
        model.addAttribute("newsList", newsRepository.findAll(PageRequest.of(page - 1, 6,
                Sort.by(Sort.Direction.DESC, "dateTime"))));

        model.addAttribute("currentPage", page);

        return "news";
    }

}
