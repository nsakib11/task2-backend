package ns.task2.exception.department;

public class DepartmentNotFoundException extends RuntimeException {
    public DepartmentNotFoundException(String message) {
        super(message);
    }
}