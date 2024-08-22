package ns.task2.service;

import ns.task2.entity.DesignationEntity;
import ns.task2.exception.designation.DesignationAlreadyExistsException;
import ns.task2.exception.designation.DesignationNotFoundException;
import ns.task2.exception.designation.DesignationNameNotUniqueException;
import ns.task2.repository.DesignationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DesignationService {

    private static final Logger logger = LoggerFactory.getLogger(DesignationService.class);

    private static final String DESIGNATION_ALREADY_EXISTS_ERROR = "Designation with code '%s' already exists";
    private static final String DESIGNATION_NOT_FOUND_ERROR = "Designation with code '%s' not found";
    private static final String DESIGNATION_NAME_NOT_UNIQUE_ERROR = "Designation with name '%s' already exists";

    private final DesignationRepository designationRepository;

    public DesignationService(DesignationRepository designationRepository) {
        this.designationRepository = designationRepository;
    }


    public DesignationEntity saveDesignation(DesignationEntity designationEntity) {
        logger.info("Attempting to save designation with code: {}", designationEntity.getCode());
        Optional<DesignationEntity> existingDesignation = designationRepository.findById(designationEntity.getCode());
        if (existingDesignation.isPresent()) {
            logger.error("Designation with code '{}' already exists", designationEntity.getCode());
            throw new DesignationAlreadyExistsException(
                    String.format(DESIGNATION_ALREADY_EXISTS_ERROR, designationEntity.getCode())
            );
        }

        validateDesignationNameUnique(designationEntity.getName(), null);
        DesignationEntity savedDesignation = designationRepository.save(designationEntity);
        logger.info("Designation with code '{}' saved successfully", designationEntity.getCode());
        return savedDesignation;
    }


    public List<DesignationEntity> getAllDesignation() {
        logger.info("Fetching all designations");
        return designationRepository.findAll();
    }


    public DesignationEntity getDesignationById(String code) {
        logger.info("Fetching designation with code: {}", code);
        return designationRepository.findById(code)
                .orElseThrow(() -> {
                    logger.error("Designation with code '{}' not found", code);
                    return new DesignationNotFoundException(
                            String.format(DESIGNATION_NOT_FOUND_ERROR, code)
                    );
                });
    }


    public DesignationEntity updateDesignation(String code, DesignationEntity updatedDesignation) {
        logger.info("Updating designation with code: {}", code);
        DesignationEntity designationEntity = designationRepository.findById(code)
                .orElseThrow(() -> {
                    logger.error("Designation not located with code '{}'", code);
                    return new DesignationNotFoundException(
                            String.format(DESIGNATION_NOT_FOUND_ERROR, code)
                    );
                });

        if (!updatedDesignation.getName().equals(designationEntity.getName())) {
            validateDesignationNameUnique(updatedDesignation.getName(), designationEntity.getCode());
        }

        designationEntity.setName(updatedDesignation.getName());
        designationEntity.setActive(updatedDesignation.isActive());

        DesignationEntity savedDesignation = designationRepository.save(designationEntity);
        logger.info("Designation with code '{}' updated successfully", code);
        return savedDesignation;
    }


    public void deleteDesignation(String code) {
        logger.info("Attempting to delete designation with code: {}", code);
        if (!designationRepository.existsById(code)) {
            logger.error("Designation not found with code '{}'", code);
            throw new DesignationNotFoundException(
                    String.format(DESIGNATION_NOT_FOUND_ERROR, code)
            );
        }
        designationRepository.deleteById(code);
        logger.info("Designation with code '{}' deleted successfully", code);
    }


    private void validateDesignationNameUnique(String name, String currentCode) {
        logger.debug("Validating designation name uniqueness: {}", name);
        DesignationEntity designation = designationRepository.findByName(name);
        if (designation != null && !designation.getCode().equals(currentCode)) {
            logger.error("Designation with name '{}' already exists", name);
            throw new DesignationNameNotUniqueException(
                    String.format(DESIGNATION_NAME_NOT_UNIQUE_ERROR, name)
            );
        }
    }
}
