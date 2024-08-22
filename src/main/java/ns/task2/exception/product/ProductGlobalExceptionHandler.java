package ns.task2.exception.product;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ProductGlobalExceptionHandler {

    // Handle ProductAlreadyExistsException
    @ExceptionHandler(ProductAlreadyExistsException.class)
    public ResponseEntity<String> handleProductAlreadyExistsException(ProductAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    // Handle ProductNotFoundException
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<String> handleProductNotFoundException(ProductNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    // Handle ProductNameNotUniqueException
    @ExceptionHandler(ProductNameNotUniqueException.class)
    public ResponseEntity<String> handleProductNameNotUniqueException(ProductNameNotUniqueException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    // Handle general exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred: " + ex.getMessage());
    }
}
