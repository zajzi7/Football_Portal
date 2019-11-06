package pl.dominik.football.utilities.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = FirstNameValidator.class)
@Target( {ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface IsUniqueFirstName {

    String message() default "Nazwa własna jest już zajęta!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
