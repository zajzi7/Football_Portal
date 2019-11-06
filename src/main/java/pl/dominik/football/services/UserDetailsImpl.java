package pl.dominik.football.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.dominik.football.domain.entity.User;
import pl.dominik.football.domain.repository.UserRepository;

@Service
public class UserDetailsImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

    public User findByUsername(String username) throws NullPointerException {
        return userRepository.findByUsername(username);
    }

    public User findByFirstName(String firstName) throws NullPointerException {
        return userRepository.findByFirstName(firstName);
    }

    public User findByEmail(String email) throws NullPointerException {
        return userRepository.findByEmail(email);
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

}
