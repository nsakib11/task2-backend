package ns.task2.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@Entity
@Table(name = "requisition")
public class RequisitionEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Auto-generated primary key

    @Column(unique = true)
    private String orderId;

    private String product;
    private int quantity;
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    @JsonIgnore
    @JsonBackReference
    private EmployeeEntity employee; // Foreign key to EmployeeEntity

    @Enumerated(EnumType.STRING)
    private Status status;



    public enum Status {
        INITIATED,
        ORDER_PLACED,
        WAITING_FOR_DELIVERY,
        READY_FOR_DELIVERY,
        DELIVERED
    }
}
