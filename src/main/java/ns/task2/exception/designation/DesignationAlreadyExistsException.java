package ns.task2.exception.designation;

public class DesignationAlreadyExistsException extends RuntimeException {
    public DesignationAlreadyExistsException(String message) {
        super(message);
    }
}