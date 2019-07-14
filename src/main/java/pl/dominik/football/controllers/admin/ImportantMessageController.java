package pl.dominik.football.controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import pl.dominik.football.services.UserConfigService;

@Controller
public class ImportantMessageController {

    @Autowired
    UserConfigService userConfigService;

    @RequestMapping(value = "/important-message")
    //Form to edit important message by admin
    public String editImportantMessage(Model model) {
        model.addAttribute("importantMessageFlag", userConfigService.getImportantMessageFlag());
        model.addAttribute("importantMessageContent", userConfigService.getImportantMessageContent());
        return "admin/edit-important-message";
    }

    @RequestMapping(value = "/save-important-message", method = RequestMethod.POST)
    public String saveImportantMessage(@RequestParam(value = "importantMessageFlag", required = false) boolean flag,
                                       @RequestParam("importantMessageContent") String content) {

        userConfigService.setImportantMessageFlag(flag);
        userConfigService.setImportantMessageContent(content);
        return "redirect:/";
    }
}