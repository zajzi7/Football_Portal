package pl.dominik.football.controllers.accessible;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.dominik.football.domain.repository.NewsRepository;

@Controller
public class HomeController {

    @Autowired
    NewsRepository newsRepository;

    @RequestMapping("/layout")
    public String layout() {
        return "layout";
    }

    @RequestMapping("/")
    public String homepage(Model model, @RequestParam(defaultValue = "1") int page) {

        //News
        model.addAttribute("newsList", newsRepository.findAll(PageRequest.of(page - 1, 6, Sort.by(Sort.Direction.DESC, "dateTime"))));
        model.addAttribute("currentPage", page);

        return "news";
    }

}
