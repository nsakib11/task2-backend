package ns.task2.repository;

import ns.task2.entity.QualificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QualificationRepository extends JpaRepository<QualificationEntity, Long> {
}
