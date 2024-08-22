package ns.task2.controller;

import ns.task2.entity.QualificationEntity;
import ns.task2.service.QualificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(value = "http://localhost:3000")
@RequestMapping("/qualification")
public class QualificationController {

    private static final Logger logger = LoggerFactory.getLogger(QualificationController.class);

    private final QualificationService qualificationService;

    public QualificationController(QualificationService qualificationService) {
        this.qualificationService = qualificationService;
    }

    @PostMapping
    public ResponseEntity<QualificationEntity> createQualification(@RequestBody QualificationEntity qualificationEntity) {
        logger.info("Received request to create qualification with id: {}", qualificationEntity.getId());
        QualificationEntity createdQualification = qualificationService.saveQualification(qualificationEntity);
        logger.info("Qualification with id '{}' created successfully", createdQualification.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdQualification);
    }

    @GetMapping
    public List<QualificationEntity> getAllQualifications() {
        logger.info("Received request to fetch all qualifications");
        return qualificationService.getAllQualifications();
    }

    @GetMapping("/{id}")
    public ResponseEntity<QualificationEntity> getQualificationById(@PathVariable Long id) {
        logger.info("Received request to fetch qualification with id: {}", id);
        QualificationEntity qualification = qualificationService.getQualificationById(id);
        logger.info("Qualification with id '{}' fetched successfully", id);
        return ResponseEntity.ok().body(qualification);
    }

    @PutMapping("/{id}")
    public ResponseEntity<QualificationEntity> updateQualification(@PathVariable Long id, @RequestBody QualificationEntity qualificationEntity) {
        logger.info("Received request to update qualification with id: {}", id);
        QualificationEntity updatedQualification = qualificationService.updateQualification(id, qualificationEntity);
        logger.info("Qualification with id '{}' updated successfully", id);
        return ResponseEntity.ok().body(updatedQualification);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteQualification(@PathVariable Long id) {
        logger.info("Received request to delete qualification with id: {}", id);
        qualificationService.deleteQualification(id);
        logger.info("Qualification with id '{}' deleted successfully", id);
        return ResponseEntity.ok("Qualification with id '" + id + "' has been deleted successfully");
    }
}
