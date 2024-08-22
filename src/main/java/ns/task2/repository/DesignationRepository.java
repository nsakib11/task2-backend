package ns.task2.repository;

import ns.task2.entity.DesignationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DesignationRepository extends JpaRepository<DesignationEntity, String> {
    DesignationEntity findByName(String name);
}
