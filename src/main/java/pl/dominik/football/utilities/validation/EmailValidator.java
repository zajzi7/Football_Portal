package pl.dominik.football.utilities.validation;

import org.springframework.beans.factory.annotation.Autowired;
import pl.dominik.football.domain.entity.User;
import pl.dominik.football.services.UserDetailsImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailValidator implements ConstraintValidator<IsUniqueEmail, String> {

    @Autowired
    UserDetailsImpl userDetails;

    @Override
    public void initialize(IsUniqueEmail isUniqueEmail) {
        isUniqueEmail.message();
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if (userDetails != null) {
            User user = userDetails.findByEmail(email);

            if (user != null)
                return false;
        }
        return true;
    }
}
