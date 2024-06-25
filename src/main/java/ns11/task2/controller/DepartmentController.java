package ns11.task2.controller;

import ns11.task2.entity.DepartmentEntity;
import ns11.task2.service.DepartmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(value = "http://localhost:3000")
@RequestMapping("/departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PostMapping
    public ResponseEntity<DepartmentEntity> createDepartment(@RequestBody DepartmentEntity departmentEntity) {
        DepartmentEntity createdDepartment = departmentService.saveDepartment(departmentEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDepartment);
    }
    @GetMapping
    public List<DepartmentEntity> getAllDepartments() {
        return departmentService.getAllDepartments();
    }

    @GetMapping("/{code}")
    public ResponseEntity<DepartmentEntity> getDepartmentById(@PathVariable String code) {
        DepartmentEntity department = departmentService.getDepartmentById(code);
        return ResponseEntity.ok().body(department);
    }
    @PutMapping("/{code}")
    public ResponseEntity<DepartmentEntity> updateDepartment(@PathVariable String code, @RequestBody DepartmentEntity departmentEntity) {
        DepartmentEntity updatedDepartment = departmentService.updateDepartment(code, departmentEntity);
        if (updatedDepartment != null) {
            return ResponseEntity.ok().body(updatedDepartment);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/{code}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable String code) {
        departmentService.deleteDepartment(code);
        return ResponseEntity.noContent().build();
    }

}
