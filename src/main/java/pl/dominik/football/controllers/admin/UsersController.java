package pl.dominik.football.controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.dominik.football.domain.entity.User;
import pl.dominik.football.services.UserDetailsImpl;

import java.util.List;

//Manage users
@Controller
@RequestMapping("/admin")
public class UsersController {

    @Autowired
    UserDetailsImpl userDetails;

    //Show all users
    @RequestMapping("/manage-users")
    public String users(Model model) {

        List<User> userList = userDetails.findAllUsers();
        model.addAttribute("userList", userList);

        return "admin/manage-users";
    }


}
