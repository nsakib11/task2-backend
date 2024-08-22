package ns.task2.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "product")

public class ProductEntity {
    @Id
    private String code;
    private String name;

    private String description;

}
