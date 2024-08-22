package ns.task2.repository;

import ns.task2.entity.DepartmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<DepartmentEntity, String> {
    DepartmentEntity findByName(String name);
}
