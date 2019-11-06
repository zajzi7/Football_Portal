package pl.dominik.football.utilities.validation;

import org.springframework.beans.factory.annotation.Autowired;
import pl.dominik.football.domain.entity.User;
import pl.dominik.football.services.UserDetailsImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UsernameValidator implements ConstraintValidator<IsUniqueUsername, String> {

    @Autowired
    UserDetailsImpl userDetails;

    @Override
    public void initialize(IsUniqueUsername isUniqueUsername) {
        isUniqueUsername.message();
    }

    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        if (userDetails != null) {
            User user = userDetails.findByUsername(username);

            if (user != null)
                return false;
        }
        return true;
    }
}
