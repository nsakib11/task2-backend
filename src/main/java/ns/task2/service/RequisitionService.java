package ns.task2.service;

import ns.task2.entity.EmployeeEntity;
import ns.task2.entity.RequisitionEntity;
import ns.task2.exception.requisition.RequisitionNotFoundException;
import ns.task2.repository.EmployeeRepository;
import ns.task2.repository.RequisitionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class RequisitionService {

    private final RequisitionRepository requisitionRepository;
    private final EmployeeRepository employeeRepository;

    public RequisitionService(RequisitionRepository requisitionRepository, EmployeeRepository employeeRepository) {
        this.requisitionRepository = requisitionRepository;
        this.employeeRepository = employeeRepository;
    }

    @Transactional
    public List<RequisitionEntity> saveRequisitions(List<RequisitionEntity> requisitionEntities, String employeeId) {
        EmployeeEntity employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RequisitionNotFoundException("Employee not found with ID: " + employeeId));

        requisitionEntities.forEach(req -> {
            req.setEmployee(employee);
            req.setOrderId(generateOrderId(employeeId));
            req.setStatus(RequisitionEntity.Status.INITIATED);  // Set initial status
        });

        return requisitionRepository.saveAll(requisitionEntities);
    }

    private String generateOrderId(String employeeId) {
        String prefix = "ORD-EMP";
        String dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        return String.format("%s%s-%s", prefix, employeeId, dateTime);
    }

    public RequisitionEntity getRequisitionByOrderId(String orderId) {
        return requisitionRepository.findByOrderId(orderId)
                .orElseThrow(() -> new RequisitionNotFoundException("Requisition with orderId '" + orderId + "' not found"));
    }
    public List<RequisitionEntity> getAllRequisitions() {
        return requisitionRepository.findAll();
    }
    public List<RequisitionEntity> getRequisitionsByProduct(String product) {
        return requisitionRepository.findByProduct(product);
    }

    public List<RequisitionEntity> getRequisitionsByEmployeeId(String employeeId) {
        return requisitionRepository.findByEmployeeId(employeeId);
    }

    public EmployeeEntity getEmployeeByOrderId(String orderId) {
        RequisitionEntity requisition = requisitionRepository.findByOrderId(orderId)
                .orElseThrow(() -> new RequisitionNotFoundException("Requisition with orderId " + orderId + " not found"));

        return requisition.getEmployee();
    }

    public RequisitionEntity updateRequisitionStatus(String orderId, RequisitionEntity.Status status) {
        RequisitionEntity requisition = getRequisitionByOrderId(orderId);
        requisition.setStatus(status);
        return requisitionRepository.save(requisition);
    }
}
