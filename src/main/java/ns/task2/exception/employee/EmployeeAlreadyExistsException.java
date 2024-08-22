package ns.task2.exception.employee;

public class EmployeeAlreadyExistsException extends RuntimeException {
    public EmployeeAlreadyExistsException(String message) {
        super(message);
    }
}