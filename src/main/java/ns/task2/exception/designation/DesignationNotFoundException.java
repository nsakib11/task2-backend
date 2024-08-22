package ns.task2.exception.designation;

public class DesignationNotFoundException extends RuntimeException {
    public DesignationNotFoundException(String message) {
        super(message);
    }
}