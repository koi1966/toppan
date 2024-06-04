package toppan.example.toppan.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        log.debug("Finding user by username: {}", username);
        return userRepository.findByUsername(username)
                .map(entity -> new User(
                        entity.getUsername(),
                        entity.getPassword(),
                        List.of(new SimpleGrantedAuthority(entity.getStatus().name()))
                ))
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

}