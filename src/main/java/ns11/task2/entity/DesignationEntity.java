package ns11.task2.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "designation")
public class DesignationEntity {
    @Id
    private String code;
    private String name;
    @JsonProperty
    private boolean isActive;
}
