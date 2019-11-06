package pl.dominik.football.utilities.validation;

import org.springframework.beans.factory.annotation.Autowired;
import pl.dominik.football.domain.entity.User;
import pl.dominik.football.services.UserDetailsImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FirstNameValidator implements ConstraintValidator<IsUniqueFirstName, String> {

    @Autowired
    UserDetailsImpl userDetails;

//    UserDetailsImpl userDetails;

    @Override
    public void initialize(IsUniqueFirstName isUniqueFirstName) {
//        userDetails = BeanUtil.getBean(UserDetailsImpl.class);
//        isUniqueFirstName.message();
    }

    @Override
    public boolean isValid(String firstName, ConstraintValidatorContext context) {
        if (userDetails != null) {
            User user = userDetails.findByFirstName(firstName);

            if (user != null)
                return false;
        }
        return true;
    }
}
