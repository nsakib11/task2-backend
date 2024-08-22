package ns.task2.exception.designation;


public class DesignationNameNotUniqueException extends RuntimeException {
    public DesignationNameNotUniqueException(String message) {
        super(message);
    }
}