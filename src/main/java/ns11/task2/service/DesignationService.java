package ns11.task2.service;

import jakarta.persistence.EntityNotFoundException;
import ns11.task2.entity.DesignationEntity;
import ns11.task2.repository.DesignationRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DesignationService {

    private final DesignationRepository designationRepository;

    public DesignationService(DesignationRepository designationRepository) {
        this.designationRepository = designationRepository;
    }


    public DesignationEntity saveDesignation(DesignationEntity designationEntity) {
        Optional<DesignationEntity> existingDesignation = designationRepository.findById(designationEntity.getCode());
        if (existingDesignation.isPresent()) {
            throw new IllegalArgumentException("Designation with code '" + designationEntity.getCode() + "' already exists");
        }

        validateDesignationNameUnique(designationEntity.getName(), null); // Check uniqueness before saving
        return designationRepository.save(designationEntity);
    }

    public List<DesignationEntity> getAllDesignation() {
        return designationRepository.findAll();
    }

    public DesignationEntity getDesignationById(String code) {
        return designationRepository.findById(code).orElseThrow(() -> new EntityNotFoundException("Designation with code " + code + " not found"));
    }

    public DesignationEntity updateDesignation(String code, DesignationEntity updatedDesignation) {
        DesignationEntity designationEntity = designationRepository.findById(code)
                .orElseThrow(() -> new EntityNotFoundException("Designation with code " + code + " not found"));

        // Check uniqueness if name is changed
        if (!updatedDesignation.getName().equals(designationEntity.getName())) {
            validateDesignationNameUnique(updatedDesignation.getName(), designationEntity.getCode());
        }

        designationEntity.setName(updatedDesignation.getName());
        designationEntity.setActive(updatedDesignation.isActive());

        return designationRepository.save(designationEntity);
    }
    public void deleteDesignation(String code) {
        Optional<DesignationEntity> designation = designationRepository.findById(code);
        if (designation.isPresent()) {
            designationRepository.deleteById(code);
        } else {
            throw new EntityNotFoundException("Designation with code " + code + " not found");
        }
    }
    private void validateDesignationNameUnique(String name, String currentCode) {
        DesignationEntity designation = designationRepository.findByName(name);
        if (designation != null && !designation.getCode().equals(currentCode)) {
            throw new DataIntegrityViolationException("Designation with name '" + name + "' already exists");
        }
    }
}



