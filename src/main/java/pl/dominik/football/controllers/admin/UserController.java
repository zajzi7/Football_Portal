package pl.dominik.football.controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.dominik.football.domain.entity.User;
import pl.dominik.football.services.UserDetailsImpl;

import javax.validation.Valid;

@Controller
public class UserController {

    //Auxiliary variable to get String value from validation.messages.properties and add to the error
    @Value("${pl.user.validation.passwordNotMatch}")
    String passwordDoNotMatch;

    @Autowired
    UserDetailsImpl userDetails;

    @Autowired
    PasswordEncoder passwordEncoder;

    @RequestMapping("/register")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        return "admin/register-user";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String registerUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult,
                               @RequestParam("confirmPassword") String confirmPassword,
                               RedirectAttributes redirectAttributes) {

        //If the password doesn't match with the 'confirm password' field
        if (!(user.getPassword().equals(confirmPassword))) {
            ObjectError error = new ObjectError("passwordDoNotMatch", passwordDoNotMatch);
            bindingResult.addError(error);
        }

        //Check if the username or password contains errors(validation @NotNull, @Size)
        if (bindingResult.hasErrors()) {
            return "admin/register-user";
        }

        if (user.getEmail().equals("")) { //If empty email then set it to null
            user.setEmail(null);
        }

        user.setEnabled(false); //It's not necessary, but to be sure I set it up again (it's already set in the User entity)
        user.setRole("ROLE_EDITOR");
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userDetails.saveUser(user);

        redirectAttributes.addFlashAttribute("flash.message",
                "Zostałeś zarejestrowany poprawnie. Poczekaj na aktywację konta przez administratora");
        return "redirect:/register";
    }

}
