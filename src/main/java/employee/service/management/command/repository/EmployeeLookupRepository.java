package employee.service.management.command.repository;

import employee.service.management.command.model.EmployeeLookUpEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeLookupRepository extends JpaRepository<EmployeeLookUpEntity,String> {
    EmployeeLookUpEntity findByEmail(String email);
}
