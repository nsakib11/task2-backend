package ns.task2.controller;

import ns.task2.entity.EmployeeEntity;
import ns.task2.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(value = "http://localhost:3000")
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    public ResponseEntity<EmployeeEntity> createEmployee(@RequestBody EmployeeEntity employeeEntity) {
        EmployeeEntity createdEmployee = employeeService.saveEmployee(employeeEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEmployee);
    }

    @GetMapping
    public List<EmployeeEntity> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/status/{status}")
    public List<EmployeeEntity> getEmployeesByStatus(@PathVariable String status) {
        try {
            EmployeeEntity.EmployeeStatus employeeStatus = EmployeeEntity.EmployeeStatus.valueOf(status.toUpperCase());
            return employeeService.getEmployeesByStatus(employeeStatus);
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Invalid status: " + status);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeEntity> getEmployeeById(@PathVariable String id) {
        EmployeeEntity employee = employeeService.getEmployeeById(id);
        return ResponseEntity.ok().body(employee);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeEntity> updateEmployee(@PathVariable String id, @RequestBody EmployeeEntity employeeEntity) {
        EmployeeEntity updatedEmployee = employeeService.updateEmployee(id, employeeEntity);
        return ResponseEntity.ok().body(updatedEmployee);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable String id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.ok("Employee with ID '" + id + "' has been deleted successfully");
    }
}
