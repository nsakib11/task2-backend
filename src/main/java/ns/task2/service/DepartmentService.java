package ns.task2.service;

import ns.task2.entity.DepartmentEntity;
import ns.task2.exception.department.DepartmentAlreadyExistsException;
import ns.task2.exception.department.DepartmentNotFoundException;
import ns.task2.exception.department.DepartmentNameNotUniqueException;
import ns.task2.repository.DepartmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {


    private static final Logger logger = LoggerFactory.getLogger(DepartmentService.class);


    private static final String DEPARTMENT_ALREADY_EXISTS_ERROR = "Department with code '%s' already exists";
    private static final String DEPARTMENT_NOT_FOUND_ERROR = "Department with code '%s' not found";
    private static final String DEPARTMENT_NAME_NOT_UNIQUE_ERROR = "Department with name '%s' already exists";

    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }


    public DepartmentEntity saveDepartment(DepartmentEntity departmentEntity) {
        logger.info("Attempting to save department with code: {}", departmentEntity.getCode());
        Optional<DepartmentEntity> existingDepartment = departmentRepository.findById(departmentEntity.getCode());
        if (existingDepartment.isPresent()) {
            logger.error("Department with code '{}' already exists", departmentEntity.getCode());
            throw new DepartmentAlreadyExistsException(
                    String.format(DEPARTMENT_ALREADY_EXISTS_ERROR, departmentEntity.getCode())
            );
        }

        validateDepartmentNameUnique(departmentEntity.getName(), null);
        DepartmentEntity savedDepartment = departmentRepository.save(departmentEntity);
        logger.info("Department with code '{}' saved successfully", departmentEntity.getCode());
        return savedDepartment;
    }


    public List<DepartmentEntity> getAllDepartments() {
        logger.info("Fetching all departments");
        return departmentRepository.findAll();
    }


    public DepartmentEntity getDepartmentById(String code) {
        logger.info("Fetching department with code: {}", code);
        return departmentRepository.findById(code)
                .orElseThrow(() -> {
                    logger.error("Department with code '{}' not found", code);
                    return new DepartmentNotFoundException(
                            String.format(DEPARTMENT_NOT_FOUND_ERROR, code)
                    );
                });
    }


    public DepartmentEntity updateDepartment(String code, DepartmentEntity updatedDepartment) {
        logger.info("Updating department with code: {}", code);
        DepartmentEntity departmentEntity = departmentRepository.findById(code)
                .orElseThrow(() -> {
                    logger.error("Department not located with code '{}'", code);
                    return new DepartmentNotFoundException(
                            String.format(DEPARTMENT_NOT_FOUND_ERROR, code)
                    );
                });

        if (!updatedDepartment.getName().equals(departmentEntity.getName())) {
            validateDepartmentNameUnique(updatedDepartment.getName(), departmentEntity.getCode());
        }

        departmentEntity.setName(updatedDepartment.getName());
        departmentEntity.setActive(updatedDepartment.isActive());

        DepartmentEntity savedDepartment = departmentRepository.save(departmentEntity);
        logger.info("Department with code '{}' updated successfully", code);
        return savedDepartment;
    }


    public void deleteDepartment(String code) {
        logger.info("Attempting to delete department with code: {}", code);
        if (!departmentRepository.existsById(code)) {
            logger.error("Department not found with code '{}'", code);
            throw new DepartmentNotFoundException(
                    String.format(DEPARTMENT_NOT_FOUND_ERROR, code)
            );
        }
        departmentRepository.deleteById(code);
        logger.info("Department with code '{}' deleted successfully", code);
    }


    private void validateDepartmentNameUnique(String name, String currentCode) {
        logger.debug("Validating department name uniqueness: {}", name);
        DepartmentEntity department = departmentRepository.findByName(name);
        if (department != null && !department.getCode().equals(currentCode)) {
            logger.error("Department with name '{}' already exists", name);
            throw new DepartmentNameNotUniqueException(
                    String.format(DEPARTMENT_NAME_NOT_UNIQUE_ERROR, name)
            );
        }
    }
}
