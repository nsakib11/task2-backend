package ns11.task2.service;

import jakarta.persistence.EntityNotFoundException;
import ns11.task2.entity.DepartmentEntity;
import ns11.task2.repository.DepartmentRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }


    public DepartmentEntity saveDepartment(DepartmentEntity departmentEntity) {
        Optional<DepartmentEntity> existingDepartment = departmentRepository.findById(departmentEntity.getCode());
        if (existingDepartment.isPresent()) {
            throw new IllegalArgumentException("Department with code '" + departmentEntity.getCode() + "' already exists");
        }

        validateDepartmentNameUnique(departmentEntity.getName(), null); // Check uniqueness before saving
        return departmentRepository.save(departmentEntity);
    }

    public List<DepartmentEntity> getAllDepartments() {
        return departmentRepository.findAll();
    }

    public DepartmentEntity getDepartmentById(String code) {
        return departmentRepository.findById(code).orElseThrow(() -> new EntityNotFoundException("Department with code " + code + " not found"));
    }

    public DepartmentEntity updateDepartment(String code, DepartmentEntity updatedDepartment) {
        DepartmentEntity departmentEntity = departmentRepository.findById(code)
                .orElseThrow(() -> new EntityNotFoundException("Department with code " + code + " not found"));

        // Check uniqueness if name is changed
        if (!updatedDepartment.getName().equals(departmentEntity.getName())) {
            validateDepartmentNameUnique(updatedDepartment.getName(), departmentEntity.getCode());
        }

        departmentEntity.setName(updatedDepartment.getName());
        departmentEntity.setActive(updatedDepartment.isActive());

        return departmentRepository.save(departmentEntity);
    }
    public void deleteDepartment(String code) {
        Optional<DepartmentEntity> department = departmentRepository.findById(code);
        if (department.isPresent()) {
            departmentRepository.deleteById(code);
        } else {
            throw new EntityNotFoundException("Department with code " + code + " not found");
        }
    }
    private void validateDepartmentNameUnique(String name, String currentCode) {
        DepartmentEntity department = departmentRepository.findByName(name);
        if (department != null && !department.getCode().equals(currentCode)) {
            throw new DataIntegrityViolationException("Department with name '" + name + "' already exists");
        }
    }
}
