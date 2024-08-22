package ns.task2.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "employee")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class EmployeeEntity {

    @Id
    private String id;
    private String name;
    private String mobile;
    private String email;
    private String department;
    private String designation;
    private String dob;
    private String qualification;
    private String address;
    private String remark;

    @Enumerated(EnumType.STRING)
    private EmployeeStatus status;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<RequisitionEntity> requisitions;

    public enum EmployeeStatus {
        ACTIVE, INACTIVE, DELETED
    }
}
