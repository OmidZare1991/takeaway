package employee.service.management.core.domain;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Setter
@Getter
@ToString
@EqualsAndHashCode(exclude = "hobbies")
public class Employee implements Serializable {
    @Id
    private String uuid;
    private String email;
    private String fullName;
    private String birthdate;
    @CreationTimestamp
    private Date createdAt;

    @CollectionTable(name = "hobbies", joinColumns = @JoinColumn(name = "employee_id"))
    @ElementCollection
    @ToString.Exclude
    private List<String> hobbies = new ArrayList<>();
}
