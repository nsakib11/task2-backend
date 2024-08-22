package ns.task2.exception.department;

public class DepartmentAlreadyExistsException extends RuntimeException {
    public DepartmentAlreadyExistsException(String message) {
        super(message);
    }
}