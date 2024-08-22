package ns.task2.controller;

import ns.task2.entity.EmployeeEntity;
import ns.task2.entity.RequisitionEntity;
import ns.task2.service.RequisitionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(value = "http://localhost:3000")
@RequestMapping("/requisitions")
public class RequisitionController {

    private final RequisitionService requisitionService;

    public RequisitionController(RequisitionService requisitionService) {
        this.requisitionService = requisitionService;
    }

    @PostMapping("/{employeeId}")
    public ResponseEntity<List<RequisitionEntity>> createRequisitions(
            @PathVariable String employeeId,
            @RequestBody List<RequisitionEntity> requisitionEntities) {

        List<RequisitionEntity> createdRequisitions = requisitionService.saveRequisitions(requisitionEntities, employeeId);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRequisitions);
    }

    @GetMapping
    public List<RequisitionEntity> getAllRequisitions() {
        return requisitionService.getAllRequisitions();
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<RequisitionEntity> getRequisitionByOrderId(@PathVariable String orderId) {
        RequisitionEntity requisition = requisitionService.getRequisitionByOrderId(orderId);
        return ResponseEntity.ok().body(requisition);
    }

    @GetMapping("/{product}")
    public ResponseEntity<List<RequisitionEntity>> getRequisitionsByProduct(@PathVariable String product) {
        List<RequisitionEntity> requisitions = requisitionService.getRequisitionsByProduct(product);
        return ResponseEntity.ok().body(requisitions);
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<RequisitionEntity>> getRequisitionsByEmployeeId(@PathVariable String employeeId) {
        List<RequisitionEntity> requisitions = requisitionService.getRequisitionsByEmployeeId(employeeId);
        return ResponseEntity.ok().body(requisitions);
    }

    @GetMapping("/details/{orderId}")
    public ResponseEntity<EmployeeEntity> getEmployeeByOrderId(@PathVariable String orderId) {
        EmployeeEntity employee = requisitionService.getEmployeeByOrderId(orderId);
        return ResponseEntity.ok().body(employee);
    }

    @PatchMapping("/status/{orderId}")
    public ResponseEntity<RequisitionEntity> updateRequisitionStatus(
            @PathVariable String orderId,
            @RequestParam String status) {

        // Convert the string status to the enum Status
        RequisitionEntity.Status statusEnum;
        try {
            statusEnum = RequisitionEntity.Status.valueOf(status.toUpperCase().replace(" ", "_"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }

        RequisitionEntity updatedRequisition = requisitionService.updateRequisitionStatus(orderId, statusEnum);
        return ResponseEntity.ok().body(updatedRequisition);
    }
}
