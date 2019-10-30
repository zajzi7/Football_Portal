package pl.dominik.football.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter;
import pl.dominik.football.domain.entity.User;
import pl.dominik.football.services.UserDetailsImpl;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsImpl userDetails;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetails);
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/resources/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/admin/important-message/**")
                .hasRole("ADMIN")
                .antMatchers("/admin/**")
                .hasAnyRole("EDITOR", "ADMIN")
                .and()
                .formLogin()
                .defaultSuccessUrl("/admin/", true)
                .permitAll()
                .and()
                .logout().permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/access-denied");

        //CSP security
        http.headers()
//                .contentSecurityPolicy("default-src 'self'; " +
//                        "script-src 'self' " +
//                        "'sha256-H8rtBlErmyK9iXLU+Tw8fr6EGzAo2OSJwZ1JtJ73xV0=' " + //Tooltips jQuery
//                        "'sha256-cKkS3+z2QlxaQvQR7ZtSYqJYqdinuCGJ2JEYLk5ldbE=' " + //Counter for the next match
//                        "'sha256-HnHrcVFvhFkUsi9/daMLFaZsZ7yYRrrDvelH0MtpZUA=' " + //CKEditor
//                        "'sha256-fAOpFu8gVgoGjnyvIhc5D8pdgycG0gcaErXw5PjpCgU='; " + //CKEditor
//                        "img-src 'self' data: " +
//                        "https://*.fbcdn.net " + //Facebook images
//                        "https://*.fbcdn.com " + //Facebook images
//                        "https://*.fbcdn.pl; " + //Facebook images
//                        "style-src 'self' " +
//                        "'sha256-ZVjd2zfSTfAVh1y7eCcNk0SPGUQOP/H8vzrFJIVgg90=' " + //CKEditor
//                        "https://getbootstrap.com " +
//                        "https://maxcdn.bootstrapcdn.com; " +
//                        "")
//                .and()
//                .featurePolicy("ambient-light-sensor 'none'; autoplay 'none'; accelerometer 'none'; " +
//                        "camera 'none'; display-capture 'none'; document-domain 'none'; encrypted-media 'none'; " +
//                        "fullscreen 'none'; geolocation 'none'; gyroscope 'none'; magnetometer 'none'; " +
//                        "microphone 'none'; midi 'none'; payment 'none'; picture-in-picture 'none'; speaker 'none'; " +
//                        "sync-xhr 'none'; usb 'none'; wake-lock 'none'; webauthn 'none'; vr 'none'; " +
//                        "focus-without-user-activation 'none'; ")
//                .and()
                .referrerPolicy(ReferrerPolicyHeaderWriter.ReferrerPolicy.STRICT_ORIGIN);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void createUsers() {
        User user = new User("user", passwordEncoder().encode("123"), "ROLE_EDITOR", true);
        User admin = new User("admin", passwordEncoder().encode("123"), "ROLE_ADMIN", true);
        userDetails.saveUser(user);
        userDetails.saveUser(admin);
    }

}
