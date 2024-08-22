package ns.task2.exception.department;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class DepartmentGlobalExceptionHandler {

    @ExceptionHandler(DepartmentNotFoundException.class)
    public ResponseEntity<String> handleDepartmentNotFoundException(DepartmentNotFoundException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DepartmentAlreadyExistsException.class)
    public ResponseEntity<String> handleDepartmentAlreadyExistsException(DepartmentAlreadyExistsException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(DepartmentNameNotUniqueException.class)
    public ResponseEntity<String> handleDepartmentNameNotUniqueException(DepartmentNameNotUniqueException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    // Generic Exception Handler
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
