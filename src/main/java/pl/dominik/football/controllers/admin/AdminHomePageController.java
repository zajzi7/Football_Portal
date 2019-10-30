package pl.dominik.football.controllers.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminHomePageController {

    @RequestMapping("/")
    public String adminLayout() {
        return "admin/admin-layout";
    }

}
