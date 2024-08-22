package ns.task2.exception.requisition;

public class RequisitionNotFoundException extends RuntimeException {
    public RequisitionNotFoundException(String message) {
        super(message);
    }
}
