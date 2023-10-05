package employee.service.management.core.security.config;

import employee.service.management.core.domain.Role;
import employee.service.management.core.domain.Users;
import employee.service.management.core.security.repository.UserRepository;
import employee.service.management.core.security.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import static employee.service.management.core.security.util.AdminDummyData.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class SeedDataConfig implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    @Override
    public void run(String... args) {

        if (userRepository.count() == 0) {

            Users admin = Users
                    .builder()
                    .firstname(FIRST_NAME.getValue())
                    .lastname(LASTNAME.getValue())
                    .email(EMAIL.getValue())
                    .password(passwordEncoder.encode(PASSWORD.getValue()))
                    .role(Role.ROLE_ADMIN)
                    .build();

            userService.save(admin);
        }
    }

}