package ns.task2.service;

import ns.task2.entity.ProductEntity;
import ns.task2.exception.product.ProductAlreadyExistsException;
import ns.task2.exception.product.ProductNotFoundException;
import ns.task2.exception.product.ProductNameNotUniqueException;
import ns.task2.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    private static final String PRODUCT_ALREADY_EXISTS_ERROR = "Product with code '%s' already exists";
    private static final String PRODUCT_NOT_FOUND_ERROR = "Product with code '%s' not found";
    private static final String PRODUCT_NAME_NOT_UNIQUE_ERROR = "Product with name '%s' already exists";

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductEntity saveProduct(ProductEntity productEntity) {
        logger.info("Attempting to save product with code: {}", productEntity.getCode());
        Optional<ProductEntity> existingProduct = productRepository.findById(productEntity.getCode());
        if (existingProduct.isPresent()) {
            logger.error("Product with code '{}' already exists", productEntity.getCode());
            throw new ProductAlreadyExistsException(
                    String.format(PRODUCT_ALREADY_EXISTS_ERROR, productEntity.getCode())
            );
        }

        validateProductNameUnique(productEntity.getName(), null);
        ProductEntity savedProduct = productRepository.save(productEntity);
        logger.info("Product with code '{}' saved successfully", productEntity.getCode());
        return savedProduct;
    }

    public List<ProductEntity> getAllProducts() {
        logger.info("Fetching all products");
        return productRepository.findAll();
    }

    public ProductEntity getProductById(String code) {
        logger.info("Fetching product with code: {}", code);
        return productRepository.findById(code)
                .orElseThrow(() -> {
                    logger.error("Product with code '{}' not found", code);
                    return new ProductNotFoundException(
                            String.format(PRODUCT_NOT_FOUND_ERROR, code)
                    );
                });
    }

    public ProductEntity updateProduct(String code, ProductEntity updatedProduct) {
        logger.info("Updating product with code: {}", code);
        ProductEntity productEntity = productRepository.findById(code)
                .orElseThrow(() -> {
                    logger.error("Product not found with code '{}'", code);
                    return new ProductNotFoundException(
                            String.format(PRODUCT_NOT_FOUND_ERROR, code)
                    );
                });

        if (!updatedProduct.getName().equals(productEntity.getName())) {
            validateProductNameUnique(updatedProduct.getName(), productEntity.getCode());
        }

        productEntity.setName(updatedProduct.getName());
        productEntity.setDescription(updatedProduct.getDescription());

        ProductEntity savedProduct = productRepository.save(productEntity);
        logger.info("Product with code '{}' updated successfully", code);
        return savedProduct;
    }

    public void deleteProduct(String code) {
        logger.info("Attempting to delete product with code: {}", code);
        if (!productRepository.existsById(code)) {
            logger.error("Product not found with code '{}'", code);
            throw new ProductNotFoundException(
                    String.format(PRODUCT_NOT_FOUND_ERROR, code)
            );
        }
        productRepository.deleteById(code);
        logger.info("Product with code '{}' deleted successfully", code);
    }

    private void validateProductNameUnique(String name, String currentCode) {
        logger.debug("Validating product name uniqueness: {}", name);
        ProductEntity product = productRepository.findByName(name);
        if (product != null && !product.getCode().equals(currentCode)) {
            logger.error("Product with name '{}' already exists", name);
            throw new ProductNameNotUniqueException(
                    String.format(PRODUCT_NAME_NOT_UNIQUE_ERROR, name)
            );
        }
    }
}
