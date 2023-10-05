package employee.service.management.core.security.mapper;

import employee.service.management.core.domain.Users;
import org.mapstruct.Mapper;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring")
public interface UserServiceMapper {

    default Users toUser(Users user, Users updatedUser){
        user.setUpdatedAt(LocalDateTime.now());
        user.setEmail(updatedUser.getEmail());
        user.setLastname(updatedUser.getLastname());
        user.setFirstname(updatedUser.getFirstname());
        user.setPassword(updatedUser.getPassword());
        return user;
    }
}
