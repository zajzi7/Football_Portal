package pl.dominik.football.controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import pl.dominik.football.domain.entity.News;
import pl.dominik.football.domain.repository.NewsRepository;
import pl.dominik.football.services.NewsService;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Controller
public class NewsController {

    @Autowired
    NewsService newsService;

    @Autowired
    NewsRepository newsRepository;

    //Remove leading and trailing whitespace
    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @RequestMapping("/news")
    public String showNews(Model model, @RequestParam(defaultValue = "1") int page) {

        model.addAttribute("newsList", newsRepository.findAll(PageRequest.of(page - 1, 6,
                Sort.by(Sort.Direction.DESC, "id"))));

        model.addAttribute("currentPage", page);
        return "admin/manage-news";
    }

    @RequestMapping(value = "/create-news")
    //Form to create new news by admin
    public String createNews(Model model) {
        model.addAttribute("news", new News());
        return "admin/create-news";
    }

    @RequestMapping(value = "/save-news", method = RequestMethod.POST)
    public String saveNews(@Valid @ModelAttribute("news") News news, BindingResult bindingResult) {

        //Validation if news title is empty
        if (bindingResult.hasErrors()) {
            return "admin/create-news";
        }

        //Modify the url introduced by the admin in case of domain change(for example "http://my-domain.pl/img.jpg" to "img.jpg")
        try {
            String mainImageSource = news.getMainImageSource();

            String regex = "https?://[-A-Za-z0-9:.]*/";
            String[] imgLocation = mainImageSource.split(regex, 2);

            news.setMainImageSource("/" + imgLocation[1]);
        } catch (IndexOutOfBoundsException | NullPointerException e) {
            //Nothing to do (link is already modified or is empty)
        }

        //Set the current time and date
        news.setDateTime(LocalDateTime.now(ZoneId.of("Europe/Paris")));

        newsService.save(news);
        return "redirect:/news";
    }

    @RequestMapping(value = "/news/delete/{newsId}")
    public String deleteNews(@PathVariable("newsId") int newsId) {
        newsService.deleteNews(newsId);
        return "redirect:/news";
    }

    @RequestMapping(value = "/news/edit/{newsId}")
    public String updateNews(@PathVariable("newsId") int newsId, Model model) {
        News news = newsService.getById(newsId);
        model.addAttribute("news", news);
        return "admin/create-news";
    }

}