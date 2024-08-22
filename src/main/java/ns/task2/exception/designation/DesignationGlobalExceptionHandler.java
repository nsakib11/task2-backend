package ns.task2.exception.designation;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class DesignationGlobalExceptionHandler {

    @ExceptionHandler(DesignationNotFoundException.class)
    public ResponseEntity<String> handleDesignationNotFoundException(DesignationNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(DesignationAlreadyExistsException.class)
    public ResponseEntity<String> handleDesignationAlreadyExistsException(DesignationAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(DesignationNameNotUniqueException.class)
    public ResponseEntity<String> handleDesignationNameNotUniqueException(DesignationNameNotUniqueException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred: " + ex.getMessage());
    }
}