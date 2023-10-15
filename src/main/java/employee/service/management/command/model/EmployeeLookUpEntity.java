package employee.service.management.command.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "employee_lookup")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeLookUpEntity {
    @Id
    @GeneratedValue
    private Long id;
    @Column(unique = true)
    private String email;
    @Version
    private Long version;
}
