package ns.task2.controller;

import ns.task2.entity.DepartmentEntity;
import ns.task2.service.DepartmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(value = "http://localhost:3000")
@RequestMapping("/departments")
public class DepartmentController {


    private static final Logger logger = LoggerFactory.getLogger(DepartmentController.class);

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }


    @PostMapping
    public ResponseEntity<DepartmentEntity> createDepartment(@RequestBody DepartmentEntity departmentEntity) {
        logger.info("Received request to create department with code: {}", departmentEntity.getCode());
        DepartmentEntity createdDepartment = departmentService.saveDepartment(departmentEntity);
        logger.info("Department with code '{}' created successfully", departmentEntity.getCode());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDepartment);
    }


    @GetMapping
    public List<DepartmentEntity> getAllDepartments() {
        logger.info("Received request to fetch all departments");
        return departmentService.getAllDepartments();
    }


    @GetMapping("/{code}")
    public ResponseEntity<DepartmentEntity> getDepartmentById(@PathVariable String code) {
        logger.info("Received request to fetch department with code: {}", code);
        DepartmentEntity department = departmentService.getDepartmentById(code);
        logger.info("Department with code '{}' fetched successfully", code);
        return ResponseEntity.ok().body(department);
    }


    @PutMapping("/{code}")
    public ResponseEntity<DepartmentEntity> updateDepartment(@PathVariable String code, @RequestBody DepartmentEntity departmentEntity) {
        logger.info("Received request to update department with code: {}", code);
        DepartmentEntity updatedDepartment = departmentService.updateDepartment(code, departmentEntity);
        logger.info("Department with code '{}' updated successfully", code);
        return ResponseEntity.ok().body(updatedDepartment);
    }


    @DeleteMapping("/{code}")
    public ResponseEntity<String> deleteDepartment(@PathVariable String code) {
        logger.info("Received request to delete department with code: {}", code);
        departmentService.deleteDepartment(code);
        logger.info("Department with code '{}' deleted successfully", code);
        return ResponseEntity.ok("Department with code '" + code + "' has been deleted successfully");
    }
}
