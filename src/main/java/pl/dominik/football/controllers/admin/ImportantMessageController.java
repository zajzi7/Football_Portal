package pl.dominik.football.controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import pl.dominik.football.services.AdminConfigService;

@Controller
@RequestMapping("/admin")
public class ImportantMessageController {

    @Autowired
    AdminConfigService adminConfigService;

    @RequestMapping(value = "/important-message")
    //Form to edit important message by admin
    public String editImportantMessage(Model model) {
        model.addAttribute("importantMessageFlag", adminConfigService.getImportantMessageFlag());
        model.addAttribute("importantMessageContent", adminConfigService.getImportantMessageContent());
        return "admin/edit-important-message";
    }

    @RequestMapping(value = "/save-important-message", method = RequestMethod.POST)
    public String saveImportantMessage(@RequestParam(value = "importantMessageFlag", required = false) boolean flag,
                                       @RequestParam("importantMessageContent") String content) {

        adminConfigService.setImportantMessageFlag(flag);
        adminConfigService.setImportantMessageContent(content);
        return "redirect:/admin/important-message";
    }
}