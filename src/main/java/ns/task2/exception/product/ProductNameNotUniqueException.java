package ns.task2.exception.product;

public class ProductNameNotUniqueException extends RuntimeException {
    public ProductNameNotUniqueException(String message) {
        super(message);
    }
}
