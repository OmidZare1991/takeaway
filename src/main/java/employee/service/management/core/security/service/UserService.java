package employee.service.management.core.security.service;

import employee.service.management.core.domain.Users;
import employee.service.management.core.security.exception.UserAlreadyFoundException;
import employee.service.management.core.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    public Users save(Users newUser) {
        Optional<Users> user = userRepository.findByEmail(newUser.getEmail());
        if (user.isPresent()) {
            throw new UserAlreadyFoundException("user with this email exists");
        }
        return userRepository.save(newUser);
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
