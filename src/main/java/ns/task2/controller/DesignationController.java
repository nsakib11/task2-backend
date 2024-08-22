package ns.task2.controller;

import ns.task2.entity.DesignationEntity;
import ns.task2.service.DesignationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(value = "http://localhost:3000")
@RequestMapping("/designation")
public class DesignationController {

    private static final Logger logger = LoggerFactory.getLogger(DesignationController.class);

    private final DesignationService designationService;

    public DesignationController(DesignationService designationService) {
        this.designationService = designationService;
    }

    @PostMapping
    public ResponseEntity<DesignationEntity> createDesignation(@RequestBody DesignationEntity designationEntity) {
        logger.info("Received request to create designation with code: {}", designationEntity.getCode());
        DesignationEntity createdDesignation = designationService.saveDesignation(designationEntity);
        logger.info("Designation with code '{}' created successfully", designationEntity.getCode());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDesignation);
    }

    @GetMapping
    public List<DesignationEntity> getAllDesignation() {
        logger.info("Received request to fetch all designations");
        return designationService.getAllDesignation();
    }

    @GetMapping("/{code}")
    public ResponseEntity<DesignationEntity> getDesignationById(@PathVariable String code) {
        logger.info("Received request to fetch designation with code: {}", code);
        DesignationEntity designation = designationService.getDesignationById(code);
        logger.info("Designation with code '{}' fetched successfully", code);
        return ResponseEntity.ok().body(designation);
    }

    @PutMapping("/{code}")
    public ResponseEntity<DesignationEntity> updateDesignation(@PathVariable String code, @RequestBody DesignationEntity designationEntity) {
        logger.info("Received request to update designation with code: {}", code);
        DesignationEntity updatedDesignation = designationService.updateDesignation(code, designationEntity);
        logger.info("Designation with code '{}' updated successfully", code);
        return ResponseEntity.ok().body(updatedDesignation);
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<String> deleteDesignation(@PathVariable String code) {
        logger.info("Received request to delete designation with code: {}", code);
        designationService.deleteDesignation(code);
        logger.info("Designation with code '{}' deleted successfully", code);
        return ResponseEntity.ok("Designation with code '" + code + "' has been deleted successfully");
    }
}
