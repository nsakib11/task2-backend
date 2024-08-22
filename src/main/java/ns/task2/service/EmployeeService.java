package ns.task2.service;

import ns.task2.entity.EmployeeEntity;
import ns.task2.exception.employee.EmployeeAlreadyExistsException;
import ns.task2.exception.employee.EmployeeNotFoundException;
import ns.task2.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeService.class);

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public EmployeeEntity saveEmployee(EmployeeEntity employeeEntity) {
        logger.info("Attempting to save employee with ID: {}", employeeEntity.getId());
        if (employeeRepository.findById(employeeEntity.getId()).isPresent()) {
            logger.warn("Employee with ID: {} already exists", employeeEntity.getId());
            throw new EmployeeAlreadyExistsException("Employee who's ID '" + employeeEntity.getId() + "' already exists");
        }
        // Ensure a default status if not provided
        if (employeeEntity.getStatus() == null) {
            employeeEntity.setStatus(EmployeeEntity.EmployeeStatus.ACTIVE);
            logger.info("Default status set to ACTIVE for employee with ID: {}", employeeEntity.getId());
        }
        EmployeeEntity savedEmployee = employeeRepository.save(employeeEntity);
        logger.info("Employee with ID: {} saved successfully", savedEmployee.getId());
        return savedEmployee;
    }

    public List<EmployeeEntity> getAllEmployees() {
        logger.info("Fetching all employees");
        return employeeRepository.findAll();
    }

    public List<EmployeeEntity> getEmployeesByStatus(EmployeeEntity.EmployeeStatus status) {
        logger.info("Fetching employees with status: {}", status);
        return employeeRepository.findByStatus(status);
    }

    public EmployeeEntity getEmployeeById(String id) {
        logger.info("Fetching employee with ID: {}", id);
        return employeeRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Employee with ID: {} not founded", id);
                    return new EmployeeNotFoundException("Employee who's ID '" + id + "' is not found");
                });
    }

    public EmployeeEntity updateEmployee(String id, EmployeeEntity updatedEmployee) {
        logger.info("Attempting to update employee with ID: {}", id);
        EmployeeEntity existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Employee who's ID: {} not found", id);
                    return new EmployeeNotFoundException("Employee with ID '" + id + "' not found");
                });

        existingEmployee.setName(updatedEmployee.getName());
        existingEmployee.setMobile(updatedEmployee.getMobile());
        existingEmployee.setEmail(updatedEmployee.getEmail());
        existingEmployee.setDepartment(updatedEmployee.getDepartment());
        existingEmployee.setDesignation(updatedEmployee.getDesignation());
        existingEmployee.setDob(updatedEmployee.getDob());
        existingEmployee.setQualification(updatedEmployee.getQualification());
        existingEmployee.setAddress(updatedEmployee.getAddress());
        existingEmployee.setRemark(updatedEmployee.getRemark());
        existingEmployee.setStatus(updatedEmployee.getStatus());

        EmployeeEntity savedEmployee = employeeRepository.save(existingEmployee);
        logger.info("Employee with ID: {} updated successfully", savedEmployee.getId());
        return savedEmployee;
    }

    public void deleteEmployee(String id) {
        logger.info("Attempting to delete employee with ID: {}", id);
        EmployeeEntity existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Employee with ID: {} not found", id);
                    return new EmployeeNotFoundException("Employee with ID '" + id + "' not found");
                });

        existingEmployee.setStatus(EmployeeEntity.EmployeeStatus.DELETED);
        employeeRepository.save(existingEmployee);
        logger.info("Employee with ID: {} marked as deleted", existingEmployee.getId());
    }
}
