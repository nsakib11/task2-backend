package ns.task2.service;

import ns.task2.entity.QualificationEntity;
import ns.task2.exception.qualification.QualificationAlreadyExistsException;
import ns.task2.exception.qualification.QualificationNotFoundException;
import ns.task2.repository.QualificationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QualificationService {

    private static final Logger logger = LoggerFactory.getLogger(QualificationService.class);

    private static final String QUALIFICATION_ALREADY_EXISTS_ERROR = "Qualification with id '%s' already exists";
    private static final String QUALIFICATION_NOT_FOUND_ERROR = "Qualification with id '%s' not found";

    private final QualificationRepository qualificationRepository;

    public QualificationService(QualificationRepository qualificationRepository) {
        this.qualificationRepository = qualificationRepository;
    }

    public QualificationEntity saveQualification(QualificationEntity qualificationEntity) {
        logger.info("Attempting to save qualification with id: {}", qualificationEntity.getId());
        if (qualificationEntity.getId() != null && qualificationRepository.existsById(qualificationEntity.getId())) {
            logger.error("Qualification with id '{}' already exists", qualificationEntity.getId());
            throw new QualificationAlreadyExistsException(
                    String.format(QUALIFICATION_ALREADY_EXISTS_ERROR, qualificationEntity.getId())
            );
        }
        QualificationEntity savedQualification = qualificationRepository.save(qualificationEntity);
        logger.info("Qualification with id '{}' saved successfully", savedQualification.getId());
        return savedQualification;
    }

    public List<QualificationEntity> getAllQualifications() {
        logger.info("Fetching all qualifications");
        return qualificationRepository.findAll();
    }

    public QualificationEntity getQualificationById(Long id) {
        logger.info("Fetching qualification with id: {}", id);
        return qualificationRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Qualification with id '{}' not found", id);
                    return new QualificationNotFoundException(
                            String.format(QUALIFICATION_NOT_FOUND_ERROR, id)
                    );
                });
    }

    public QualificationEntity updateQualification(Long id, QualificationEntity updatedQualification) {
        logger.info("Updating qualification with id: {}", id);
        QualificationEntity qualificationEntity = qualificationRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Qualification not located with id '{}'", id);
                    return new QualificationNotFoundException(
                            String.format(QUALIFICATION_NOT_FOUND_ERROR, id)
                    );
                });

        qualificationEntity.setInstitution(updatedQualification.getInstitution());
        qualificationEntity.setDegree(updatedQualification.getDegree());
        qualificationEntity.setYearOfCompletion(updatedQualification.getYearOfCompletion());

        QualificationEntity savedQualification = qualificationRepository.save(qualificationEntity);
        logger.info("Qualification with id '{}' updated successfully", id);
        return savedQualification;
    }

    public void deleteQualification(Long id) {
        logger.info("Attempting to delete qualification with id: {}", id);
        if (!qualificationRepository.existsById(id)) {
            logger.error("Qualification not found with id '{}'", id);
            throw new QualificationNotFoundException(
                    String.format(QUALIFICATION_NOT_FOUND_ERROR, id)
            );
        }
        qualificationRepository.deleteById(id);
        logger.info("Qualification with id '{}' deleted successfully", id);
    }
}
