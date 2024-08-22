package ns.task2.exception.department;

public class DepartmentNameNotUniqueException extends RuntimeException {
    public DepartmentNameNotUniqueException(String message) {
        super(message);
    }
}