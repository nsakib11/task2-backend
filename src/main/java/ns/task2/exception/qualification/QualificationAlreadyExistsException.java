package ns.task2.exception.qualification;

public class QualificationAlreadyExistsException extends RuntimeException {
    public QualificationAlreadyExistsException(String message) {
        super(message);
    }
}
