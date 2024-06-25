package ns11.task2.controller;

import ns11.task2.entity.DesignationEntity;
import ns11.task2.service.DesignationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(value = "http://localhost:3000")
@RequestMapping("/designation")
public class DesignationController {

    private final DesignationService designationService;

    public DesignationController(DesignationService designationService) {
        this.designationService = designationService;
    }

    @PostMapping
    public ResponseEntity<DesignationEntity> createDesignation(@RequestBody DesignationEntity designationEntity) {
        DesignationEntity createdDesignation = designationService.saveDesignation(designationEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDesignation);
    }
    @GetMapping
    public List<DesignationEntity> getAllDesignation() {
        return designationService.getAllDesignation();
    }

    @GetMapping("/{code}")
    public ResponseEntity<DesignationEntity> getDesignationById(@PathVariable String code) {
        DesignationEntity designation = designationService.getDesignationById(code);
        return ResponseEntity.ok().body(designation);
    }
    @PutMapping("/{code}")
    public ResponseEntity<DesignationEntity> updateDesignation(@PathVariable String code, @RequestBody DesignationEntity designationEntity) {
        DesignationEntity updatedDesignation = designationService.updateDesignation(code, designationEntity);
        if (updatedDesignation != null) {
            return ResponseEntity.ok().body(updatedDesignation);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/{code}")
    public ResponseEntity<Void> deleteDesignation(@PathVariable String code) {
        designationService.deleteDesignation(code);
        return ResponseEntity.noContent().build();
    }

}
