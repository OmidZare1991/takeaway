package employee.service.management.query.repository;

import employee.service.management.core.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {
    Employee findByEmail(String email);

    @Query(value = "SELECT * FROM employee ORDER BY created_at LIMIT :pageSize OFFSET :offset",
            nativeQuery = true)
    List<Employee> findEmployeesWithPagination(@Param("pageSize") int pageSize, @Param("offset") long offset);

}
