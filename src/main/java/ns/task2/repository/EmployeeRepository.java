package ns.task2.repository;

import ns.task2.entity.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<EmployeeEntity, String> {
    EmployeeEntity findByEmail(String email);
    List<EmployeeEntity> findByStatus(EmployeeEntity.EmployeeStatus status);

    @Query("SELECT e FROM EmployeeEntity e WHERE e.status != 'DELETED'")
    List<EmployeeEntity> findAll();

    @Query("SELECT e FROM EmployeeEntity e WHERE e.id = :id AND e.status != 'DELETED'")
    Optional<EmployeeEntity> findById(String id);
}
