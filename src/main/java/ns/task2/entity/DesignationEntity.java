package ns.task2.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "designation", uniqueConstraints = {@UniqueConstraint(columnNames = {"name"})})
public class DesignationEntity {
    @Id
    private String code;
    private String name;
    @JsonProperty
    private boolean isActive;
}
