package pl.dominik.football.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pl.dominik.football.utilities.validation.IsUniqueEmail;
import pl.dominik.football.utilities.validation.IsUniqueFirstName;
import pl.dominik.football.utilities.validation.IsUniqueUsername;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.Collections;

@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@NoArgsConstructor
public class User implements UserDetails {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
    @Id
    private int id;

    @Getter @Setter
    @NotNull(message = "{pl.user.validation.notNull.message}")
    @Size(min = 2, message = "{pl.user.validation.size.message}")
    @IsUniqueUsername(message = "{pl.user.validation.alreadyExist.message}")
    private String username;

    @Getter @Setter
    @NotNull(message = "{pl.user.validation.passwordNotNull.message}")
    @Size(min = 4, message = "{pl.user.validation.passwordSize.message}")
    private String password;

    @Getter @Setter
    @NotNull(message = "{pl.user.validation.firstNameNotNull}")
    @Size(min = 1, message = "{pl.user.validation.firstNameSize}")
    @IsUniqueFirstName(message = "{pl.user.validation.firstNameAlreadyExist}")
    private String firstName;

    @Getter @Setter
    @IsUniqueEmail(message = "{pl.user.validation.emailExist.message}")
    private String email;

    @Getter @Setter
    private String role;

    @Getter @Setter
    private boolean enabled = false;    //Activation of the user account

    //Constructor
    public User(String username, String firstName, String password, String role, boolean enabled) {
        this.username = username;
        this.firstName = firstName;
        this.password = password;
        this.role = role;
        this.enabled = enabled;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(role));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

}