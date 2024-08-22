package ns.task2.repository;


import ns.task2.entity.RequisitionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface RequisitionRepository extends JpaRepository<RequisitionEntity, Long> {
    Optional<RequisitionEntity> findByOrderId(String orderId);
    List<RequisitionEntity> findByProduct(String product);
    List<RequisitionEntity> findByEmployeeId(String employeeId);
    List<RequisitionEntity> findByStatus(RequisitionEntity.Status status);

}
